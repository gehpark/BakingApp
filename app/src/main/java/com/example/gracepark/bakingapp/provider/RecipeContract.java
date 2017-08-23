package com.example.gracepark.bakingapp.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class RecipeContract {

    public static final String AUTHORITY = "com.example.gracepark.bakingapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final String PATH_RECIPES = "recipes";

    public static final class RecipeEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RECIPES).build();

        public static final String TABLE_NAME = "recipesTable";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_INGREDIENTS = "ingredients";
        public static final String COLUMN_STEPS = "shortSteps";
    }
}