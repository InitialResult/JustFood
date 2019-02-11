package com.justfood.justfood;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.justfood.justfood.adapters.RecipeBookAdapter;
import com.justfood.justfood.database.RecipeBook;
import com.justfood.justfood.database.RecipeBookDBHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeInfoActivity extends AppCompatActivity {
    private SQLiteDatabase dbWrite;
    private SQLiteDatabase dbRead;
    private String image;
    private String title;
    private String f2f_url;
    private Boolean isSaved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // User should see the links to how to cook.
        // First the database for this image and title if true background action changes
        // if the user clicks again it remove the row

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecipeBookDBHelper recipeBookDBHelper = new RecipeBookDBHelper(this);
        dbWrite = recipeBookDBHelper.getWritableDatabase();
        dbRead = recipeBookDBHelper.getReadableDatabase();

        image = getIntent().getStringExtra("image");
        title = getIntent().getStringExtra("title");
        f2f_url = getIntent().getStringExtra("f2f_url");

        ImageView imageView = findViewById(R.id.image_info);
        TextView textView = findViewById(R.id.title_info);

        Picasso.get().load(image).into(imageView);
        textView.setText(title);

        isSaved = checkIfRecipeIsSaved();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actions_recipe_details, menu);
        if (isSaved) {
            menu.getItem(0).setIcon(R.drawable.ic_action_like_saved);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.like:
                isSaved = checkIfRecipeIsSaved();

                if (isSaved == true) {
                    item.setIcon(R.drawable.ic_action_like);
                    // Define 'where' part of query.
                    String selection = RecipeBook.RecipeBookEntry.COLUMN_TITLE + " LIKE ?";
                    // Specify arguments in placeholder order.
                    String[] selectionArgs = { title };
                    // Issue SQL statement.
                    dbWrite.delete(RecipeBook.RecipeBookEntry.TABLE_NAME, selection, selectionArgs);
                } else if (isSaved == false) {
                    item.setIcon(R.drawable.ic_action_like_saved);
                    if (image.trim().length() == 0 || title.trim().length() == 0) {
                        return false;
                    }
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(RecipeBook.RecipeBookEntry.COLUMN_IMAGE, image);
                    contentValues.put(RecipeBook.RecipeBookEntry.COLUMN_TITLE, title);
                    contentValues.put(RecipeBook.RecipeBookEntry.COLUMN_F2F_URL, f2f_url);
                    dbWrite.insert(RecipeBook.RecipeBookEntry.TABLE_NAME, null, contentValues);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void link(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(f2f_url));
        startActivity(intent);
    }

    public boolean checkIfRecipeIsSaved() {
        String[] projection = {
                RecipeBook.RecipeBookEntry._ID,
                RecipeBook.RecipeBookEntry.COLUMN_TITLE,
        };

        // Filter results WHERE "title" = title
        String selection = RecipeBook.RecipeBookEntry.COLUMN_TITLE + " = ?";
        String[] selectionArgs = { title };

        String sortOrder =
                RecipeBook.RecipeBookEntry.COLUMN_TITLE + " DESC";

        Cursor cursor = dbRead.query(
                RecipeBook.RecipeBookEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        while(cursor.moveToNext()) {
            return true;
        }
        cursor.close();

        return false;
    }
}
