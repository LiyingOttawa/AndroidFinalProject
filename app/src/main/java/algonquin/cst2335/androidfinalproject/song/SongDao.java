/*
 * Filename: SongDao.java
 * Author: Zhaoguo Han
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 * Purpose: This interface defines the Data Access Object (DAO) for accessing the Song entity in the database.
 */
package algonquin.cst2335.androidfinalproject.song;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) for accessing the Song entity in the database.
 */
@Dao
public interface SongDao {

    /**
     * Insert a song into the database.
     * @param song The song object to be inserted.
     */
    @Insert
    void insert(Song song);

    /**
     * Retrieve all songs from the database.
     * @return A list of all songs stored in the database.
     */
    @Query("SELECT * FROM Song")
    List<Song> getAllSongs();

    /**
     * Delete a song from the database.
     * @param song The song object to be deleted.
     */
    @Delete
    void delete(Song song);
}
