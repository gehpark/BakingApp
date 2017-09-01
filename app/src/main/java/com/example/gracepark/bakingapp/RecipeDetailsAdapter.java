package com.example.gracepark.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter to hold and inflate ingredients and short description of the steps.
 */

public class RecipeDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<String> mDetailItems;
    private int mIngredientsCount;
    private RecipeDetailsFragment.OnStepClickListener mCallback;

    public RecipeDetailsAdapter(List<String> objects, LayoutInflater inflater, int ingredientsCount, RecipeDetailsFragment.OnStepClickListener listener) {
        mInflater = inflater;
        mCallback = listener;
        mDetailItems = objects;
        mIngredientsCount = ingredientsCount;
    }

    private final int HEADER_VIEW_TYPE = 0;
    private final int INGREDIENT_VIEW_TYPE = 1;
    private final int STEP_VIEW_TYPE = 2;

    private final int FIXED_SECTION_HEADERS_COUNT = 2;

    @Override
    public int getItemCount() {
        return mDetailItems.size();
    }


    private int getIndex(int position) {
        return position - FIXED_SECTION_HEADERS_COUNT - mIngredientsCount;
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


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == HEADER_VIEW_TYPE) {
            View view = mInflater.inflate(R.layout.detail_list_separator, viewGroup, false);
            return new RecipeDetailViewHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.detail_list_item, viewGroup, false);
            return new RecipeDetailViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final RecipeDetailViewHolder myHolder = (RecipeDetailViewHolder) holder;
        myHolder.text1.setText(mDetailItems.get(position));

        if (getItemViewType(position) == HEADER_VIEW_TYPE) {
            myHolder.panel.setOnClickListener(null);
        } else if (getItemViewType(position) == STEP_VIEW_TYPE) {

            myHolder.panel.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mCallback.onStepSelected(getIndex(myHolder.getAdapterPosition()));
                        }
                    }
            );
        }
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text1)
        TextView text1;
        LinearLayout panel;

        public RecipeDetailViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            panel = (LinearLayout) itemView.findViewById(R.id.detail_panel);
        }
    }
}