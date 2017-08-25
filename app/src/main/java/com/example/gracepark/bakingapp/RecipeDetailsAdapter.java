package com.example.gracepark.bakingapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Adapter to hold and inflate ingredients and short description of the steps.
 */

public class RecipeDetailsAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;
    private int mIngredientsCount;
    private RecipeDetailsFragment.OnStepClickListener mCallback;

    public RecipeDetailsAdapter(Context context, List<String> objects, LayoutInflater inflater, int ingredientsCount, RecipeDetailsFragment.OnStepClickListener listener) {
        super(context, 0, objects);
        mInflater = inflater;
        mCallback = listener;
        mIngredientsCount = ingredientsCount;
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
        if (position == 0 || position == mIngredientsCount + 1) {
            return HEADER_VIEW_TYPE;
        } else if (position < mIngredientsCount + 1) {
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
        return position - FIXED_SECTION_HEADERS_COUNT - mIngredientsCount;
    }
}