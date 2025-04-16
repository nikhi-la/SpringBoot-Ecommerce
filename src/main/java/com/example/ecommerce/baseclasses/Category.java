package com.example.ecommerce.baseclasses;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	private String categoryname;
	
	public Category() {
	
	}
	
	public Category(Long id, String categoryname) {
	
		super();
		this.id = id;
		this.categoryname = categoryname;
	
	}

	public long getId() {
		return id;
	}


	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}
	
	
	
}
