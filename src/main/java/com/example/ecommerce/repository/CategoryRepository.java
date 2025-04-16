package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.baseclasses.Category;
import com.example.ecommerce.baseclasses.UserWeb;

public interface CategoryRepository extends JpaRepository< Category,Long >{
	Optional<Category> findByCategoryname(String categoryname);

}
