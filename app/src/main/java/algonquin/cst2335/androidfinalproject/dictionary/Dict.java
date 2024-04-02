package algonquin.cst2335.androidfinalproject.dictionary;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Represents a dictionary entry, including details like the entry name, a summary, a source URL, a unique ID, and whether it's linked to a "save" button.
 * It's ready to be stored in a Room database because of the {@link Entity} annotation.
 * @author Feng Qi
 * @version 1.0
 * @since 2024-03-29
 */
@Entity
public class Dict {
    @ColumnInfo(name = "dictName")
    public String dictName;

    @ColumnInfo(name = "summary")
    public String summary;

    @ColumnInfo(name = "srcUrl")
    protected String srcUrl;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public long id;

    @ColumnInfo(name = "isSaveButton")
    protected boolean isSaveButton;

    /**
     * Default empty constructor.
     */
    public Dict() {}

    /**
     * Creates an instance with a given word and its definition.
     *
     * @param word Entry word.
     * @param def Entry definition.
     */
    public Dict(String word, String def) {
        this.dictName = word;
        this.summary = def;
    }

    public String getDictName() {
        return dictName;
    }

    public void setDictName(String dictName) {
        this.dictName = dictName;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    // This method is synonymous with getSummary() for the sake of addressing the error.
    public String getDictDefinition() {
        return getSummary();
    }

    public String getSrcUrl() {
        return srcUrl;
    }

    public void setSrcUrl(String srcUrl) {
        this.srcUrl = srcUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isSaveButton() {
        return isSaveButton;
    }

    public void setSaveButton(boolean saveButton) {
        isSaveButton = saveButton;
    }
}
