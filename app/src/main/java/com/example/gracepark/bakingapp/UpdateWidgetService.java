package com.example.gracepark.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;

import com.example.gracepark.bakingapp.provider.RecipeContract;

import static com.example.gracepark.bakingapp.provider.RecipeContract.BASE_CONTENT_URI;
import static com.example.gracepark.bakingapp.provider.RecipeContract.PATH_RECIPES;

/**
 * Created by gracepark on 8/26/17.
 */

public class UpdateWidgetService extends IntentService {
    public static final String ACTION_UPDATE = "com.example.android.bakingapp.update_widget_service";
    public static final String EXTRA_RECIPE_ID = "recipe_id";

    public static final int NO_RECIPE_CHOSEN_ID = -1;

    public UpdateWidgetService() {
        super("UpdateWidgetService");
    }

    public static void startActionUpdate(Context context, int id) {
        Intent intent = new Intent(context, UpdateWidgetService.class);
        intent.setAction(ACTION_UPDATE);
        intent.putExtra(EXTRA_RECIPE_ID, id);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE.equals(action)) {
                if (intent.getIntExtra(EXTRA_RECIPE_ID, 0) == NO_RECIPE_CHOSEN_ID) {
                    // We don't have a recipe to display.
                    return;
                }
                handleActionUpdatePlantWidgets(intent.getIntExtra(EXTRA_RECIPE_ID, 0));
            }
        }
    }

        /**
         * Handle action UpdatePlantWidgets in the provided background thread
         */
        private void handleActionUpdatePlantWidgets(int id) {
            Uri PLANT_URI = RecipeContract.RecipeEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(id)).build();
            Cursor cursor = getContentResolver().query(
                    PLANT_URI,
                    null,
                    null,
                    null,
                    null
            );
            // Extract the plant details
            String name = "";
            String ingredients = "";
            String steps = "";
            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                int idIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry._ID);
                int nameIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_NAME);
                int ingredientsIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_INGREDIENTS);
                int stepsIndex = cursor.getColumnIndex(RecipeContract.RecipeEntry.COLUMN_STEPS);

                name = cursor.getString(nameIndex);
                ingredients = cursor.getString(ingredientsIndex);
                steps = cursor.getString(stepsIndex);

                cursor.close();
            }
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeWidgetProvider.class));
            //Trigger data update to handle the GridView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list);
            //Now update all widgets
            for (int appWidgetId : appWidgetIds) {
                RecipeWidgetProvider.updateAppWidget(this, appWidgetManager, appWidgetId, name, ingredients, steps);
            }

        }

}
