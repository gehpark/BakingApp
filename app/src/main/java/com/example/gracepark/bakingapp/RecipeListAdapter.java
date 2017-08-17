package com.example.gracepark.bakingapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gracepark.bakingapp.data.Recipe;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Adapter to hold and inflate each of the recipes available.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private Context mContext;
        private LayoutInflater mInflater;
        private final RecipeOnClickListener mListener;
        private List<Recipe> mRecipesList = Collections.emptyList();

        public RecipeListAdapter(Context context, List<Recipe> data, RecipeOnClickListener recipeClickListener){
            this.mContext = context;
            mInflater = LayoutInflater.from(context);
            this.mRecipesList = data;
            this.mListener = recipeClickListener;
        }

        public void setRecipeList(List<Recipe> data) {
            mRecipesList = data;
        }


        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.panel_recipe, parent,false);
            return new RecipeViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

            final RecipeViewHolder myHolder= (RecipeViewHolder) holder;
            Recipe recipe = mRecipesList.get(position);
            myHolder.recipeName.setText(recipe.name);

            if (!recipe.image.isEmpty()) {
                Picasso.with(mContext).load(recipe.image)
                        .error(R.drawable.img_error)
                        .into(myHolder.recipeImage);
            }

            myHolder.recipe.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mListener.onRecipeClick(mRecipesList.get(myHolder.getAdapterPosition()));
                        }
                    }
            );
        }

        @Override
        public int getItemCount() {
            return mRecipesList.size();
        }

        private class RecipeViewHolder extends RecyclerView.ViewHolder {

            TextView recipeName;
            ImageView recipeImage;
            LinearLayout recipe;

            public RecipeViewHolder(View itemView) {
                super(itemView);
                recipeImage= (ImageView) itemView.findViewById(R.id.recipe_image);
                recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
                recipe = (LinearLayout) itemView.findViewById(R.id.recipe);
            }
        }

        public interface RecipeOnClickListener {
            void onRecipeClick(Recipe recipe);
        }
}
