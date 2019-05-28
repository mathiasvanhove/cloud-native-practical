package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import com.ezgroceries.shoppinglist.exceptions.ShoppingListException;
import com.ezgroceries.shoppinglist.models.ShoppingListResource;
import com.ezgroceries.shoppinglist.repositories.ShoppingListRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-17)
 */

@Service
public class ShoppingListServiceImpl implements ShoppingListService{

    private final ShoppingListRepository shoppingListRepository;
    private final CocktailService cocktailService;

    public ShoppingListServiceImpl(ShoppingListRepository shoppingListRepository, CocktailService cocktailService) {
        this.shoppingListRepository = shoppingListRepository;
        this.cocktailService = cocktailService;
    }

    @Override
    public ShoppingListResource createShoppingList(String name) {
        ShoppingListEntity shoppingListEntity = new ShoppingListEntity(name);
        ShoppingListEntity saved = shoppingListRepository.save(shoppingListEntity);
        return fromShoppingListEntity(saved);
    }

    @Override
    public ShoppingListResource get(String shoppingListId) {
        Optional<ShoppingListEntity> shoppingListResource = shoppingListRepository.findById(UUID.fromString(shoppingListId));
        return shoppingListResource.map(this::fromShoppingListEntity)
                .orElseThrow(() -> new ShoppingListException("Shopping list with id '" + shoppingListId + "' not found"));
    }

    @Override
    public ShoppingListResource addCocktails(String shoppingListId, List<String> cocktails) {
        List<CocktailEntity> cocktailEntities = cocktailService.getAllById(cocktails);
        return shoppingListRepository.findById(UUID.fromString(shoppingListId)).map(shoppingList -> {
            shoppingList.addCocktails(cocktailEntities);
            shoppingListRepository.save(shoppingList);
            return fromShoppingListEntity(shoppingList);
        }).orElseThrow(() -> new ShoppingListException("Shopping list with id '" + shoppingListId + "' not found"));
    }

    @Override
    public List<ShoppingListResource> getAllShoppingLists() {
        List<ShoppingListEntity> entity = shoppingListRepository.findAll();
        return entity.stream().map(this::fromShoppingListEntityWithIngredients).collect(Collectors.toList());
    }

    private ShoppingListResource fromShoppingListEntity(ShoppingListEntity shoppingListEntity) {
        return new ShoppingListResource(shoppingListEntity.getId(), shoppingListEntity.getName());
    }

    private ShoppingListResource fromShoppingListEntityWithIngredients(ShoppingListEntity entity){
        ShoppingListResource shoppingList = fromShoppingListEntity(entity);
        List<CocktailEntity> entities = (entity.getCocktails() != null) ? entity.getCocktails() : new ArrayList<>();
        List<String> ids = entities.stream().map(CocktailEntity::getId).map(UUID::toString).collect(Collectors.toList());
        List<String> ingredients = cocktailService.getAllById(ids).stream().map(CocktailEntity::getIngredients).flatMap(Set::stream).distinct().collect(Collectors.toList());
        shoppingList.addIngredients(ingredients);
        return shoppingList;
    }
}
