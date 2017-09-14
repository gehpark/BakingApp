package com.example.gracepark.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.ArrayList;

import static com.example.gracepark.bakingapp.RecipeDetailsActivity.EXTRA_KEY_MEDIA;
import static com.example.gracepark.bakingapp.RecipeDetailsActivity.EXTRA_KEY_POSITION;
import static com.example.gracepark.bakingapp.RecipeDetailsActivity.EXTRA_KEY_SHORT;
import static com.example.gracepark.bakingapp.RecipeDetailsActivity.EXTRA_KEY_TEXT;

/**
 * Activity to hold information about each step of a recipe. Holds RecipeStepFragment in phone view.
 */

public class RecipeStepActivity extends AppCompatActivity implements RecipeStepFragment.OnNextClickListener{

    private static String STEP_FRAGMENT_TAG = "step_fragment_tag";

    private int mPosition;
    private ArrayList<String> mStepMediaList;
    private ArrayList<String> mStepTextList;
    private ArrayList<String> mStepShortList;
    private RecipeStepFragment mStepFragment;

    @Override
    public void onNextClicked() {
        mPosition++;
        setFragmentDetails();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step);

        Bundle bundle = getIntent().getExtras();

        mPosition = bundle.getInt(EXTRA_KEY_POSITION);
        mStepMediaList = bundle.getStringArrayList(EXTRA_KEY_MEDIA);
        mStepTextList = bundle.getStringArrayList(EXTRA_KEY_TEXT);
        mStepShortList = bundle.getStringArrayList(EXTRA_KEY_SHORT);

        FragmentManager fragmentManager = getSupportFragmentManager();

        if (savedInstanceState != null) {
            mStepFragment = (RecipeStepFragment) fragmentManager.getFragment(savedInstanceState, STEP_FRAGMENT_TAG);
        } else {
            mStepFragment = new RecipeStepFragment();
            setFragmentDetails();
        }

        fragmentManager.beginTransaction()
                .replace(R.id.recipe_step, mStepFragment)
                .commit();
    }

    private void setFragmentDetails() {
        mStepFragment.setMedia(mStepMediaList.get(mPosition));
        mStepFragment.setText(mStepTextList.get(mPosition));
        if (mPosition + 1 < mStepShortList.size()) {
            mStepFragment.setNextButtonText(mStepShortList.get(mPosition + 1));
        } else {
            mStepFragment.hideNextButton();
        }
        mStepFragment.setViews();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, STEP_FRAGMENT_TAG, mStepFragment);
    }
}
