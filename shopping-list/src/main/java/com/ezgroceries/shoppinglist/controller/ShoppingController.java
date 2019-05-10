package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.internal.shoppingList.ShoppingListResource;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-07)
 */

@RestController
public class ShoppingController {


    @GetMapping(value = "/shopping-lists")
    @ResponseStatus(HttpStatus.CREATED)
    public List<ShoppingListResource> getShoppingList() {
        return Arrays.asList(
                new ShoppingListResource(
                        UUID.fromString("eb18bb7c-61f3-4c9f-981c-55b1b8ee8915"), "Stephanie's birthday"
                )
        );
    }

}
