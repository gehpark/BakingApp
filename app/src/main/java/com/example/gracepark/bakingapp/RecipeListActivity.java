package com.example.gracepark.bakingapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.gracepark.bakingapp.data.Recipe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class RecipeListActivity extends AppCompatActivity {

    private RecipeListAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    public static final String EXTRA_RECIPE_INGREDIENTS = "extra_recipe_ingredients";
    public static final String EXTRA_RECIPE_STEPS = "extra_recipe_steps";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(RecipeListActivity.this));


        new RecipeFetchTask().execute();
    }

    private void showSnackbar(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }

    private class RecipeFetchTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {

            try {
                InputStream is = getAssets().open("recipes.json");
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                return new String(buffer, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            List<Recipe> recipeList = new ArrayList<>();

            try {
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject json_data = jsonArray.getJSONObject(i);
                    Recipe recipe = new Recipe();
                    recipe.image = json_data.getString("image");
                    recipe.name = json_data.getString("name");
                    recipe.ingredientsList = json_data.getString("ingredients");
                    recipe.stepsList = json_data.getString("steps");

                    recipeList.add(recipe);
                }

                mAdapter = new RecipeListAdapter(
                        RecipeListActivity.this,
                        recipeList,
                        new RecipeClickListener());
                mRecyclerView.setAdapter(mAdapter);

                mAdapter.setRecipeList(recipeList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class RecipeClickListener implements RecipeListAdapter.RecipeOnClickListener {
        @Override
        public void onRecipeClick(Recipe recipe) {
            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailsActivity.class);
            intent.putExtra(EXTRA_RECIPE_NAME, recipe.name);
            intent.putExtra(EXTRA_RECIPE_INGREDIENTS, recipe.ingredientsList);
            intent.putExtra(EXTRA_RECIPE_STEPS, recipe.stepsList);
            startActivity(intent);
        }
    }
}
