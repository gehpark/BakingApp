package com.example.gracepark.bakingapp;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.gracepark.bakingapp.provider.RecipeContract.RecipeEntry;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter to hold and inflate each of the recipes available.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private Cursor mCursor;
    private LayoutInflater mInflater;
    private final RecipeOnClickListener mListener;

    public RecipeListAdapter(Context context, Cursor cursor, RecipeOnClickListener recipeClickListener){
        this.mContext = context;
        this.mCursor = cursor;
        mInflater = LayoutInflater.from(context);
        this.mListener = recipeClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.panel_recipe, parent,false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        mCursor.moveToPosition(position);
        int nameIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_NAME);
        int imageIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_IMAGE);
        final int ingredientsIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_INGREDIENTS);
        final int stepsIndex = mCursor.getColumnIndex(RecipeEntry.COLUMN_STEPS);
        final String name = mCursor.getString(nameIndex);
        String image = mCursor.getString(imageIndex);

        final RecipeViewHolder myHolder = (RecipeViewHolder) holder;
        myHolder.recipeName.setText(name);

        if (!image.isEmpty()) {
            Picasso.with(mContext).load(image)
                    .error(R.drawable.img_error)
                    .into(myHolder.recipeImage);
        }

        myHolder.recipe.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListener.onRecipeClick(
                                mCursor.getPosition(),
                                name,
                                mCursor.getString(ingredientsIndex),
                                mCursor.getString(stepsIndex));
                    }
                }
        );
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) {
            mCursor.close();
        }
        mCursor = newCursor;
        if (mCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.recipe_name) TextView recipeName;
        @BindView(R.id.recipe_image)ImageView recipeImage;
        @BindView(R.id.recipe) LinearLayout recipe;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface RecipeOnClickListener {
        void onRecipeClick(int position, String name, String ingredients, String steps);
    }
}
