package algonquin.cst2335.androidfinalproject.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.androidfinalproject.MainActivity;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivityRecipesBinding;
import algonquin.cst2335.androidfinalproject.databinding.ActivitySunriseSunsetLookupBinding;
import algonquin.cst2335.androidfinalproject.dictionary.DictActivity;
import algonquin.cst2335.androidfinalproject.recipe.RecipesActivity;
import algonquin.cst2335.androidfinalproject.song.MainSongActivity;
import algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Externalapi.VolleyRequestQueue;

public class SunriseSunsetLookup extends AppCompatActivity {
    EditText latitudeEditText, longitudeEditText;
    TextView sunriseView, sunsetView;
    Button lookupButton, saveButton, deleteButton;

    private SharedPreferences sharedPreferences;
    private ActivitySunriseSunsetLookupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySunriseSunsetLookupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.myToolbar);

        sunriseView = findViewById(R.id.sunriseView);
        sunsetView = findViewById(R.id.sunsetView);
        latitudeEditText = findViewById(R.id.latitudeEditText);
        longitudeEditText = findViewById(R.id.longitudeEditText);
        lookupButton = findViewById(R.id.lookupButton);
        saveButton = findViewById(R.id.saveButton);
        deleteButton = findViewById(R.id.DeleteButton);

        sharedPreferences = getSharedPreferences("SunriseSunsetPrefs", Context.MODE_PRIVATE);

        lookupButton.setOnClickListener(v -> lookupSunriseSunset());
        saveButton.setOnClickListener(v -> saveLocationToDatabase());
        deleteButton.setOnClickListener(v -> deleteFavoriteLocation());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.common_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.returnHomeMenu) {
            // Put your code for returning home here
            AlertDialog.Builder builder = new AlertDialog.Builder(SunriseSunsetLookup.this);
            builder.setMessage(getString(R.string.goToHomeSnack))
                    .setTitle(R.string.question)
                    .setNegativeButton(getString(R.string.reject), (a, b) -> {})
                    .setPositiveButton(getString(R.string.confirm), (a, b) -> {
                        Intent songSavedList = new Intent(SunriseSunsetLookup.this, MainActivity.class);
                        CharSequence text3 = getString(R.string.goToHomeSnack);
                        Toast.makeText(this, text3, Toast.LENGTH_SHORT).show();
                        startActivity(songSavedList);
                        Snackbar.make(binding.myToolbar, getString(R.string.goToHomeSnack), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), clk -> {
                                    Intent mainPage = new Intent(SunriseSunsetLookup.this, MainActivity.class);
                                    CharSequence text1 = getResources().getString(R.string.goToSunPage);
                                    Toast.makeText(this, text1, Toast.LENGTH_SHORT).show();
                                    startActivity(mainPage);
                                })
                                .show();
                    }).create().show();
            return true;
        } else if (id == R.id.showSaveList) {



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
            Toast.makeText(this, getString(R.string.version), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.menu_help) {
            // Code for showing help/instructions
            AlertDialog.Builder instructionsDialog = new AlertDialog.Builder(this);
            instructionsDialog.setMessage(R.string.SongAboutuse)
                    .setTitle(R.string.About_name)
                    .setNegativeButton(getString(R.string.confirm), (dialog, cl) -> {})
                    .create().show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void lookupSunriseSunset() {
        String latitude = latitudeEditText.getText().toString();
        String longitude = longitudeEditText.getText().toString();

        if (latitude.isEmpty() || longitude.isEmpty()) {
            Toast.makeText(this, "Please enter latitude and longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        String apiUrl = "https://api.sunrisesunset.io/json?lat=" + latitude + "&lng=" + longitude + "&timezone=UTC&date=today";

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, apiUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject results = response.getJSONObject("results");
                            String sunriseTime = results.getString("sunrise");
                            String sunsetTime = results.getString("sunset");
                            sunriseView.setText("Sunrise: " + sunriseTime);
                            sunsetView.setText("Sunset: " + sunsetTime);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SunriseSunsetLookup.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        VolleyRequestQueue.getInstance(this).addToRequestQueue(request);
    }

    private void saveLocationToDatabase() {
        // Implement saving location to database
    }

    private void deleteFavoriteLocation() {
        // Implement deletion of favorite location from database
    }
}
