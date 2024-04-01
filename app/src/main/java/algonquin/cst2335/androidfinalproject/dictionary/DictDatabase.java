package algonquin.cst2335.androidfinalproject.dictionary;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Defines the Room database for the dictionary component of the Android final project.
 * This database class includes the configuration for the database such as the entities it contains
 * and its version. It inherits from {@link RoomDatabase}
 *
 * @author Feng Qi
 * @version 1.0
 * @since 2024-03-29
 */
@Database(entities = {Dict.class}, version = 1)
public abstract class DictDatabase extends RoomDatabase {
    /**
     * Provides access to the database's Data Access Object (DAO) for performing operations on the data.
     *
     * @return An instance of {@link DictDAO} for database operations.
     */
    public abstract DictDAO DictDAO();
}
