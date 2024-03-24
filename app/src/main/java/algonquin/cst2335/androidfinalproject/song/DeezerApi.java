package algonquin.cst2335.androidfinalproject.song;

import android.content.Context;
import android.util.Log;

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

public class DeezerApi {

    private static final String TAG = "DeezerApi";
    private static final String SEARCH_ARTIST_URL = "https://api.deezer.com/search/artist/?q=";

    public static void searchArtist(Context context, String artistName, final Callback<List<Song>> callback) {
        // Construct the URL
        String url = "https://api.deezer.com/search/artist/?q=" + artistName;

        // Create a JsonObjectRequest to make the network request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Parse the response and extract the list of songs
                        List<Song> songs = parseSongsFromResponse(response);

                        // Notify the callback with the list of songs
                        callback.onSuccess(songs);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Notify the callback about the error
                        callback.onError(error.getMessage());
                    }
                });

        // Add the request to the RequestQueue using RequestQueueSingleton
        RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    // Method to parse the response and extract the list of songs


    // Define the callback interface
    public interface Callback<T> {
        void onSuccess(T response);
        void onError(String errorMessage);
    }

    private static List<Song> parseSongsFromResponse(JSONObject response) {
        List<Song> songs = new ArrayList<>();

        try {
            JSONArray data = response.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject songObject = data.getJSONObject(i);

                // Extract song details from JSON object
                String title = songObject.getString("title");
                String album = songObject.getJSONObject("album").getString("title");
                String duration = songObject.getString("duration");

                // Create Song object and add it to the list
                Song song = new Song(title, album, duration);
                songs.add(song);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }

}
