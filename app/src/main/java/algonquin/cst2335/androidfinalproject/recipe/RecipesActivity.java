package algonquin.cst2335.androidfinalproject.recipe;

import static java.util.Locale.filter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivityRecipesBinding;
import algonquin.cst2335.androidfinalproject.databinding.RecipeItemBinding;

public class RecipesActivity extends AppCompatActivity implements RecipesAdapter.OnClickRecipeListener {
    private static final String TAG = "RecipesActivity";
    private ActivityRecipesBinding binding;
    private RecipesViewModel viewModel;
    private ArrayList<Recipe> recipeList;
    private RecipesAdapter adapter;
    private RequestQueue mQueue;
    private RecipeDetailDAO mDAO;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));

        viewModel = new ViewModelProvider(this).get(RecipesViewModel.class);
        recipeList = viewModel.recipes.getValue();
        if(recipeList == null)  {
            viewModel.recipes.setValue(recipeList = new ArrayList<Recipe>());
        }

        binding.rvRecipes.setAdapter(adapter=new RecipesAdapter(recipeList, RecipesActivity.this));
        mQueue = Volley.newRequestQueue(getApplicationContext());

        RecipeDatabase db = Room.databaseBuilder(getApplicationContext(), RecipeDatabase.class, "database-name").build();
        mDAO = db.cmDAO();

        prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.search_recipe_menu, menu);
        SearchView searchView = (SearchView)menu.findItem(R.id.actionSearch)
                .getActionView();

        String query = prefs.getString("query", "");
        searchView.setQuery(query,false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String queryTrimed=query.trim();
                if(!queryTrimed.isEmpty()) {
                    filter(queryTrimed);
                    editor.putString("query",queryTrimed);
                    editor.apply();
                }
                else {
                    Toast.makeText(RecipesActivity.this,"Please enter a Recipe",Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        menu.findItem(R.id.actionFavorite).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                searchView.setQuery("", false); // Passing false as the second parameter to avoid triggering the submit event
                // Collapse the SearchView to hide the keyboard and clear focus
                searchView.clearFocus(); // Clears focus from the SearchView

                Executor thread = Executors.newSingleThreadExecutor();
                thread.execute(() ->
                {
                    List<Recipe> recipes = new ArrayList<Recipe>();

                    for (RecipeDetail rcp:mDAO.retrieveAll()) {
                        recipes.add(new Recipe(rcp));
                    }
                    recipeList.clear();
                    recipeList.addAll( recipes); //Once you get the data from database

                    runOnUiThread( () ->  {
                        adapter.notifyDataSetChanged();
                    }); //You can then load the RecyclerView
                });

                return false;
            }
        });
        return true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQueue.cancelAll(TAG);
    }

    private void filter(String query) {
        String url = String.format("https://api.spoonacular.com/recipes/complexSearch?query=%s&apiKey=%s",query,"c918ec43605843bfbbb1e58441d40b7c");
        JsonObjectRequest request  = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                recipeList.clear();
                List<Recipe> recipeParsed = parseResponse(response);
                if(recipeParsed.size()>0) {
                    recipeList.addAll(recipeParsed);
                }
                adapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, error.toString());
            }
        });

        mQueue.add(request);
    }

    private List<Recipe> parseResponse(JSONObject response) {
        List<Recipe> recipes = new ArrayList<>();

        try {
            JSONArray data = response.getJSONArray("results");

            for(int i = 0; i < data.length(); i++) {
                JSONObject r = data.getJSONObject(i);
                String title = r.getString("title");
                int id =r.getInt("id");
                String imageUrl = r.getString("image");
                recipes.add(new Recipe(id,title,imageUrl));
            }
            return recipes;
        } catch (JSONException e) {
            Log.e(TAG,"Error");
        }


        return null;
    }


    @Override
    public void onRecipeClick(long recipeId) {
        Log.d(TAG,"");
        Intent intent = new Intent(RecipesActivity.this,RecipeDetailActivity.class);
        intent.putExtra("recipe_id",recipeId);
        startActivity(intent);
    }
}