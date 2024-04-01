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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.net.URLEncoder;
import android.util.Log;

public class DeezerApi {

    private static final String TAG = "DeezerApi";
    private static final String SEARCH_ARTIST_URL = "https://api.deezer.com/search/artist/?q=";

    // Define the callback interface
    public interface Callback<T> {
        void onSuccess(T response);
        void onError(String errorMessage);
    }

    public static void searchArtist(Context context, String artistName, final Callback<List<Song>> callback) {
        try {
            // Encode artistName
            String encodedArtistName = URLEncoder.encode(artistName, "UTF-8");

            // Construct the URL
            String url = "https://api.deezer.com/search/artist/?q=" + encodedArtistName;

            // Create a JsonObjectRequest to make the network request
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Parse the response and extract the list of songs
                            List<Song> songs = new ArrayList<>();
                            try {
                                JSONArray data = response.getJSONArray("data");

                                for (int i = 0; i < data.length(); i++) {
                                    JSONObject anAlbum = data.getJSONObject(i);
                                    String tracklistUrl = anAlbum.getString("tracklist");

                                    JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.GET,
                                            tracklistUrl, null,
                                            new Response.Listener<JSONObject>() {
                                                @Override
                                                public void onResponse(JSONObject response1) {
                                                    JSONArray tracks = null;
                                                    try {
                                                        tracks = response1.getJSONArray("data");
                                                        for (int k = 0; k < tracks.length(); k++){
                                                            JSONObject song = tracks.getJSONObject(k);
                                                            String title = song.getString("title");
                                                            int duration = song.getInt("duration");

                                                            JSONObject album = song.getJSONObject("album");
                                                            String name = album.getString("title");
                                                            String cover = album.getString("cover");

                                                            // Create DeezerSong object and add to the list
                                                            Song deezer = new Song(title, name, duration, cover);
                                                            songs.add(deezer);
                                                        }
                                                        // Notify the callback with the list of songs
                                                        callback.onSuccess(songs);
                                                    } catch (JSONException e) {
                                                        callback.onError(e.getMessage());
                                                    }
                                                }
                                            },
                                            new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    callback.onError(error.getMessage());
                                                }
                                            });
                                    // Add the request to the RequestQueue
                                    RequestQueueSingleton.getInstance(context).addToRequestQueue(request2);
                                }
                            } catch (JSONException e) {
                                callback.onError(e.getMessage());
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            callback.onError(error.getMessage());
                        }
                    });

            // Add the request to the RequestQueue using RequestQueueSingleton
            RequestQueueSingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }


    // Method to parse the response and extract the list of songs


    private static List<Song> parseSongsFromResponse(JSONObject response) {
        List<Song> songs = new ArrayList<>();

        try {
            JSONArray data = response.getJSONArray("data");

            for (int i = 0; i < data.length(); i++) {
                JSONObject songObject = data.getJSONObject(i);

                // Extract song details from JSON object
                String title = songObject.getString("title");
                String album = songObject.getJSONObject("album").getString("title");
                       album = songObject.getString("name");

                int duration = songObject.getInt("duration");
                String cover = songObject.getString("cover");

                // Create Song object and add it to the list
                Song song = new Song(title, album, duration, cover);
                songs.add(song);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }


}
