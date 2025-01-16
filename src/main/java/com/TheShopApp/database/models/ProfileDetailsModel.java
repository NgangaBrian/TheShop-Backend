package com.TheShopApp.database.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "personal_details")
public class ProfileDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int user_id;
    private String mobile_number;
    private String address;
    private String postal_code;
    private String profile_url;

}
