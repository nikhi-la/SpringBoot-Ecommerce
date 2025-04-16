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
	public Object getOneProduct(@PathVariable long id){
		Optional <Product> product = productRepository.findById(id);
		if(product.isEmpty()) {
			return "Product Not Found";
		}
		return product.get();
	}

	
	@GetMapping("/product/category/{id}")
	public Object getProductByCategory(@PathVariable long id){
		List<Product> product = productRepository.findByCategoryId(id);
		if(product.isEmpty()) {
			return "Product Not Found";
		}
		//System.out.println(product);
		return product;
	}
	
	@GetMapping("/product/{productId}/category/{categoryId}")
	public Object getProducts( @PathVariable Long categoryId, @PathVariable Long productId) {

	        Optional<Product> product = productRepository.findByIdAndCategoryId(productId, categoryId);
	        if (product.isEmpty()) {
	            return "No matching product found in this category";
	        }
	        //System.out.println("Received Product: " + List.of(product.get()));
	        return product.get();	    
	}
	
	@PostMapping("/product")
	public String createProduct(@RequestBody Product product){
		Optional<Product> existingProduct = productRepository.findByProductname(product.getProductname());
		//System.out.println("Received Product: " + product);
		if(existingProduct.isEmpty()) {
			productRepository.save(product);
			return "Product Added";
		}
		else {
			return "Product Exist";
		}
		
	}
	
}
