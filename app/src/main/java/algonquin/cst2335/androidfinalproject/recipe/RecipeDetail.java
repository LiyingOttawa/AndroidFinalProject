package algonquin.cst2335.androidfinalproject.recipe;
public class RecipeDetail {
    private int id;
    private String title;
    private String summary;
    private String instructions;
    private String sourceUrl;


    public RecipeDetail(int id, String title, String summary, String instructions, String sourceUrl) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.instructions = instructions;
        this.sourceUrl = sourceUrl;
    }

    public int getId() {
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

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

}
