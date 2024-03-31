package algonquin.cst2335.androidfinalproject.recipe;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RecipeDetail {
    @PrimaryKey
    @ColumnInfo(name="id")
    private long id;
    @ColumnInfo(name="title")
    private String title;
    @ColumnInfo(name="summary")
    private String summary;
    @ColumnInfo(name="image")

    private String image;
    @ColumnInfo(name="instructions")
    private String instructions;

    public RecipeDetail(long id, String title, String summary, String image, String instructions) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.image = image;
        this.instructions = instructions;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
