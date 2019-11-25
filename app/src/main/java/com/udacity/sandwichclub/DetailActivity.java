package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mAlsoKnownAsTV, mOriginTV, mIngredientsTV, mDescriptionTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        mAlsoKnownAsTV = findViewById(R.id.also_known_tv);
        mOriginTV = findViewById(R.id.origin_tv);
        mIngredientsTV = findViewById(R.id.ingredients_tv);
        mDescriptionTV = findViewById(R.id.description_tv);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> sandwichAlsoKnown = sandwich.getAlsoKnownAs();
        int alsoKnownSize = sandwichAlsoKnown.size();
        StringBuilder alsoKnownStrBuild = new StringBuilder();
        for (int i = 0; i < alsoKnownSize; i++) {
            alsoKnownStrBuild.append(sandwichAlsoKnown.get(i));
            if (i < alsoKnownSize - 1)
                alsoKnownStrBuild.append(", ");
        }
        if (alsoKnownSize > 0)
            mAlsoKnownAsTV.setText(alsoKnownStrBuild.toString());
        else
            mAlsoKnownAsTV.setText(R.string.detail_no_details);

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (placeOfOrigin != null && !placeOfOrigin.isEmpty())
            mOriginTV.setText(placeOfOrigin);
        else
            mOriginTV.setText(R.string.detail_no_details);

        List<String> sandwichIngredients = sandwich.getIngredients();
        int ingredientsSize = sandwichIngredients.size();
        StringBuilder ingredientsStrBuild = new StringBuilder();
        for (int i = 0; i < ingredientsSize; i++) {
            ingredientsStrBuild.append(sandwichIngredients.get(i));
            if (i < ingredientsSize - 1)
                ingredientsStrBuild.append(", ");
        }
        if (ingredientsSize > 0)
            mIngredientsTV.setText(ingredientsStrBuild.toString());
        else
            mIngredientsTV.setText(R.string.detail_no_details);

        String description = sandwich.getDescription();
        if (description != null && !description.isEmpty())
            mDescriptionTV.setText(description);
        else
            mDescriptionTV.setText(R.string.detail_no_details);
    }
}
