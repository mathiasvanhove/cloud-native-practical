package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.models.ShoppingListResource;
import java.util.List;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-17)
 */

public interface ShoppingListService {

    ShoppingListResource createShoppingList(String name);

    ShoppingListResource get(String id);

    ShoppingListResource addCocktails(String shoppingListId, List<String> cocktails);

    List<ShoppingListResource> getAllShoppingLists();

}
