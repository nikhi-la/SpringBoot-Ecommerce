package com.example.ecommerce.baseclasses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Product {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@ManyToOne
    @JoinColumn(name = "categoryid", nullable = false) // Creates a foreign key column
    private Category category;
	
	private String productname;
	private int stock;
	private int price;

	public Product() {
	}
	

	public Product(Category category, String productname, int stock, int price) {
		super();
		this.category = category;
		this.productname = productname;
		this.stock = stock;
		this.price = price;
	}

	
	public long getId() {
		return id;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}


	@Override
	public String toString() {
		return "Product [id=" + id + ", category=" + category + ", productname=" + productname + ", stock=" + stock
				+ ", price=" + price + "]";
	}
	
	
}
