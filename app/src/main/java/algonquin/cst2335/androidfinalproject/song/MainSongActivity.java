package algonquin.cst2335.androidfinalproject.song;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import algonquin.cst2335.androidfinalproject.MainActivity;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivitySongMainBinding;
import algonquin.cst2335.androidfinalproject.song.SongDao;
import algonquin.cst2335.androidfinalproject.song.SongAdapter;


public class MainSongActivity extends AppCompatActivity implements SongAdapter.OnFavoriteClickListener {
    private EditText editTextSearch;
    private Button buttonSearch;
    protected Button buttonAddToFavorites;
    protected Button buttonMyFavorites;
    private RecyclerView recyclerViewResults;
    private SongAdapter songAdapter;

    private SongDao songDAO;
    private boolean showFavorites = false;
    protected ActivitySongMainBinding binding;

    private static final String DB_NAME = "song_DB";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySongMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.mysongToolbar);

        SharedPreferences prefer = getSharedPreferences("Search History", Context.MODE_PRIVATE);
        AtomicReference<EditText> searchText = new AtomicReference<>(binding.editTextSearch);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonAddToFavorites = findViewById(R.id.buttonAddToFavorites);
        buttonMyFavorites = findViewById(R.id.buttonMyFavorites);
        recyclerViewResults = findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));

        // Initialize SongAdapter
        songAdapter = new SongAdapter();
        // Set the SongDao for the adapter
        songAdapter.setSongDao(songDAO);

        recyclerViewResults.setAdapter(songAdapter);

        songAdapter.setOnFavoriteClickListener(this); // Set the activity as the listener

        initDatabase();
        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String artistName = editTextSearch.getText().toString().trim();
                if (!artistName.isEmpty()) {
                    // Perform search operation
                    performSearch(artistName);
                } else {
                    Toast.makeText(MainSongActivity.this, "Please enter an artist name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        buttonMyFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFavorites = !showFavorites;
                if (showFavorites) {
                    // Display favorite songs
                    displayFavoriteSongs();
                } else {
                    // Display search results
                    performSearch(editTextSearch.getText().toString().trim());
                }
            }
        });
        // Set click listener for the RecyclerView item buttons
    }
    private void initDatabase() {
        // Initialize database and DAO
        SongDatabase db = Room.databaseBuilder(getApplicationContext(),
                        SongDatabase.class, DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
        songDAO = db.songDao();
        songAdapter.setSongDao(songDAO);
    }

    @Override
    public void onFavoriteClick(Song song) {
        // Handle favorite button click
        Toast.makeText(this, "Added to Favorites: " + song.getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void performSearch(String artistName) {

        songAdapter.setShowAddButton(true);
        songAdapter.setShowDeleteButton(false);

        // Fetch data from Deezer API using Volley
        DeezerApi.searchArtist(getApplicationContext(), artistName, new DeezerApi.Callback<List<Song>>() {
            @Override
            public void onSuccess(List<Song> songs) {
                // Update RecyclerView with search results
                songAdapter.setSongList(songs);
            }
            @Override
            public void onError(String errorMessage) {
                Toast.makeText(MainSongActivity.this, "Error: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Method to display favorite songs
    private void displayFavoriteSongs() {

        // show the Delete Button
        songAdapter.setShowDeleteButton(true);
        songAdapter.setShowAddButton(false);
        // Use AsyncTask or a background thread to perform the database query asynchronously
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Retrieve favorite songs from the database using the DAO
                List<Song> favoriteSongs = songDAO.getAllSongs();
                // Update the UI on the main thread with the retrieved data
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        songAdapter.setSongList(favoriteSongs);
                    }
                });
            }
        });
    }
    // Method to delete a song from favorites
    public void deleteFavoriteSong(Song song) {
        // Use AsyncTask or a background thread to delete the song from the database asynchronously
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // Delete the song from the database using the DAO
                songDAO.delete(song);
                // Update the UI on the main thread to reflect the changes (if needed)
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // You may need to update the UI after deleting the song
                        // For example, refresh the list of favorite songs
                        displayFavoriteSongs();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.song_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.returnHomeMenu) {
            // Put your code for returning home here
            AlertDialog.Builder builder = new AlertDialog.Builder(MainSongActivity.this);
            builder.setMessage(getString(R.string.goToHomeSnack))
                    .setTitle(R.string.question)
                    .setNegativeButton(getString(R.string.reject), (a, b) -> {})
                    .setPositiveButton(getString(R.string.confirm), (a, b) -> {
                        Intent songSavedList = new Intent(MainSongActivity.this, MainActivity.class);
                        CharSequence text3 = getString(R.string.goToHomeSnack);
                        Toast.makeText(this, text3, Toast.LENGTH_SHORT).show();
                        startActivity(songSavedList);
                        Snackbar.make(binding.mysongToolbar, getString(R.string.goToHomeSnack), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), clk -> {
                                    Intent mainPage = new Intent(MainSongActivity.this, MainActivity.class);
                                    CharSequence text1 = getResources().getString(R.string.goToSunPage);
                                    Toast.makeText(this, text1, Toast.LENGTH_SHORT).show();
                                    startActivity(mainPage);
                                })
                                .show();
                    }).create().show();
            return true;
        } else if (id == R.id.showSaveList) {
            // Code for showing the saved list
            Intent songSavedList = new Intent(MainSongActivity.this, MainSongActivity.class);
            CharSequence showText = getString(R.string.goToSaveList);
            Toast.makeText(this, showText, Toast.LENGTH_SHORT).show();
            startActivity(songSavedList);
            return true;
        } else if (id == R.id.song_menu_about) {
            // Code for showing the version info
            Toast.makeText(this, getString(R.string.version), Toast.LENGTH_LONG).show();
            return true;
        } else if (id == R.id.song_menu_help) {
            // Code for showing help/instructions
            AlertDialog.Builder instructionsDialog = new AlertDialog.Builder(this);
            instructionsDialog.setMessage(R.string.SongAboutuse)
                    .setTitle(R.string.yxAboutTitle)
                    .setNegativeButton(getString(R.string.confirm), (dialog, cl) -> {})
                    .create().show();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}