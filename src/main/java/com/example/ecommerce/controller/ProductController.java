package com.example.ecommerce.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.ecommerce.baseclasses.Product;
import com.example.ecommerce.repository.ProductRepository;

@RestController
public class ProductController {
	
	@Autowired
	ProductRepository productRepository;
	
	@GetMapping("/product")
	public List<Product> getAllProducts(){
		return productRepository.findAll();
	}
	
	@GetMapping("/product/{id}")
	public Product getOneProduct(@PathVariable long id){
		Optional <Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			throw new RuntimeException("Product Not Found");
		}
		return product.get();
	}

	
	@GetMapping("/product/category/{id}")
	public List<Product> getProductByCategory(@PathVariable long id){
		List<Product> product = productRepository.findByCategoryId(id);
		if(product.isEmpty()) {
			throw new RuntimeException("Product Not Found");
		}
		//System.out.println(product);
		return product;
	}
	
	@GetMapping("/product/{productId}/category/{categoryId}")
	public Product getProducts( @PathVariable Long categoryId, @PathVariable Long productId) {

	        Optional<Product> product = productRepository.findByIdAndCategoryId(productId, categoryId);
	        if (product.isEmpty()) {
	            throw new RuntimeException("No matching product found in this category.");
	        }
	        //System.out.println("Received Product: " + List.of(product.get()));
	        return product.get();	    
	}
	
	@PostMapping("/product")
	public void createProduct(@RequestBody Product product){
		//System.out.println("Received Product: " + product);
		productRepository.save(product);
	}
	
}
