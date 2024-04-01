package algonquin.cst2335.androidfinalproject.dictionary;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * Defines the methods for database interactions related to {@link Dict} entities.
 * It allows for creating, reading, updating, and deleting dictionary entries in the database.
 *
 * Example usage:
 * <pre>
 * {@code
 * // Acquire DictDAO instance from your database class
 * DictDAO dictDAO;
 * // Creating a new dictionary entry
 * Dict newEntry = new Dict("Hello", "A greeting");
 * // Inserting the new entry into the database
 * dictDAO.insertDict(newEntry);
 * }
 * </pre>
 * @author Feng Qi
 * @version 1.0
 * @since 2024-03-29
 */
@Dao
public interface DictDAO {
    /**
     * Adds a new {@link Dict} entry to the database.
     *
     * @param d The {@link Dict} object to insert.
     * @return The row ID of the newly inserted entry.
     */
    @Insert
    long insertDict(Dict d);

    /**
     * Fetches all {@link Dict} entries from the database.
     *
     * @return A list of all {@link Dict} entries.
     */
    @Query("SELECT * FROM Dict")
    List<Dict> getAllDicts();

    /**
     * Removes a specific {@link Dict} entry from the database.
     *
     * @param d The {@link Dict} object to delete.
     */
    @Delete
    void deleteDict(Dict d);

    /**
     * Updates an existing {@link Dict} entry in the database.
     *
     * @param d The {@link Dict} object with updated values.
     */
    @Update
    void updateDict(Dict d);
}