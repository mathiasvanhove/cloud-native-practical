package com.ezgroceries.shoppinglist.internal.cocktail;

import java.util.List;
import java.util.UUID;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-07)
 */
public class CocktailResource {

    private UUID cocktailId;
    private String name;
    private String glass;
    private String instructions;
    private String image;
    private List ingredients;

    public CocktailResource(UUID cocktailId, String name, String glass, String instructions, String image, List ingredients) {
        this.cocktailId = cocktailId;
        this.name = name;
        this.glass = glass;
        this.instructions = instructions;
        this.image = image;
        this.ingredients = ingredients;
    }

    public UUID getCocktailId() {
        return cocktailId;
    }

    public String getName() {
        return name;
    }

    public String getGlass() {
        return glass;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getImage() {
        return image;
    }

    public List getIngredients() {
        return ingredients;
    }

}
