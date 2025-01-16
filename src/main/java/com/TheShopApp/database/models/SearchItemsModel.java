package com.TheShopApp.database.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
public class SearchItemsModel {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long products_id;
        @Setter
        @Getter
        @Column(name = "product_name")
        private String productName;
        @Getter
        @Setter
        @Column(name = "product_description")
        private String productDescription;
        @Getter
        @Setter
        private String image_url;
        @Getter
        @Setter
        private Double product_price;


        @Setter
        @Getter
        @ManyToOne
        @JoinColumn(name = "subcategory_id", referencedColumnName = "id")
        private SubcategoriesModel subcategory;

        public SearchItemsModel() {
        }


        @Override
        public String toString() {
            return "ItemsModel [products_id=" + products_id + ", product_name=" + productName +
                    ", product_description=" + productDescription + ", image_url=" + image_url +
                    ", product_price=" + product_price + ", subcategory_id=" + subcategory + "]";
        }

        public SearchItemsModel(Long products_id, String product_name, String product_description, String image_url,
                          Double product_price, SubcategoriesModel subcategory) {
            this.products_id = products_id;
            this.productName = product_name;
            this.productDescription = product_description;
            this.image_url = image_url;
            this.product_price = product_price;
            this.subcategory = subcategory;
        }

        public Long getProduct_id() {
            return products_id;
        }

        public void setProduct_id(Long products_id) {
            this.products_id = products_id;
        }



}
