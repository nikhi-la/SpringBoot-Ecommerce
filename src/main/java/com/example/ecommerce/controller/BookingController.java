package com.example.ecommerce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecommerce.baseclasses.Booking;
import com.example.ecommerce.baseclasses.Product;
import com.example.ecommerce.baseclasses.UserWeb;
import com.example.ecommerce.repository.BookingRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

@RestController
public class BookingController {

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private UserRepository userRepository;
	
	
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
	
	
	@PostMapping("/booking")
	public Map<String, String> createBooking(@RequestBody Booking booking){
		Map<String, String> response = new HashMap<>();
		//System.out.println(booking);
		
		Optional<Product> productOptional = productRepository.findById(booking.getProduct().getId());
		if((productOptional.get().getStock()-1)<0)
		{
			response.put("message", "Out of Stock!");
			return response;
		}
		productOptional.get().setStock(productOptional.get().getStock()-1);
		
        Optional<UserWeb> userOptional = userRepository.findById(booking.getUser().getId());

        if (productOptional.isPresent() && userOptional.isPresent()) {
            booking.setProduct(productOptional.get());
            booking.setUser(userOptional.get());
            //System.out.println(booking);
            bookingRepository.save(booking);
            
            response.put("message", "Booking Added Successfully");
        } else {
        	response.put("message", "Invalid productid or userid");
			
        }
        return response;
	}
	
	@GetMapping("/booking/{userId}")
	public List<Booking> getBookingsByUserId(@PathVariable long userId) {
	    List<Booking> bookings = bookingRepository.findByUserId(userId);

	    if (bookings.isEmpty()) {
	        throw new RuntimeException("No bookings found for User ID: " + userId);
	    }

	    return bookings; //  Returns a list of bookings
	}

}
