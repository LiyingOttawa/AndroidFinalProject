package algonquin.cst2335.androidfinalproject.song;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SongDao {
    @Insert
    void insert(Song song);

    @Query("SELECT * FROM Song")
    List<Song> getAllSongs();

    // Add other database operations as needed
}