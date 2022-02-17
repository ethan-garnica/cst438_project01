package com.daclink.drew.sp22.cst438_project01_starter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import retrofit2.Call;

public class  SearchPage extends AppCompatActivity {

    private Button mSearchByName;
    private Button mSearchByIngredient;
    private EditText mRecipeName;
    private EditText mMainIngredient;
    private RecipeModel recipeModel;
    private Recipe recipe;
    private RetrofitClientInstance retrofitClientInstance;
    private LiveData<RecipeModel> recipeModelLiveData;

    private int mUserId;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_page);

        wireUpDisplay();
    }

    private void wireUpDisplay() {

        mSearchByName = findViewById(R.id.buttonSearchByName);
        mSearchByIngredient = findViewById((R.id.buttonSearchByIngredient));
        mRecipeName = findViewById(R.id.editTextSearchByName);
        mMainIngredient = findViewById(R.id.editTextSearchByIngredient);

        mSearchByName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mRecipeName != null) {
                    recipe = getRecipeByNameApi(mRecipeName.getText().toString());
                    Intent intent = DisplayRecipe.intentFactory(getApplicationContext(), mUser.getUserId(), recipe);
                    startActivity(intent);
                }
            }
        });
    }

    private Recipe getRecipeByNameApi(String recipeName) {
        retrofitClientInstance.searchByName(recipeName);
        recipeModelLiveData = retrofitClientInstance.getRecipeModelLiveData();
        recipeModel = recipeModelLiveData.getValue();
        recipe = new Recipe(recipeModel);
        return recipe;
    }
}