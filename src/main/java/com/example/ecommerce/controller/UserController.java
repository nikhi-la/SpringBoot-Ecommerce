package com.example.ecommerce.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.baseclasses.Product;
import com.example.ecommerce.baseclasses.UserWeb;
import com.example.ecommerce.repository.UserRepository;

@RestController
public class UserController {
	@Autowired
    private UserRepository userRepository;
	
	@PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody UserWeb user) {
        Optional<UserWeb> existingUser = userRepository.findByUsername(user.getUsername());
        Map<String, String> response = new HashMap<>();

        if (existingUser.isPresent()) {
            response.put("message", "Username already exists!");
        } else {
            userRepository.save(user);
            response.put("message", "User registered successfully");
        }
        return response;
    }
	
	@GetMapping("/user/{id}")
	public UserWeb getOneUser(@PathVariable long id){
		Optional <UserWeb> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new RuntimeException("User Not Found");
		}
		return user.get();
			
		}
		
	@PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody UserWeb user) {
        Optional<UserWeb> existingUser = userRepository.findByUsername(user.getUsername());
        Map<String, String> response = new HashMap<>();

        if (existingUser.isPresent() && existingUser.get().getPassword().equals(user.getPassword())) {
            response.put("message", "Login successful");
            response.put("userid", existingUser.get().getId().toString());
            response.put("usertype", existingUser.get().getUsertype().toString());
        } else {
            response.put("message", "Invalid username or password");
        }
        return response;
    }
}
