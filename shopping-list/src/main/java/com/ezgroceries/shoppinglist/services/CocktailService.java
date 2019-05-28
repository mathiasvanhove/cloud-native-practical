package com.ezgroceries.shoppinglist.services;

import com.ezgroceries.shoppinglist.entities.CocktailEntity;
import com.ezgroceries.shoppinglist.models.CocktailResource;
import java.util.List;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-17)
 */
public interface CocktailService {

    List<CocktailResource> searchCocktails(String search);

    List<CocktailEntity> getAllById(List<String> cocktails);
}
