package algonquin.cst2335.androidfinalproject.dictionary;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import algonquin.cst2335.androidfinalproject.MainActivity;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivityDictBinding;
import algonquin.cst2335.androidfinalproject.databinding.SearchDictBinding;
import algonquin.cst2335.androidfinalproject.recipe.RecipesActivity;
import algonquin.cst2335.androidfinalproject.song.MainSongActivity;

/**
 * DictActivity serves as the main interface for the dictionary section of the application.
 * It facilitates word searches, displays definitions, and supports the saving and deleting
 * of entries, as well as navigation to other sections of the application. It inherits from AppCompatActivity.
 */
public class DictActivity extends AppCompatActivity {
    private ActivityDictBinding binding;
    private ArrayList<Dict> dicts = new ArrayList<>();
    private DictViewModel dictModel;
    private RecyclerView.Adapter<MyRowHolder> dictAdapter;
    private DictDAO dictDAO;
    private RequestQueue queue;

    /**
     * Initializes the activity, setting up the user interface and loading any saved preferences.
     * @param savedInstanceState If the activity is being re-initialized after previously being shut down, this Bundle contains the data it most recently supplied.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDictBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initializeComponents();
        observeSelectedDicts();
        loadDictsFromDatabase();
        setupDictSearchButton();
        setupRecyclerView();
    }

    /**
     * Initializes components used in the activity.
     */
    private void initializeComponents() {
        queue = Volley.newRequestQueue(this);
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.dictTextInput.setText(prefs.getString("dictName", ""));
        setSupportActionBar(binding.toolbar);
        dictModel = new ViewModelProvider(this).get(DictViewModel.class);
        DictDatabase db = Room.databaseBuilder(getApplicationContext(), DictDatabase.class, "dictdb").build();
        dictDAO = db.dictDAO();
    }

    /**
     * Observes selected dictionaries and updates the UI accordingly.
     */
    private void observeSelectedDicts() {
        dictModel.selectedDicts.observe(this, selectedDict -> {
            if (selectedDict != null) {
                displayDictDetails(selectedDict);
            }
        });
    }


