package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        if (json != null && !json.isEmpty()) {
            try {
                JSONObject sandwichJsonObj = new JSONObject(json);
                JSONObject sandwichNameObj = sandwichJsonObj.getJSONObject("name");
                String mainName = sandwichNameObj.getString("mainName");
                JSONArray alsoKnownAsJson = sandwichNameObj.getJSONArray("alsoKnownAs");
                List<String> alsoKnownAs = new ArrayList<>();
                for (int i = 0; i < alsoKnownAsJson.length(); i++) {
                    alsoKnownAs.add(alsoKnownAsJson.get(i).toString());
                }
                String placeOfOrigin = sandwichJsonObj.getString("placeOfOrigin");
                String description = sandwichJsonObj.getString("description");
                String image = sandwichJsonObj.getString("image");
                JSONArray ingredientsJson = sandwichJsonObj.getJSONArray("ingredients");
                List<String> ingredients = new ArrayList<>();
                for (int i = 0; i < ingredientsJson.length(); i++) {
                    ingredients.add(ingredientsJson.get(i).toString());
                }

                return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);
            } catch (JSONException je) {
                je.printStackTrace();
            }
        }

        return null;
    }
}
