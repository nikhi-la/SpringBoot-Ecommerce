package com.example.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.baseclasses.Category;

public interface CategoryRepository extends JpaRepository< Category,Long >{

}
