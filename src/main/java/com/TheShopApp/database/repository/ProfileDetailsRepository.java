package com.TheShopApp.database.repository;

import com.TheShopApp.database.models.ProfileDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileDetailsRepository extends JpaRepository<ProfileDetailsModel, Long> {
}
