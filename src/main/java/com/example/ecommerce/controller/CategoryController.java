package com.example.ecommerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.baseclasses.Category;
import com.example.ecommerce.repository.CategoryRepository;

@RestController
public class CategoryController {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/category")
	public List<Category> getAllCategories(){
		return categoryRepository.findAll();
	}
	
	@PostMapping("/category")
	public void createCategories(@RequestBody Category category){
		categoryRepository.save(category);
	}
	
	
}
