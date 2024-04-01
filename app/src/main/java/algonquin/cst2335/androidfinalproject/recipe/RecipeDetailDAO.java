package algonquin.cst2335.androidfinalproject.recipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface RecipeDetailDAO {
    @Insert
    long createRecipe(RecipeDetail m);
    @Query("SELECT * FROM RecipeDetail WHERE id = :id")
    public RecipeDetail retrieve(long id);
    @Query("Select * from RecipeDetail")
    List<RecipeDetail> retrieveAll();

    @Delete
    void deleteMessage(RecipeDetail m);
}
