package com.example.gracepark.bakingapp.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.gracepark.bakingapp.provider.RecipeContract.RecipeEntry;

public class RecipeDBHelper extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "recipedatabase.db";

        private static final int DATABASE_VERSION = 3;

        public RecipeDBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
            final String SQL_CREATE_PLANTS_TABLE = "CREATE TABLE " + RecipeEntry.TABLE_NAME + " (" +
                    RecipeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    RecipeEntry.COLUMN_NAME + " TEXT," +
                    RecipeEntry.COLUMN_IMAGE + " TEXT," +
                    RecipeEntry.COLUMN_INGREDIENTS + " TEXT," +
                    RecipeEntry.COLUMN_STEPS + " TEXT)";

            sqLiteDatabase.execSQL(SQL_CREATE_PLANTS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeEntry.TABLE_NAME);
            onCreate(sqLiteDatabase);
        }
    }