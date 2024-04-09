package algonquin.cst2335.androidfinalproject.song;
/*
 * Filename: DeezerApi.java
 * Author: Zhaoguo Han
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 * Purpose: Handles communication with the Deezer API to search for artists and retrieve their songs.
 */

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * DeezerApi class handles the communication with the Deezer API to search for artists and retrieve their songs.
 */
public class DeezerApi {

    private static final String TAG = "DeezerApi";
    private static final String SEARCH_ARTIST_URL = "https://api.deezer.com/search/artist/?q=";

    /**
     * Callback interface for handling asynchronous responses.
     *
     * @param <T> Type of the response object
     */
    public interface Callback<T> {
        /**
         * Called when the request is successful.
         *
         * @param response The response object
         */
        void onSuccess(T response);

        /**
         * Called when the request encounters an error.
         *
         * @param errorMessage The error message
         */
        void onError(String errorMessage);
    }

    /**
     * Searches for an artist on Deezer API and retrieves their songs.
     *
     * @param context    The context of the calling activity
     * @param artistName The name of the artist to search for
     * @param callback   The callback to handle the response
     */
    public static void searchArtist(Context context, String artistName, final Callback<List<Song>> callback) {
        try {
            // Encode artistName
            String encodedArtistName = URLEncoder.encode(artistName, "UTF-8");

            // Construct the URL
            String url = SEARCH_ARTIST_URL + encodedArtistName;

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
                                                        for (int k = 0; k < tracks.length(); k++) {
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
}
