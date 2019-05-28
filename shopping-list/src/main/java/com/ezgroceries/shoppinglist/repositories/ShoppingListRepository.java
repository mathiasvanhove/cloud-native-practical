package com.ezgroceries.shoppinglist.repositories;

import com.ezgroceries.shoppinglist.entities.ShoppingListEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Mathias Vanhove (je25293)
 * @since release/yyyymm  (2019-05-17)
 */
public interface ShoppingListRepository extends CrudRepository<ShoppingListEntity, UUID> {

    List<ShoppingListEntity> findAll();
}
