package com.TheShopApp.database.services;

import com.TheShopApp.database.models.BannerSliderModel;
import com.TheShopApp.database.repository.BannerSliderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerSliderService {
    private final BannerSliderRepository bannerSliderRepository;
    public BannerSliderService(BannerSliderRepository bannerSliderRepository) {
        this.bannerSliderRepository = bannerSliderRepository;
    }

    public List<BannerSliderModel> getAllBannerSliders() {
        return bannerSliderRepository.findAll();
    }
}