    /**
     * Loads dictionaries from the database asynchronously.
     */
    private void loadDictsFromDatabase() {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> {
            dicts.addAll(dictDAO.getAllDicts());
            runOnUiThread(() -> dictAdapter.notifyDataSetChanged());
        });
    }

    /**
     * Sets up the click listener for the dictionary search button.
     */
    private void setupDictSearchButton() {
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        binding.dictSearchButton.setOnClickListener(v -> {
            String searchText = binding.dictTextInput.getText().toString().trim();
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("dictName", searchText);
            editor.apply();
            fetchDictionaryData(searchText);
        });
    }

    /**
     * Fetches dictionary data from an API based on the user's search input.
     * @param searchText The text to search for in the dictionary API.
     */
    private void fetchDictionaryData(String searchText) {
        String url = "https://api.dictionaryapi.dev/api/v2/entries/en/" + encodeText(searchText);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                this::processApiResponse,
                error -> {
                    Log.d("DictActivity", error.toString());
                    Toast.makeText(DictActivity.this, R.string.dict_notFoundToast, Toast.LENGTH_SHORT).show();
                });
        queue.add(request);
    }

    /**
     * Processes the API response, updating the UI with the results.
     * @param response The JSON response from the dictionary API.
     */
    private void processApiResponse(JSONArray response) {
        try {
            dicts.clear();
            parseJsonResponse(response);
            dictAdapter.notifyDataSetChanged();
            binding.dictTitleText.setText(binding.dictTextInput.getText().toString().trim());
        } catch (JSONException e) {
            Log.e("DictActivity", "Error parsing JSON response", e);
            Toast.makeText(DictActivity.this, R.string.dict_notAvailableToast, Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Encodes the text for URL usage.
     * @param text The text to encode.
     * @return The encoded text.
     */
    private String encodeText(String text) {
        try {
            return URLEncoder.encode(text, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Error encoding the URL", e);
        }
    }

    /**
     * Parses the JSON response from the dictionary API and updates the list of dictionaries.
     * @param response The JSON response to parse.
     * @throws JSONException If there is an error parsing the JSON.
     */
    private void parseJsonResponse(JSONArray response) throws JSONException {
        for (int i = 0; i < response.length(); i++) {
            JSONObject wordObject = response.getJSONObject(i);
            String word = wordObject.getString("word");
            JSONArray meanings = wordObject.getJSONArray("meanings");
            for (int j = 0; j < meanings.length(); j++) {
                JSONObject meaning = meanings.getJSONObject(j);
                JSONArray definitions = meaning.getJSONArray("definitions");
                for (int k = 0; k < definitions.length(); k++) {
                    String definition = definitions.getJSONObject(k).getString("definition");
                    dicts.add(new Dict(word, definition));
                }
            }
        }
    }

    /**
     * Sets up the RecyclerView for displaying dictionaries.
     */
    private void setupRecyclerView() {
        dictAdapter = new RecyclerView.Adapter<MyRowHolder>() {
            @NonNull
            @Override
            public MyRowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                SearchDictBinding binding = SearchDictBinding.inflate(getLayoutInflater(), parent, false);
                return new MyRowHolder(binding.getRoot());
            }

            @Override
            public void onBindViewHolder(@NonNull MyRowHolder holder, int position) {
                Dict dict = dicts.get(position);
                holder.dictName.setText(String.format("%s: %s...", dict.getDictName(), dict.getSummary()));
            }

            @Override
            public int getItemCount() {
                return dicts.size();
            }
        };
        binding.dictRecycleView.setLayoutManager(new LinearLayoutManager(this));
        binding.dictRecycleView.setAdapter(dictAdapter);
    }

    /**
     * Displays the details of a selected dictionary entry.
     * @param selectedDict The selected dictionary entry.
     */
    private void displayDictDetails(Dict selectedDict) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(selectedDict.getDictName()); // Set the title of the dialog to the dictionary name
        dialogBuilder.setMessage(selectedDict.getDictDefinition()); // Set the message of the dialog to the dictionary definition

        dialogBuilder.setPositiveButton(R.string.dict_addItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveDefinition(selectedDict); // Save the definition
                Toast.makeText(DictActivity.this, "Definition saved!", Toast.LENGTH_SHORT).show(); // Show confirmation toast
            }
        });

        dialogBuilder.setNegativeButton(R.string.dict_deleteItem, (dialog, which) -> {
            // Temporarily save the deleted item in case the user wants to undo the deletion
            final Dict tempDeletedDict = selectedDict;

            // Delete the definition from the database
            deleteDefinition(tempDeletedDict);

            // Show a Snackbar with an Undo option instead of a Toast message
            Snackbar.make(findViewById(android.R.id.content), "Definition deleted", Snackbar.LENGTH_LONG)
                    .setAction("Undo", view -> {
                        // Undo the deletion: save the deleted item back to the database
                        saveDefinition(tempDeletedDict);
                        Toast.makeText(DictActivity.this, "Deletion undone!", Toast.LENGTH_SHORT).show();
                    }).show();
        });

        dialogBuilder.show();
    }


    private void deleteDefinition(Dict dict) {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> dictDAO.deleteDict(dict));
    }


    /**
     * Saves the given dictionary entry to the database.
     * @param dict The dictionary entry to save.
     */
    public void saveDefinition(Dict dict) {
        Executor thread = Executors.newSingleThreadExecutor();
        thread.execute(() -> dictDAO.insertDict(dict));
    }

    /**
     * Inflates the menu options from the specified resource.
     * @param menu The options menu in which items are placed.
     * @return True for the menu to be displayed; otherwise, false.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true; // Return true to display the menu
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.returnHomeMenu) {
            // Put your code for returning home here
            AlertDialog.Builder builder = new AlertDialog.Builder(DictActivity.this);
            builder.setMessage(getString(R.string.goToHomeSnack))
                    .setTitle(R.string.question)
                    .setNegativeButton(getString(R.string.reject), (a, b) -> {})
                    .setPositiveButton(getString(R.string.confirm), (a, b) -> {
                        Intent songSavedList = new Intent(DictActivity.this, MainActivity.class);
                        CharSequence text3 = getString(R.string.goToHomeSnack);
                        Toast.makeText(this, text3, Toast.LENGTH_SHORT).show();
                        startActivity(songSavedList);
                        Snackbar.make(binding.toolbar, getString(R.string.goToHomeSnack), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), clk -> {
                                    Intent mainPage = new Intent(DictActivity.this, MainActivity.class);
                                    CharSequence text1 = getResources().getString(R.string.goToSunPage);
                                    Toast.makeText(this, text1, Toast.LENGTH_SHORT).show();
                                    startActivity(mainPage);
                                })
                                .show();
                    }).create().show();
            return true;
        } else if (id == R.id.showSaveList) {
            Intent intent = new Intent(this, DictActivity.class); // Assuming SearchDictActivity is the correct class name
            startActivity(intent);

            return true;
        }
        else if(id==R.id.goingTosunApp)
        {

            return true;
        }
        else if(id==R.id.ritaRecipePage)
        {
            Intent intent = new Intent(this, RecipesActivity.class); // Assuming SearchDictActivity is the correct class name
            startActivity(intent);
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
            Toast.makeText(this, getString(R.string.dict_aboutToast), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_help) {
            // Code for showing help/instructions
            AlertDialog.Builder instructionsDialog = new AlertDialog.Builder(this);
            instructionsDialog.setMessage(R.string.dict_helpAlert)
                    .setTitle(R.string.About_name)
                    .setNegativeButton(getString(R.string.confirm), (dialog, cl) -> {})
                    .create().show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * ViewHolder class for the dictionary RecyclerView.
     */
    class MyRowHolder extends RecyclerView.ViewHolder {
        TextView dictName;

        public MyRowHolder(View itemView) {
            super(itemView);
            dictName = itemView.findViewById(R.id.dictResult);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("xx","");
                    int position = getAdapterPosition();
                    Dict selected = dicts.get(position);
                    dictModel.selectedDicts.postValue(selected);
                }
            });
        }
    }
}
