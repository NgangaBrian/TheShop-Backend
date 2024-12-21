package com.TheShopApp.database.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "personal_details")
public class ProfileDetailsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long user_id;

    @Column(name = "mobile_nomber")
    private String mobile_nomber;

    @Column(name = "postal_code")
    private String postal_code;

    @Column(name = "profile_picture_url")
    private String profile_picture_url;
}
