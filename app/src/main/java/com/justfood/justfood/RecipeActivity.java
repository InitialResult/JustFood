package com.justfood.justfood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.justfood.justfood.models.Recipes;
import com.justfood.justfood.adapters.SearchRecipeAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class RecipeActivity extends AppCompatActivity implements SearchRecipeAdapter.onItemClickListener {
    private RecyclerView recyclerView;
    private RequestQueue queue;
    private SearchRecipeAdapter adapter;
    private ArrayList<Recipes> cardList;
    private String q;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cardList = new ArrayList<>();
        queue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        q = getIntent().getStringExtra("value");
        String url ="https://www.food2fork.com/api/search?key=60ff572d8972cfd0d4ce0cdc4fc52b38&q="+q;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("recipes");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject recipes = jsonArray.getJSONObject(i);

                                String title = recipes.getString("title");
                                String imageUrl = recipes.getString("image_url");
                                String f2f_url = recipes.getString("f2f_url");

                                cardList.add(new Recipes(title, imageUrl, f2f_url));
                            }
                            adapter = new SearchRecipeAdapter(RecipeActivity.this, cardList);
                            recyclerView.setAdapter(adapter);
                            adapter.setOnItemClickListener(RecipeActivity.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(request);
    }

    @Override
    public void onItemClick(int i) {
        Intent intent = new Intent(this, RecipeInfoActivity.class);
        Recipes recipesClicked = cardList.get(i);
        intent.putExtra("image", recipesClicked.getImageUrl());
        intent.putExtra("title", recipesClicked.getTitle());
        intent.putExtra("f2f_url", recipesClicked.getF2f_url());
        intent.putExtra("query", q);
        startActivity(intent);
    }
}
