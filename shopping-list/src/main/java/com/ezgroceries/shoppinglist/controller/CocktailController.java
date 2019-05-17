package com.ezgroceries.shoppinglist.controller;

import com.ezgroceries.shoppinglist.client.CocktailDBClient;
import com.ezgroceries.shoppinglist.client.CocktailDBResponse;
import com.ezgroceries.shoppinglist.model.CocktailResource;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.platform.commons.util.StringUtils;
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

    private final CocktailDBClient cocktailDBClient;

    private CocktailController(CocktailDBClient cocktailDBClient) { this.cocktailDBClient = cocktailDBClient; }

    @GetMapping
    public List<CocktailResource> get(@RequestParam String search) {

        return convert(cocktailDBClient.searchCocktails(search));
    }

    private List<CocktailResource> convert(CocktailDBResponse dbResponse) {
        return dbResponse.getDrinks().stream()
                .map(drinkResource -> new CocktailResource(
                        UUID.randomUUID(),
                        drinkResource.getStrDrink(),
                        drinkResource.getStrGlass(),
                        drinkResource.getStrInstructions(),
                        drinkResource.getStrDrinkThumb(),
                        Stream.of(
                                drinkResource.getStrIngredient1(),
                                drinkResource.getStrIngredient2(),
                                drinkResource.getStrIngredient3(),
                                drinkResource.getStrIngredient4(),
                                drinkResource.getStrIngredient5()
                        ).filter(StringUtils::isNotBlank).collect(Collectors.toList())

                   )
                ).collect(Collectors.toList());
    }
}
