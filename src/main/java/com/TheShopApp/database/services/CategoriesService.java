package com.TheShopApp.database.services;

import com.TheShopApp.database.models.CategoriesModel;
import com.TheShopApp.database.repository.CategoriesRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriesService {
    private final CategoriesRepository categoriesRepository;

    public CategoriesService(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    public List<CategoriesModel> getAllCategories() {
        return categoriesRepository.findAll();
    }
}
