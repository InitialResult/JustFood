package com.justfood.justfood.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class RecipeBookDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "recipeBook.db";
    public static final int DATABASE_VERSION = 1;

    public RecipeBookDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_RECIPE_BOOK_TABLE = "CREATE TABLE " +
                RecipeBook.RecipeBookEntry.TABLE_NAME + " (" +
                RecipeBook.RecipeBookEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                RecipeBook.RecipeBookEntry.COLUMN_IMAGE + " TEXT NOT NULL, " +
                RecipeBook.RecipeBookEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                RecipeBook.RecipeBookEntry.COLUMN_F2F_URL + " TEXT NOT NULL, " +
                RecipeBook.RecipeBookEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        sqLiteDatabase.execSQL(SQL_CREATE_RECIPE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + RecipeBook.RecipeBookEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
