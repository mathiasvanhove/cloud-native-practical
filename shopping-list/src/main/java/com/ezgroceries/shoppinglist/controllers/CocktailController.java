package com.ezgroceries.shoppinglist.controllers;

import com.ezgroceries.shoppinglist.models.CocktailResource;
import com.ezgroceries.shoppinglist.services.CocktailService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-07)
 */

@RestController
@RequestMapping(value = "/cocktails", produces = "application/json")
public class CocktailController {

    private final CocktailService cocktailService;

    private CocktailController(CocktailService cocktailService) { this.cocktailService = cocktailService; }

    @GetMapping
    public List<CocktailResource> get(@RequestParam String search) {

        return cocktailService.searchCocktails(search);
    }

}
