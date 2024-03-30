package algonquin.cst2335.androidfinalproject.song;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import algonquin.cst2335.androidfinalproject.MainActivity;
import algonquin.cst2335.androidfinalproject.R;
import algonquin.cst2335.androidfinalproject.databinding.ActivitySongMainBinding;

public class MainSongActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private RecyclerView recyclerViewResults;
    private SongAdapter songAdapter;

    protected ActivitySongMainBinding binding;

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
        recyclerViewResults = findViewById(R.id.recyclerViewResults);
        recyclerViewResults.setLayoutManager(new LinearLayoutManager(this));

        songAdapter = new SongAdapter();
        recyclerViewResults.setAdapter(songAdapter);

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
    }

    private void performSearch(String artistName) {
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