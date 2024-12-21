package com.TheShopApp.database.services;

import com.TheShopApp.database.models.ProfileDetailsModel;
import com.TheShopApp.database.repository.ProfileDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileDetailsService {

    @Autowired
    private ProfileDetailsRepository profileDetailsRepository;

    public ProfileDetailsModel saveProfileDetails(ProfileDetailsModel profileDetailsModel) {
        return profileDetailsRepository.save(profileDetailsModel);
    }
}
