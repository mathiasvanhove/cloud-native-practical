package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.model.ShoppingListResource;
import com.ezgroceries.shoppinglist.services.ShoppingListService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
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

    private final ShoppingListService shoppingListService;

    public ShoppingController(ShoppingListService shoppingListService) {
        this.shoppingListService = shoppingListService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ShoppingListResource createShoppingList(@RequestBody Map<String, String> body) {
        ShoppingListResource shoppingList = shoppingListService.createShoppingList(body.get("name"));
        return shoppingList;
    }


    @PostMapping(value = "/{accountId}/cocktails")
    @ResponseStatus(HttpStatus.OK)
    public List<Map<String, String>> addCocktails(@PathVariable String accountId, @RequestBody List<Map<String, String>> body) {
        List<String> cocktails = body.stream().map(map -> map.get("cocktailId")).collect(Collectors.toList());
        shoppingListService.addCocktails(accountId, cocktails);
        return body.subList(0, 1);
    }

    @GetMapping(value = "/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ShoppingListResource getShoppingList(@PathVariable String accountId) {
        return shoppingListService.get(accountId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ShoppingListResource> getAllShoppingLists() {
        return shoppingListService.getAllShoppingLists();
    }

}
