package com.example.ecommerce.baseclasses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Booking {
	
	@Id
	@GeneratedValue
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "userid", nullable = false) // Creates a foreign key column
    private UserWeb user;
	
	@ManyToOne
    @JoinColumn(name = "productid", nullable = false) // Creates a foreign key column
    private Product product;

	
	public Booking() {
	
	}
	
	public Booking(int id, UserWeb user, Product product) {
		super();
		this.id = id;
		this.user = user;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public UserWeb getUser() {
		return user;
	}

	public void setUser(UserWeb user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	@Override
	public String toString() {
		return "Booking [id=" + id + ", user=" + user + ", product=" + product + "]";
	}
	
	
}
