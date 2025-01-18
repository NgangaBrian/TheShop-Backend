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

	@JsonProperty("merchant_request_id")
	private String merchant_request_id;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@JsonProperty("customerId")
	@JsonDeserialize(using = UserDeserializer.class)
	private User customer;

	@JsonProperty("orderDate")
	@Column(name = "order_date", insertable = false)
	private Date order_date;

    @JsonProperty("products")
	@OneToMany(mappedBy = "orders", cascade = CascadeType.PERSIST)
	private List<ProductsItem> products;

	@Override
	public String toString() {
		return "OrdersModel{" +
				"id=" + id +
				", merchant_request_id='" + merchant_request_id + '\'' +
				", order_date=" + order_date +
				", customer=" + (customer != null ? customer.getId() : null) + // Limit recursion by showing just ID of the customer
				", products=" + (products != null ? products.size() : 0) + // Show size instead of full list
				'}';
	}
}