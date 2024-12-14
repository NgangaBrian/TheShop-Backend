package com.TheShopApp.darajaApi.dtos;

import com.TheShopApp.database.deserializer.UserDeserializer;
import com.TheShopApp.database.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrdersModel{

	@Id
	@JsonProperty("id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonProperty("paymentId")
	private Long payment_id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonProperty("customerId")
	@JsonDeserialize(using = UserDeserializer.class)
	private User customer;

	@JsonProperty("orderDate")
	private Date order_date;

    @JsonProperty("products")
	@OneToMany(mappedBy = "orders", cascade = CascadeType.PERSIST)
	private List<ProductsItem> products;
}