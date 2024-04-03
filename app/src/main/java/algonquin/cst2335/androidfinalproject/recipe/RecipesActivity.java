/**
 * Filename: RecipesActivity.java
 * Purpose:This activity displays a list of recipes and allows users to search for recipes, view details, and save favorites.
 * Author: Liying Guo
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 */
package algonquin.cst2335.androidfinalproject.recipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;
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
import com.google.android.material.snackbar.Snackbar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import algonquin.cst2335.androidfinalproject.MainActivity;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivityRecipesBinding;
import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;
import algonquin.cst2335.androidfinalproject.song.MainSongActivity;
import algonquin.cst2335.androidfinalproject.ui.SunriseSunsetLookup;

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
    private boolean showFavorite=false;

    /**
     * Called when the activity is starting.
     * Sets up the activity layout, initializes the ViewModel, and retrieves saved search query from SharedPreferences.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down,
     *                           this Bundle contains the data it most recently supplied in
     *                           {@link #onSaveInstanceState(Bundle)}. Otherwise, it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecipesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        binding.rvRecipes.setLayoutManager(new LinearLayoutManager(this));
        SearchView searchView=binding.searchView;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                showFavorite = false;
                searchView.clearFocus();
                String queryTrimed=query.trim();
                if(!queryTrimed.isEmpty()) {
                    filter(queryTrimed);
                    editor.putString("query",queryTrimed);
                    editor.apply();
                }
                else {
                    Toast.makeText(RecipesActivity.this,getString(R.string.toastEmpty),Toast.LENGTH_SHORT).show();
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
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

        String query = prefs.getString("query", "");
        searchView.setQuery(query,false);

        checkEmpty();
    }

    /**
     * Initialize the contents of the Activity's standard options menu.
     * This is only called once, the first time the options menu is displayed.
     *
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    /**
     * Handle the selection of items in the options menu.
     * @param item The menu item that was selected.
     * @return Return false to allow normal menu processing to proceed, true to consume it here.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.returnHomeMenu) {
            // Put your code for returning home here
            AlertDialog.Builder builder = new AlertDialog.Builder(RecipesActivity.this);
            builder.setMessage(getString(R.string.goToHomeSnack))
                    .setTitle(R.string.question)
                    .setNegativeButton(getString(R.string.reject), (a, b) -> {})
                    .setPositiveButton(getString(R.string.confirm), (a, b) -> {
                        Intent songSavedList = new Intent(RecipesActivity.this, MainActivity.class);
                        CharSequence text3 = getString(R.string.goToHomeSnack);
                        Toast.makeText(this, text3, Toast.LENGTH_SHORT).show();
                        startActivity(songSavedList);
                        Snackbar.make(binding.myToolbar, getString(R.string.goToHomeSnack), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), clk -> {
                                    Intent mainPage = new Intent(RecipesActivity.this, MainActivity.class);
                                    CharSequence text1 = getResources().getString(R.string.goToSunPage);
                                    Toast.makeText(this, text1, Toast.LENGTH_SHORT).show();
                                    startActivity(mainPage);
                                })
                                .show();
                    }).create().show();
            return true;
        } else if (id == R.id.showSaveList) {
            showFavorite=true;
            showFavorite();
            return true;
        }
        else if(id==R.id.goingTosunApp)
        {
            Intent intent = new Intent(this, SunriseSunsetLookup.class); // Assuming SearchDictActivity is the correct class name
            startActivity(intent);
            return true;
        }
        else if(id==R.id.ritaRecipePage)
        {

            return true;
        }
        else if(id==R.id.ritaDictionary)
        {
            Intent intent = new Intent(this, DictActivity.class); // Assuming SearchDictActivity is the correct class name
            startActivity(intent);
            return true;
        }
        else if(id==R.id.goingToSongApp)
        {
            Intent intent = new Intent(this, MainSongActivity.class); // Assuming SearchDictActivity is the correct class name
            startActivity(intent);
            return true;
        }
        else if (id == R.id.menu_about) {
            // Code for showing the version info
            Toast.makeText(this, getString(R.string.toastVersion), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_help) {
            // Code for showing help/instructions
            AlertDialog.Builder instructionsDialog = new AlertDialog.Builder(this);
            instructionsDialog.setMessage(R.string.recipeInstruction)
                    .setTitle(R.string.About_name)
                    .setNegativeButton(getString(R.string.confirm), (dialog, cl) -> {})
                    .create().show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void showFavorite() {
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
                checkEmpty();
                CharSequence showText = getString(R.string.goToSaveList);
                Toast.makeText(this, showText, Toast.LENGTH_SHORT).show();
            }); //You can then load the RecyclerView
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        mQueue.cancelAll(TAG);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(showFavorite)
        {
            showFavorite();
        }
    }

    private void filter(String query) {
        mQueue.cancelAll(TAG);
        String url = String.format("https://api.spoonacular.com/recipes/complexSearch?query=%s&apiKey=%s",query,Constants.RECEIPE_API );
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
                checkEmpty();
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

    private void checkEmpty()
    {
        // Check if the adapter has no items
        if (adapter.getItemCount() == 0) {
            binding.emptyTextView.setVisibility(View.VISIBLE);
            binding.rvRecipes.setVisibility(View.GONE);
        } else {
            binding.emptyTextView.setVisibility(View.GONE);
            binding.rvRecipes.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRecipeClick(long recipeId) {
        Log.d(TAG,"");
        Intent intent = new Intent(RecipesActivity.this,RecipeDetailActivity.class);
        intent.putExtra("recipe_id",recipeId);
        startActivity(intent);
    }
}