package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-07)
 */

@RestController
@RequestMapping(value = "/shopping-lists", produces = "application/json")
public class ShoppingController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListResource createShoppingList(@RequestBody Map<String, String> body) {
        UUID accountId = UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915");
        ShoppingListResource shoppingList = new ShoppingListResource(accountId, body.get("name"));
        return shoppingList;
    }


    @PostMapping(value = "/{accountId}/cocktails")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, String>> addCocktails(@PathVariable String accountId, @RequestBody List<Map<String, String>> body) {
        return body.subList(0, 1);
    }

    @GetMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListResource getShoppingList(@PathVariable String accountId) {
        UUID uuid = UUID.fromString(accountId);
        ShoppingListResource shoppingList = new ShoppingListResource(uuid, "Stephanie's birthday");
        shoppingList.addIngredients(Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao"));
        return shoppingList;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingListResource> getList() {

        List<ShoppingListResource> shoppingLists = new ArrayList<>();
        ShoppingListResource shoppingList = new ShoppingListResource(UUID.fromString("4ba92a46-1d1b-4e52-8e38-13cd56c7224c"), "Stephanie's birthday");
        shoppingList.addIngredients(Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao"));
        shoppingLists.add(shoppingList);
        shoppingList = new ShoppingListResource(UUID.fromString("6c7d09c2-8a25-4d54-a979-25ae779d2465"), "My birthday");
        shoppingList.addIngredients(Arrays.asList("Tequila",
                "Triple sec",
                "Lime juice",
                "Salt",
                "Blue Curacao"));
        shoppingLists.add(shoppingList);
        return shoppingLists;
    }
}
