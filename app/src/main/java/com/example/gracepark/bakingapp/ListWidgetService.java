package com.example.gracepark.bakingapp;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_INGREDIENTS;

/**
 * Created by gracepark on 8/25/17.
 */

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        ArrayList<String> s = RecipeDetailsActivity.getRecipeIngredients(intent.getStringExtra(EXTRA_RECIPE_INGREDIENTS));
        return new ListRemoteViewFactory(this.getApplicationContext(), s);
    }
}

class ListRemoteViewFactory implements RemoteViewsService.RemoteViewsFactory {

    Context mContext;
    ArrayList<String> mIngredients;

    public ListRemoteViewFactory(Context applicationContext, ArrayList<String> ingredients) {
        mContext = applicationContext;
        mIngredients = ingredients;
    }

    @Override
    public void onCreate() {

    }

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public int getCount() {
        return mIngredients.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_row);
        views.setTextViewText(R.id.widget_row_text, mIngredients.get(position));
        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
