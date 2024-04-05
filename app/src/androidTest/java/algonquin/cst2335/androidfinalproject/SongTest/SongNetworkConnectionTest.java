package algonquin.cst2335.androidfinalproject.SongTest;

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
public class SongNetworkConnectionTest {

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
}