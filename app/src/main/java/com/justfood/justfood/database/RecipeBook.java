package com.justfood.justfood.database;

import android.provider.BaseColumns;

public class RecipeBook {

    private RecipeBook() {}

    public static final class RecipeBookEntry implements BaseColumns {
        public static final String TABLE_NAME = "recipeBook";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_F2F_URL = "f2f_url";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
