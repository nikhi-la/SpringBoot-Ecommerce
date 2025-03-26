package com.example.ecommerce.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.baseclasses.Product;

public interface ProductRepository extends JpaRepository< Product,Long > {
	List<Product> findByCategoryId(Long categoryId);
	Optional<Product> findByIdAndCategoryId(Long categoryId,Long productId);
}
