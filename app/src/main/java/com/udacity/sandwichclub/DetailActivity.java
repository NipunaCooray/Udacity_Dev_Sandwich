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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    TextView mOtherNames;
    TextView mIngredients;
    TextView mPlaceOfOrigin;
    TextView mDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        mOtherNames = findViewById(R.id.also_known_tv);
        mIngredients = findViewById(R.id.ingredients_tv);
        mPlaceOfOrigin = findViewById(R.id.origin_tv);
        mDescription = findViewById(R.id.description_tv);

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

        try{

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

        }catch(Exception e){
            e.printStackTrace();
        }


    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        if(sandwich.getAlsoKnownAs().isEmpty()){
            mOtherNames.setText("No other names");
        }else{

            String otherNames="";
            for(int i=0;i < sandwich.getAlsoKnownAs().size();i++){
                otherNames = otherNames +" "+ sandwich.getAlsoKnownAs().get(i);
            }

            mOtherNames.setText(otherNames);
        }

        if(sandwich.getIngredients().isEmpty()){
            mIngredients.setText("Ingredients unavailable");
        }else{
            String ingredients="";
            for(int i=0;i < sandwich.getIngredients().size();i++){
                ingredients = ingredients +" "+ sandwich.getIngredients().get(i);
            }

            mIngredients.setText(ingredients);
        }

        if((sandwich.getPlaceOfOrigin().equals("")) || (sandwich.getPlaceOfOrigin().equals(null))){
            mPlaceOfOrigin.setText("Place of origin unknown");
        }else{
            mPlaceOfOrigin.setText(sandwich.getPlaceOfOrigin());
        }

        if((sandwich.getDescription().equals("")) || (sandwich.getDescription().equals(null))){
            mDescription.setText("Description not available");
        }else{
            mDescription.setText(sandwich.getDescription());
        }

    }
}
