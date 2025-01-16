package com.TheShopApp.database.services;

import com.TheShopApp.database.models.ItemsModel;
import com.TheShopApp.database.models.SearchItemsModel;
import com.TheShopApp.database.repository.ItemsRepository;
import com.TheShopApp.database.repository.SearchItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ItemSearchService {

    @Autowired
    private SearchItemRepository searchItemRepository;

    public List<SearchItemsModel> searchProduct(String searchTerm) {
        List<SearchItemsModel> resultList;

        resultList = searchItemRepository.findBySubcategoryCategoryNameContaining(searchTerm);
        if (!resultList.isEmpty()) return resultList;

        resultList = searchItemRepository.findBySubcategoryNameContaining(searchTerm);
        if (!resultList.isEmpty()) return resultList;

        resultList = searchItemRepository.findByProductNameContaining(searchTerm);
        if (!resultList.isEmpty()) return resultList;

        resultList = searchItemRepository.findByProductDescriptionContaining(searchTerm);
        if (!resultList.isEmpty()) return resultList;

        return Collections.emptyList();
    }
}
