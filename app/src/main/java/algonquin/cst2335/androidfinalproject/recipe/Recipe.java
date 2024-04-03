/*
 * Filename: Recipe.java
 * Purpose: Defines a class representing a recipe with its id, title, and image URL.
 * Author: Liying Guo
 * Lab Section: CST2355 011
 * Creation Date: March 31, 2024
 */
package algonquin.cst2335.androidfinalproject.recipe;
/**
 * Class representing a recipe with its id, title, and image URL.
 */
public class Recipe {
    private long id;
    private String title;
    private String image; //image url

    /**
     * Constructor to initialize a Recipe object with the provided id, title, and image URL.
     * @param id The unique identifier of the recipe.
     * @param title The title of the recipe.
     * @param image The URL of the recipe image.
     */
    public Recipe(long id, String title, String image) {
        this.id = id;
        this.title = title;
        this.image = image;
    }

    /**
     * Constructor to initialize a Recipe object using a RecipeDetail object.
     * @param rcp The RecipeDetail object containing the recipe information.
     */
    public Recipe(RecipeDetail rcp) {
        this(rcp.getId(),rcp.getTitle(),rcp.getImage());
    }

    /**
     * Retrieves the ID of the recipe.
     * @return The ID of the recipe.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the ID of the recipe.
     * @param id The ID of the recipe to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the title of the recipe.
     * @return The title of the recipe.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the recipe.
     * @param title The title of the recipe to set.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Retrieves the URL of the recipe image.
     * @return The URL of the recipe image.
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the URL of the recipe image.
     * @param image The URL of the recipe image to set.
     */
    public void setImage(String image) {
        this.image = image;
    }

}
