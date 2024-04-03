/*
 * Filename: RecipeDetailDAO.java
 * Purpose: Defines a Data Access Object (DAO) interface for interacting with recipe details in the database.
 * Author: Liying Guo
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 */
package algonquin.cst2335.androidfinalproject.recipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

/**
 * Data Access Object (DAO) interface for interacting with recipe details in the database.
 */
@Dao
public interface RecipeDetailDAO {
    /**
     * Inserts a recipe detail into the database.
     * @param m The recipe detail to insert.
     * @return The ID of the inserted recipe detail.
     */
    @Insert
    long createRecipe(RecipeDetail m);

    /**
     * Retrieves a recipe detail from the database based on its ID.
     * @param id The ID of the recipe detail to retrieve.
     * @return The retrieved recipe detail.
     */
    @Query("SELECT * FROM RecipeDetail WHERE id = :id")
    RecipeDetail retrieve(long id);

    /**
     * Retrieves all recipe details from the database.
     * @return A list of all recipe details stored in the database.
     */
    @Query("Select * from RecipeDetail")
    List<RecipeDetail> retrieveAll();

    /**
     * Deletes a recipe detail from the database.
     * @param m The recipe detail to delete.
     */
    @Delete
    void deleteMessage(RecipeDetail m);
}
