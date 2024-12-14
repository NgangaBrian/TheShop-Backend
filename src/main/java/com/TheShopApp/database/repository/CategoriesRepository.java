package com.TheShopApp.database.repository;

import com.TheShopApp.database.models.CategoriesModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<CategoriesModel, Long> {
}
