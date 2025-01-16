package com.TheShopApp.database.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "subcategories")
public class SubcategoriesModel {

    @Id
    public int id;
    public String name;
    public String image_url;


    @ManyToOne
    @JoinColumn(name = "categories_id", referencedColumnName = "categories_id")
    private CategoriesModel category;
}
