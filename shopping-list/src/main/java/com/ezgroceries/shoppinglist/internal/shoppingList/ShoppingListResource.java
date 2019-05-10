package com.ezgroceries.shoppinglist.internal.shoppingList;

import java.util.UUID;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-07)
 */
public class ShoppingListResource {
    private UUID shoppingListId;
    private String name;

    public ShoppingListResource(UUID shoppingListId, String name) {
        this.shoppingListId = shoppingListId;
        this.name = name;
    }

    public UUID getShoppingListId() {
        return shoppingListId;
    }

    public String getName() {
        return name;
    }
}
