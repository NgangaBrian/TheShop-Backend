package com.TheShopApp.database.repository;

import com.TheShopApp.database.models.ItemsModel;
import com.TheShopApp.database.models.SearchItemsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchItemRepository extends JpaRepository<SearchItemsModel, Long> {

    //Find products by category name
    List<SearchItemsModel> findBySubcategoryCategoryNameContaining(String searchTerm);

    //Find products by subcategory name
    List<SearchItemsModel> findBySubcategoryNameContaining(String searchTerm);

    //Find products by product name
    List<SearchItemsModel> findByProductNameContaining(String searchTerm);

    //Fing products by product description
    List<SearchItemsModel> findByProductDescriptionContaining(String searchTerm);
}
