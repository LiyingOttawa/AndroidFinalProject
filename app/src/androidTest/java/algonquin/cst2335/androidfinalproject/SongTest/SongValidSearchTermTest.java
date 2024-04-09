package algonquin.cst2335.androidfinalproject.SongTest;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import algonquin.cst2335.androidfinalproject.song.DeezerApi;
import algonquin.cst2335.androidfinalproject.song.Song;

/**
 * Unit tests for the DeezerApi class.
 */
@RunWith(AndroidJUnit4.class)
public class SongValidSearchTermTest {

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

}