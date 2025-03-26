package com.example.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.baseclasses.UserWeb;

public interface UserRepository extends JpaRepository< UserWeb,Long >{
	Optional<UserWeb> findByUsername(String username);
}
