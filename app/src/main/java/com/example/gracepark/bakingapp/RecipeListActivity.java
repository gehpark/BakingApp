package com.example.gracepark.bakingapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import static com.example.gracepark.bakingapp.provider.RecipeContract.BASE_CONTENT_URI;
import static com.example.gracepark.bakingapp.provider.RecipeContract.PATH_RECIPES;

/**
 * Display the list of recipes that we have by title.
 */
public class RecipeListActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView mRecyclerView;
    private RecipeListAdapter mRecipeListAdapter;
    private GridLayoutManager mGridLayoutManager;

    public static final String EXTRA_RECIPE_ID = "extra_recipe_id";
    public static final String EXTRA_RECIPE_NAME = "extra_recipe_name";
    public static final String EXTRA_RECIPE_INGREDIENTS = "extra_recipe_ingredients";
    public static final String EXTRA_RECIPE_STEPS = "extra_recipe_steps";

    public static final String STATE_POSITION = "state_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);


        mRecyclerView = (RecyclerView) findViewById(R.id.list);
        if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            mGridLayoutManager = new GridLayoutManager(RecipeListActivity.this, 1);
        } else {
            mGridLayoutManager = new GridLayoutManager(RecipeListActivity.this, 3);
        }

        mRecyclerView.setLayoutManager(mGridLayoutManager);

        mRecipeListAdapter = new RecipeListAdapter(
                RecipeListActivity.this,
                null,
                new RecipeClickListener());

        getSupportLoaderManager().initLoader(9876, null, this);

        if (savedInstanceState != null) {
            mGridLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(STATE_POSITION));
        }
        mRecyclerView.setAdapter(mRecipeListAdapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_POSITION, mGridLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri PLANT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();
        return new CursorLoader(this, PLANT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        cursor.moveToFirst();
        mRecipeListAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader loader) {}

    private class RecipeClickListener implements RecipeListAdapter.RecipeOnClickListener {
        @Override
        public void onRecipeClick(int position, String name, String ingredients, String steps) {
            Intent intent = new Intent(RecipeListActivity.this, RecipeDetailsActivity.class);
            intent.putExtra(EXTRA_RECIPE_ID, position);
            intent.putExtra(EXTRA_RECIPE_NAME, name);
            intent.putExtra(EXTRA_RECIPE_INGREDIENTS, ingredients);
            intent.putExtra(EXTRA_RECIPE_STEPS, steps);
            startActivity(intent);
        }
    }
}
