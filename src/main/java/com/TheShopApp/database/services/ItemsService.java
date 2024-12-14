package com.TheShopApp.database.services;

import com.TheShopApp.database.models.ItemsModel;
import com.TheShopApp.database.repository.ItemsRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ItemsService {
    public final ItemsRepository itemsRepository;
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public Page<ItemsModel> getAllItems(Pageable pageable) {
        return itemsRepository.findAll(pageable);
    }

}
