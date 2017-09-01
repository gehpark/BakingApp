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
        if (fragmentManager.findFragmentById(R.id.recipe_step) == null ){
            mStepFragment = new RecipeStepFragment();
            setFragmentDetails();
            fragmentManager.beginTransaction()
                    .replace(R.id.recipe_step, mStepFragment)
                    .commit();
        }

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
}
