package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) throws JSONException {

        /* Sandwich keys in the json object */
        final String NAME = "name";
        final String MAIN_NAME = "mainName";
        final String ALSO_KNOWN_AS = "alsoKnownAs";
        final String PLACE_OF_ORIGIN = "placeOfOrigin";
        final String DESCRIPTION = "description";
        final String IMAGE = "image";
        final String INGREDIENTS = "ingredients";


        String mainName;

        /* List store also known as names */
        List parsedAlsoKnownAsNames = null;

        String placeOfOrigin="";

        String description="";

        String image="";

        /* List to store ingredients */
        List parsedIngredients = null;

        //JSon parsing
        JSONObject sandwichJson = new JSONObject(json);

        JSONObject nameJSON = sandwichJson.getJSONObject(NAME);
        mainName  = nameJSON.getString(MAIN_NAME);

        if(nameJSON.isNull(ALSO_KNOWN_AS)){

        }else{
            JSONArray  otherNamesJSONArray = nameJSON.getJSONArray(ALSO_KNOWN_AS);
            parsedAlsoKnownAsNames = new ArrayList();

            if(otherNamesJSONArray.length()>0){
                for (int i = 0; i < otherNamesJSONArray.length(); i++) {
                    String otherName = otherNamesJSONArray.getString(i);
                    parsedAlsoKnownAsNames.add(otherName);
                }
            }
        }

        if(sandwichJson.isNull(PLACE_OF_ORIGIN)){

        }else{
            placeOfOrigin  = sandwichJson.getString(PLACE_OF_ORIGIN);
        }


        description = sandwichJson.getString(DESCRIPTION);

        image = sandwichJson.getString(IMAGE);

        JSONArray  ingredientsJSONArray = sandwichJson.getJSONArray(INGREDIENTS);

        parsedIngredients = new ArrayList();

        if(ingredientsJSONArray.length()>0){
            for(int i=0;i<ingredientsJSONArray.length();i++){
                String ingredient= ingredientsJSONArray.getString(i);
                parsedIngredients.add(ingredient);
            }
        }




        //Creating the sandwich object
        Sandwich sandwich = new Sandwich();
        sandwich.setMainName(mainName);
        sandwich.setAlsoKnownAs(parsedAlsoKnownAsNames);
        sandwich.setPlaceOfOrigin(placeOfOrigin);
        sandwich.setDescription(description);
        sandwich.setImage(image);
        sandwich.setIngredients(parsedIngredients);

        return sandwich;
    }
}
