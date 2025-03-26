package com.example.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.baseclasses.Booking;

public interface BookingRepository extends JpaRepository< Booking,Long >{
	List<Booking> findByUserId(Long userId);
}
