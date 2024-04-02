package algonquin.cst2335.androidfinalproject.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.ui.SunriseSunset.Externalapi.VolleyRequestQueue;

public class SunriseSunsetLookup extends AppCompatActivity {
    EditText latitudeEditText, longitudeEditText;
    TextView sunriseView, sunsetView;
    Button lookupButton, saveButton, deleteButton;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunrise_sunset_lookup);

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
