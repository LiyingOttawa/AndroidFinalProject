package algonquin.cst2335.androidfinalproject.recipe;
public class Recipe {
    private long id;
    private String title;
    private String image; //image url

    public Recipe(long id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    public Recipe(RecipeDetail rcp) {
        this(rcp.getId(),rcp.getTitle(),rcp.getImage());
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
