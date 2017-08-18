package com.example.gracepark.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragment to hold recipe details including ingredients and brief outline of instructions.
 */

public class RecipeDetailsFragment extends Fragment {

    public ArrayList<String> mIngredientsList = new ArrayList<>();
    private int mIngredientCount;
    private LayoutInflater mInflater;

    public RecipeDetailsFragment() {
    }

    OnStepClickListener mCallback;

    public interface OnStepClickListener {
        void onStepSelected(int position);
    }

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

    public void setIngredientsCount(int count) {
        mIngredientCount = count;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mInflater = inflater;
        View rootView = mInflater.inflate(R.layout.fragment_recipe_details, container, false);


        ListView ingredientsListView = (ListView) rootView.findViewById(R.id.details_list);
        RecipeDetailsFragment.SectionedAdapter ingredientsAdapter = new RecipeDetailsFragment.SectionedAdapter(
                getActivity().getApplicationContext(),
                mIngredientsList);
        ingredientsListView.setAdapter(ingredientsAdapter);
        return rootView;
    }

    public class SectionedAdapter extends ArrayAdapter {

        public SectionedAdapter(Context context, List<String> objects) {
            super(context, 0, objects);
        }

        private final int HEADER_VIEW_TYPE = 0;
        private final int INGREDIENT_VIEW_TYPE = 1;
        private final int STEP_VIEW_TYPE = 2;

        private final int FIXED_SECTION_HEADERS_COUNT = 2;

        @Override
        public int getViewTypeCount() {
            return 3;
        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0 || position == mIngredientCount) {
                return HEADER_VIEW_TYPE;
            } else if (position < mIngredientCount + 1) {
                return INGREDIENT_VIEW_TYPE;
            } else {
                return STEP_VIEW_TYPE;
            }
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (getItemViewType(position) == HEADER_VIEW_TYPE) {
                convertView = mInflater.inflate(R.layout.detail_list_separator, null);
            } else {
                convertView = mInflater.inflate(android.R.layout.simple_list_item_1, null);
                if (getItemViewType(position) == INGREDIENT_VIEW_TYPE) {
                    convertView.setOnClickListener(null);
                } else if (getItemViewType(position) == STEP_VIEW_TYPE) {
                    convertView.setOnClickListener(
                            new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mCallback.onStepSelected(getIndex(position));
                                }
                            }
                    );
                }
            }
            return super.getView(position, convertView, parent);
        }

        private int getIndex(int position) {
            return position - FIXED_SECTION_HEADERS_COUNT - mIngredientCount + 1;
        }
    }
}
