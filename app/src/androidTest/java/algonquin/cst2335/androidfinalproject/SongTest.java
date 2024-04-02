package algonquin.cst2335.androidfinalproject;

import android.content.Context;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;

import algonquin.cst2335.androidfinalproject.song.DeezerApi;
import algonquin.cst2335.androidfinalproject.song.Song;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

/**
 * Unit tests for the DeezerApi class.
 */
@RunWith(AndroidJUnit4.class)
public class SongTest {

    private DeezerApi deezerAPI;
    private Context context;

    /**
     * Set up the test environment before each test case.
     */
    @Before
    public void setUp() {
        deezerAPI = new DeezerApi();
        // Initialize the context for testing
        context = ApplicationProvider.getApplicationContext();
    }

    /**
     * Test case to verify the behavior when an empty search term is provided.
     */
    @Test
    public void testEmptySearchTerm() {
        String searchTerm = "";
        DeezerApi.Callback<List<Song>> callback = new DeezerApi.Callback<List<Song>>() {
            @Override
            public void onSuccess(List<Song> response) {
                // This should not be called for this test
                Assert.fail("Callback onSuccess() should not be called for empty search term");
            }

            @Override
            public void onError(String errorMessage) {
                Assert.assertEquals("Error: Empty search term", errorMessage);
            }
        };
        deezerAPI.searchArtist(context, searchTerm, callback);
    }

    /**
     * Test case to verify the behavior when a valid search term is provided.
     */
    @Test
    public void testValidSearchTerm() {
        String searchTerm = "Artist Name";
        DeezerApi.Callback<List<Song>> callback = new DeezerApi.Callback<List<Song>>() {
            @Override
            public void onSuccess(List<Song> response) {
                // Assuming the response is not empty
                Assert.assertTrue("Response should contain at least one song", !response.isEmpty());
            }

            @Override
            public void onError(String errorMessage) {
                Assert.fail("Callback onError() should not be called for valid search term");
            }
        };
        deezerAPI.searchArtist(context, searchTerm, callback);
    }

    /**
     * Test case to verify the behavior when an invalid search term is provided.
     */
    @Test
    public void testInvalidSearchTerm() {
        String searchTerm = "!@#$%";
        DeezerApi.Callback<List<Song>> callback = new DeezerApi.Callback<List<Song>>() {
            @Override
            public void onSuccess(List<Song> response) {
                Assert.fail("Callback onSuccess() should not be called for invalid search term");
            }

            @Override
            public void onError(String errorMessage) {
                Assert.assertEquals("Error: Invalid search term", errorMessage);
            }
        };
        deezerAPI.searchArtist(context, searchTerm, callback);
    }

    /**
     * Test case to verify the behavior when there is no network connection.
     */
    @Test
    public void testNetworkConnection() {
        // Simulate no internet connection
        String searchTerm = "Artist Name";
        DeezerApi.Callback<List<Song>> callback = new DeezerApi.Callback<List<Song>>() {
            @Override
            public void onSuccess(List<Song> response) {
                Assert.fail("Callback onSuccess() should not be called for no network connection");
            }

            @Override
            public void onError(String errorMessage) {
                Assert.assertEquals("Error: No internet connection", errorMessage);
            }
        };
        deezerAPI.searchArtist(context, searchTerm, callback);
    }

    /**
     * Test case to verify the behavior when the request times out.
     */
    @Test(timeout = 5000)
    public void testTimeout() {
        String searchTerm = "Artist Name";
        DeezerApi.Callback<List<Song>> callback = new DeezerApi.Callback<List<Song>>() {
            @Override
            public void onSuccess(List<Song> response) {
                Assert.fail("Callback onSuccess() should not be called for timeout");
            }

            @Override
            public void onError(String errorMessage) {
                Assert.assertEquals("Error: Request timed out", errorMessage);
            }
        };
        deezerAPI.searchArtist(context, searchTerm, callback);
    }
}