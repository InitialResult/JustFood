package com.justfood.justfood;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.justfood.justfood.adapters.RecipeBookAdapter;
import com.justfood.justfood.adapters.SearchRecipeAdapter;
import com.justfood.justfood.database.RecipeBook;
import com.justfood.justfood.database.RecipeBookDBHelper;
import com.justfood.justfood.models.Recipes;

import java.util.ArrayList;

public class RecipeBookFragment extends Fragment implements RecipeBookAdapter.onItemClickListener {
    private SQLiteDatabase db;
    private RecipeBookAdapter recipeBookAdapter;
    private ArrayList<Recipes> cardList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // User should be able to click the card to view in depth detail
        //

        View view = inflater.inflate(R.layout.fragment_recipe_book, container, false);

        RecipeBookDBHelper recipeBookDBHelper = new RecipeBookDBHelper(getContext());
        db = recipeBookDBHelper.getWritableDatabase();

        cardList = new ArrayList<>();

        Cursor cursor = parseSQLite();

        while(cursor.moveToNext()) {
            String title = cursor.getString(
                    cursor.getColumnIndexOrThrow(RecipeBook.RecipeBookEntry.COLUMN_TITLE));
            String imageUrl = cursor.getString(
                    cursor.getColumnIndexOrThrow(RecipeBook.RecipeBookEntry.COLUMN_IMAGE));
            String f2f_url = cursor.getString(
                    cursor.getColumnIndexOrThrow(RecipeBook.RecipeBookEntry.COLUMN_F2F_URL));

            cardList.add(new Recipes(title, imageUrl, f2f_url));
        }

        cursor.close();

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_recipe_book);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeBookAdapter = new RecipeBookAdapter(getContext(), parseSQLite(), cardList);
        recyclerView.setAdapter(recipeBookAdapter);
        recipeBookAdapter.setOnItemClickListener(this);

        return view;
    }

    private Cursor parseSQLite() {
        return db.query(
                RecipeBook.RecipeBookEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                RecipeBook.RecipeBookEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    @Override
    public void onItemClick(int i) {
        Intent intent = new Intent(getContext(), RecipeInfoActivity.class);
        Recipes recipesClicked = cardList.get(i);
        intent.putExtra("image", recipesClicked.getImageUrl());
        intent.putExtra("title", recipesClicked.getTitle());
        intent.putExtra("f2f_url", recipesClicked.getF2f_url());
        startActivity(intent);
    }
}
