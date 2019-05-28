package com.ezgroceries.shoppinglist.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-07)
 */
public class ShoppingListResource {
    private final UUID shoppingListId;
    private final String name;
    private List<String> ingredients;

    public ShoppingListResource(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
        this.ingredients = new ArrayList<>();
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void addIngredient(String ingredient) {this.ingredients.add(ingredient);}

    public void addIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
