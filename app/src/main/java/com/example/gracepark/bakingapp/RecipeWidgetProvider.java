package com.example.gracepark.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_INGREDIENTS;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_NAME;
import static com.example.gracepark.bakingapp.RecipeListActivity.EXTRA_RECIPE_STEPS;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        Bundle extras = new Bundle();
        extras.putString(EXTRA_RECIPE_NAME, "Recipe name");
        extras.putString(EXTRA_RECIPE_INGREDIENTS, "[]");
        extras.putString(EXTRA_RECIPE_STEPS, "[]");
        intent.putExtras(extras);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget);
        views.setOnClickPendingIntent(R.id.widget_layout, pendingIntent);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

