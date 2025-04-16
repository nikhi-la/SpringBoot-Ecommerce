package com.example.ecommerce.controller;

import java.util.List;
import java.util.Optional;

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
		
		List<Category> catgeoryList =  categoryRepository.findAll();
		if(!catgeoryList.isEmpty()) {
			return catgeoryList;
		}
		else {
			return List.of();
		}
	}
	
	@PostMapping("/category")
	public String createCategories(@RequestBody Category category){
		Optional<Category> existingCategory = categoryRepository.findByCategoryname(category.getCategoryname());
		
		if (existingCategory.isPresent()) {
            return "Category Exist";
        } else {
        	categoryRepository.save(category);
    		return "Category Added";
        }
		
		
	}
	
	
}
