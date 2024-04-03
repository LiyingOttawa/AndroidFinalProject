/*
 * Filename: RecipesAdapter.java
 * Purpose: Defines a RecyclerView adapter for displaying a list of recipes.
 * Author: Liying Guo
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 */
package algonquin.cst2335.androidfinalproject.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import algonquin.cst2335.androidfinalproject.databinding.RecipeItemBinding;

/**
 * RecyclerView adapter for displaying a list of recipes.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ItemViewHolder> {
    private final RecipesAdapter.OnClickRecipeListener activity;
    ArrayList<Recipe> recipeList;

    /**
     * Constructs a new RecipesAdapter.
     * @param recipeList The list of recipes to display.
     * @param activity The activity implementing OnClickRecipeListener.
     */
    public RecipesAdapter(ArrayList<Recipe> recipeList, RecipesAdapter.OnClickRecipeListener activity)
    {
        this.recipeList= recipeList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeItemBinding binding = RecipeItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding, activity);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.recipeTitle.setText(recipe.getTitle());
        Picasso.get().load(recipe.getImage()).into(holder.recipeImg);
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    /**
     * Interface to handle click events on recipe items.
     */
    public static interface OnClickRecipeListener
    {
        /**
         * Called when a recipe item is clicked.
         * @param recipeId The ID of the clicked recipe.
         */
        void onRecipeClick(long recipeId);
    }

    /**
     * ViewHolder class for the recipe item views.
     */
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        public ImageView recipeImg;
        public TextView recipeTitle;
        public ItemViewHolder(@NonNull RecipeItemBinding binding, RecipesAdapter.OnClickRecipeListener handler) {
            super(binding.getRoot());
            recipeImg = binding.imgRecipeItem;
            recipeTitle = binding.tvTitle;
            this.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long recipeId = recipeList.get(getAdapterPosition()).getId();
                    handler.onRecipeClick(recipeId);
                }
            });
        }
    }
}
