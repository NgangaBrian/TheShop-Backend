package com.TheShopApp.database.repository;

import com.TheShopApp.database.models.ProfileDetailsModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfileDetailsRepository extends JpaRepository<ProfileDetailsModel, Integer> {
}
