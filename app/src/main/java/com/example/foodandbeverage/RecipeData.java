package com.example.foodandbeverage;

import com.google.firebase.database.PropertyName;

public class RecipeData
{
    private String recipeName;
    private String recipeDescription;
    private String category;
    private String recipeIngredients;
    private String recipeSteps;
    private String recipeImage;
    private String key;
    private int image;

    public RecipeData(String name, String description, String category, String image)
    {
        this.recipeName = name;
        this.recipeDescription = description;
        this.category = category;
        this.recipeImage = image;
    }

    public RecipeData(String name, String description, String category, String ingredients, String steps, String image)
    {
        this.recipeName = name;
        this.recipeDescription = description;
        this.category = category;
        this.recipeIngredients = ingredients;
        this.recipeSteps = steps;
        this.recipeImage = image;
    }

    public RecipeData(String name, int image)
    {
        this.recipeName = name;
        this.image = image;
    }

    public RecipeData()
    {

    }

    public int imageMethod()
    {
        return image;
    }

    //@PropertyName("recipeName")
    public String getRecipeName()
    {
        return recipeName;
    }

    //@PropertyName("recipeDescription")
    public String getRecipeDescription()
    {
        return recipeDescription;
    }

    public String getRecipeIngredients()
    {
        return recipeIngredients;
    }

    public String getRecipeSteps()
    {
        return recipeSteps;
    }

    public String getCategory()
    {
        return category;
    }

    //@PropertyName("recipeImage")
    public String getRecipeImage()
    {
        return recipeImage;
    }

    public String getKey()
    {
        return key;
    }

    public void setKey(String key)
    {
        this.key = key;
    }
}
