package com.example.gracepark.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_INGREDIENTS;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_NAME;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_STEPS;

/**
 * Activity to hold details on recipe including short descriptions of steps and ingredients.
 * Holds RecipeDetailsFragment in phone view.
 */

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeDetailsFragment.OnStepClickListener {

    public final static String EXTRA_KEY_POSITION = "key_step_position";
    public final static String EXTRA_KEY_MEDIA = "key_step_media";
    public final static String EXTRA_KEY_TEXT = "key_step_text";
    public final static String EXTRA_KEY_SHORT = "key_step_short";

    private RecipeDetailsFragment mDetailsFragment;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra(EXTRA_RECIPE_NAME));

        mDetailsFragment = new RecipeDetailsFragment();
        mDetailsFragment.setIngredientsListData(intent.getStringExtra(EXTRA_RECIPE_INGREDIENTS));
        mDetailsFragment.setStepsListData(intent.getStringExtra(EXTRA_RECIPE_STEPS));
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_details, mDetailsFragment)
                .commit();
    }

    public void onStepSelected(int position) {

        final Intent intent = new Intent(this, RecipeStepActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList(EXTRA_KEY_SHORT, mDetailsFragment.getShortDescriptionList());
        bundle.putStringArrayList(EXTRA_KEY_TEXT, mDetailsFragment.getTextList());
        bundle.putStringArrayList(EXTRA_KEY_MEDIA, mDetailsFragment.getMediaList());
        bundle.putInt(EXTRA_KEY_POSITION, position);
        intent.putExtras(bundle);

        startActivity(intent);
    }
}
