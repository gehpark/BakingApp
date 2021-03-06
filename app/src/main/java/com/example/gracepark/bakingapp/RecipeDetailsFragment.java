package com.example.gracepark.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Fragment to hold recipe details including ingredients and brief outline of instructions.
 */

public class RecipeDetailsFragment extends Fragment {

    public static final String STATE_POSITION = "state_position";

    public ArrayList<String> mIngredientsList = new ArrayList<>();
    private ArrayList<String> mShortStepsList = new ArrayList<>();
    private OnStepClickListener mCallback;
    private RecyclerView mListView;
    private LinearLayoutManager mLayoutManager;

    public RecipeDetailsFragment() {}

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnStepClickListener");
        }
    }

    public void setIngredientsList(ArrayList<String> list) {
        mIngredientsList = list;
    }

    public void setShortStepsList(ArrayList<String> list) {
        mShortStepsList = list;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);

        ArrayList<String> masterList = new ArrayList<>();
        masterList.add(getString(R.string.ingredients));
        masterList.addAll(mIngredientsList);
        masterList.add(getString(R.string.steps));
        masterList.addAll(mShortStepsList);

        mListView = (RecyclerView) rootView.findViewById(R.id.details_list);
        RecipeDetailsAdapter ingredientsAdapter =
                new RecipeDetailsAdapter(
                    masterList,
                    inflater,
                    mIngredientsList.size(),
                    mCallback);


        mLayoutManager = new LinearLayoutManager(getActivity());
        mListView.setLayoutManager(mLayoutManager);
        if (savedInstanceState != null) {
            mLayoutManager.onRestoreInstanceState(savedInstanceState.getParcelable(STATE_POSITION));
        }

        mListView.setAdapter(ingredientsAdapter);


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(STATE_POSITION, mLayoutManager.onSaveInstanceState());
        super.onSaveInstanceState(outState);
    }

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }
}
