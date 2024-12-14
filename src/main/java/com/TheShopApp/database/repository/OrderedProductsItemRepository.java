package com.TheShopApp.database.repository;

import com.TheShopApp.darajaApi.dtos.ProductsItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderedProductsItemRepository extends JpaRepository<ProductsItem, Long> {
}
