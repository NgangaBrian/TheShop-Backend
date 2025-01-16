package com.TheShopApp.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
public class ItemsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long products_id;
    @Setter
    @Getter
    @Column(name = "product_name")
    private String product_name;

    @Getter
    @Setter
    @Column(name = "product_description")
    private String product_description;

    @Getter
    @Setter
    private String image_url;

    @Getter
    @Setter
    private Double product_price;

    @Setter
    @Getter
    private int subcategory_id;

    public ItemsModel() {
    }

    @Override
    public String toString() {
        return "ItemsModel [products_id=" + products_id + ", product_name=" + product_name +
                ", product_description=" + product_description + ", image_url=" + image_url +
                ", product_price=" + product_price + ", subcategory_id=" + subcategory_id + "]";
    }

    public ItemsModel(Long products_id, String product_name, String product_description, String image_url,
                      Double product_price, int subcategory_id) {
        this.products_id = products_id;
        this.product_name = product_name;
        this.product_description = product_description;
        this.image_url = image_url;
        this.product_price = product_price;
        this.subcategory_id = subcategory_id;
    }

    public Long getProduct_id() {
        return products_id;
    }

    public void setProduct_id(Long products_id) {
        this.products_id = products_id;
    }

}
