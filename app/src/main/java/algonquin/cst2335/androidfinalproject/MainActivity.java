package algonquin.cst2335.androidfinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import algonquin.cst2335.androidfinalproject.song.MainSongActivity;

public class MainActivity extends AppCompatActivity {

    private Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button4 = findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start MainSongActivity
                Intent intent = new Intent(MainActivity.this,MainSongActivity.class);
                startActivity(intent);
            }
        });
    }
}
