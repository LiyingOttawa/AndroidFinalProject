/*
 * Filename: SongDatabase.java
 * Author: Zhaoguo Han
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 * Purpose: This class represents the Room database for storing favorite songs.
 */
package algonquin.cst2335.androidfinalproject.song;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * Room database for storing favorite songs.
 */
@Database(entities = {Song.class}, version = 1)
public abstract class SongDatabase extends RoomDatabase {

    // Define the abstract method to retrieve the SongDao
    public abstract SongDao songDao();

    // Database name
    private static final String DATABASE_NAME = "favorite_database";
    private static SongDatabase instance;

    /**
     * Get an instance of the SongDatabase.
     * @param context The application context.
     * @return An instance of the SongDatabase.
     */
    public static synchronized SongDatabase getInstance(Context context) {
        if (instance == null) {
            // Create a new instance of the database if it doesn't exist
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            SongDatabase.class, DATABASE_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
