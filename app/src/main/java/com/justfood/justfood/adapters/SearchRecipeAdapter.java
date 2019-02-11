package com.justfood.justfood.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.justfood.justfood.R;
import com.justfood.justfood.models.Recipes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeAdapter.SearchRecipeAdapterHolder> {
    private Context context;
    private ArrayList<Recipes> cardList;
    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public SearchRecipeAdapter(Context context, ArrayList<Recipes> cardList) {
        this.context = context;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public SearchRecipeAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list, viewGroup, false);
        return new SearchRecipeAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecipeAdapterHolder searchRecipeAdapterHolder, int i) {
        Recipes recipes = cardList.get(i);
        String title = recipes.getTitle();
        String image = recipes.getImageUrl();

        searchRecipeAdapterHolder.title.setText(title);
        Picasso.get().load(image).into(searchRecipeAdapterHolder.image);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class SearchRecipeAdapterHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public SearchRecipeAdapterHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int i = getAdapterPosition();
                        if (i != RecyclerView.NO_POSITION) {
                            listener.onItemClick(i);
                        }
                    }
                }
            });
        }
    }
}