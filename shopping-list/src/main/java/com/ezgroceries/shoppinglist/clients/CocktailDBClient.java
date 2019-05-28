package com.ezgroceries.shoppinglist.clients;

import com.ezgroceries.shoppinglist.clients.CocktailDBResponse.DrinkResource;
import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.repositories.CocktailRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-14)
 */
@Component
@FeignClient(name = "cocktailDBClient", url = "https://www.thecocktaildb.com/api/json/v1/1", fallback = CocktailDBClient.CocktailDBClientFallback.class)
public interface CocktailDBClient {

    @GetMapping(value = "search.php")
    CocktailDBResponse searchCocktails(@RequestParam("s") String search);

    @Component
    class CocktailDBClientFallback implements CocktailDBClient {

        private final CocktailRepository cocktailRepository;

        public CocktailDBClientFallback(CocktailRepository cocktailRepository) {
            this.cocktailRepository = cocktailRepository;
        }
        
        @Override
        public CocktailDBResponse searchCocktails(String search) {
            List<CocktailEntity> cocktailEntities = cocktailRepository.findByNameContainingIgnoreCase(search);

            CocktailDBResponse cocktailDBResponse = new CocktailDBResponse();
            cocktailDBResponse.setDrinks(cocktailEntities.stream().map(cocktailEntity -> {
                CocktailDBResponse.DrinkResource drinkResource = new CocktailDBResponse.DrinkResource();
                drinkResource.setIdDrink(cocktailEntity.getIdDrink());
                drinkResource.setStrDrink(cocktailEntity.getName());
                drinkResource.setStrGlass(cocktailEntity.getGlass());
                drinkResource.setStrDrinkThumb(cocktailEntity.getImage());
                drinkResource.setStrInstructions(cocktailEntity.getInstructions());
                return drinkResource;
            }).collect(Collectors.toList()));

            return cocktailDBResponse;
        }
    }
}