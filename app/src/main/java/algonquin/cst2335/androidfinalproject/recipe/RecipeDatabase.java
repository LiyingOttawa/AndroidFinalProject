/*
 * Filename: RecipeDatabase.java
 * Purpose: Defines a Room database class for storing recipe details.
 * Author: Liying Guo
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 */
package algonquin.cst2335.androidfinalproject.recipe;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Room database class for storing recipe details.
 */
@Database(entities = {RecipeDetail.class}, version = 1)
public abstract class RecipeDatabase extends RoomDatabase {
    public abstract RecipeDetailDAO cmDAO();
}
