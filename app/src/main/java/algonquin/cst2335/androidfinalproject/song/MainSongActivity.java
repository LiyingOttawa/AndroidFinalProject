package algonquin.cst2335.androidfinalproject.song;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import algonquin.cst2335.androidfinalproject.R;

public class MainSongActivity extends AppCompatActivity {

    private EditText editTextSearch;
    private Button buttonSearch;
    private RecyclerView recyclerViewResults;
    private SongAdapter songAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_main);

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
}
