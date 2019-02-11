package com.justfood.justfood.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.justfood.justfood.R;
import com.justfood.justfood.database.RecipeBook;
import com.justfood.justfood.models.Recipes;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecipeBookAdapter extends RecyclerView.Adapter<RecipeBookAdapter.RecipeBookViewHolder>{
    private Context context;
    private Cursor cursor;
    private ArrayList<Recipes> cardList;
    private onItemClickListener listener;

    public interface onItemClickListener {
        void onItemClick(int i);
    }

    public void setOnItemClickListener(onItemClickListener listener) {
        this.listener = listener;
    }


    public RecipeBookAdapter(Context context, Cursor cursor, ArrayList<Recipes> cardList) {
        this.context = context;
        this.cursor = cursor;
        this.cardList = cardList;
    }

    @NonNull
    @Override
    public RecipeBookViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.recipe_list, viewGroup, false);
        return new RecipeBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeBookViewHolder recipeBookViewHolder, int i) {
        if (!cursor.moveToPosition(i)) {
            return;
        }

        String image = cursor.getString(cursor.getColumnIndex(RecipeBook.RecipeBookEntry.COLUMN_IMAGE));
        String title = cursor.getString(cursor.getColumnIndex(RecipeBook.RecipeBookEntry.COLUMN_TITLE));

        Picasso.get().load(image).into(recipeBookViewHolder.image);
        recipeBookViewHolder.title.setText(title);
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    public class RecipeBookViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;

        public RecipeBookViewHolder(@NonNull View itemView) {
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
