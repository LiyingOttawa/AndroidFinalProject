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

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.ItemViewHolder> {
    private final RecipesAdapter.OnClickRecipeListener activity;
    ArrayList<Recipe> recipeList;
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

    public static interface OnClickRecipeListener
    {
        void onRecipeClick(long recipeId);
    }

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
