package com.example.ecommerce.controller;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.example.ecommerce.baseclasses.Booking;
import com.example.ecommerce.baseclasses.Category;
import com.example.ecommerce.baseclasses.Product;
import com.example.ecommerce.baseclasses.UserWeb;
import com.example.ecommerce.repository.BookingRepository;
import com.example.ecommerce.repository.ProductRepository;
import com.example.ecommerce.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookingRepository bookingRepository;
	
	@MockBean
	private ProductRepository productRepository;
	
	@MockBean
	private UserRepository userRepository;
	
	//-----------------------------------------------------------------------------------------------
	// Book product which is out of stock
	
	@Test
	public void createBooking_outOfStock() throws Exception {
		
		Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",0,50);
		
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        RequestBuilder request = MockMvcRequestBuilders
                .post("/booking").contentType(MediaType.APPLICATION_JSON)
                .content("""
                		{
                		  "id": 1,
                		  "user": {
                		  	"id":1,
                		    "username": "john",
                		    "password": "1234",
                		    "email": "john@example.com"
                		  },
                		  "product": {
                		  	"id":1,
                		    "categoryname": "Fruits",
                		    "productname": "Apple",
                		    "stock": 0,
                		    "quantity": 50
                		  }
                		}
                		""")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Out of Stock!"));
	
	}
	
	// Book product - Invalid  user id
	
	@Test
	public void createBooking_InvalidUser() throws Exception {
		
		Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
		
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());
        
        
        RequestBuilder request = MockMvcRequestBuilders
                .post("/booking").contentType(MediaType.APPLICATION_JSON)
                .content("""
                		{
                		  "id": 1,
                		  "user": {
                		  	"id":1,
                		    "username": "john",
                		    "password": "1234",
                		    "email": "john@example.com"
                		  },
                		  "product": {
                		  	"id":1,
                		    "categoryname": "Fruits",
                		    "productname": "Apple",
                		    "stock": 10,
                		    "quantity": 50
                		  }
                		}
                		""")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Invalid productid or userid"));
	
	}
	
	// Book product - valid  user and product
	
	@Test
	public void createBooking_ValidProductAndUser() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
		Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
		
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        
        
        RequestBuilder request = MockMvcRequestBuilders
                .post("/booking").contentType(MediaType.APPLICATION_JSON)
                .content("""
                		{
                		  "id": 1,
                		  "user": {
                		  	"id":1,
                		    "username": "john",
                		    "password": "1234",
                		    "email": "john@example.com"
                		  },
                		  "product": {
                		  	"id":1,
                		    "categoryname": "Fruits",
                		    "productname": "Apple",
                		    "stock": 10,
                		    "quantity": 50
                		  }
                		}
                		""")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Booking Added Successfully"));
	
	}
	
	
	//-----------------------------------------------------------------------------------------------
	
	
	//List Booking - Atleast one booking
	
	@Test
	public void getBookingsByUserId_IfPresent() throws Exception {
		
		List<Booking> mockBookingList = List.of(new Booking(1L,new UserWeb(1L, "john", "1234", "john@example.com"),new Product(1L, new Category(1L,"Fruits"), "Apple",10,50)));
		
        Mockito.when(bookingRepository.findByUserId(1L)).thenReturn(mockBookingList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/booking/1")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].user.username").value("john"))
                .andExpect(jsonPath("$[0].user.password").value("1234"))
                .andExpect(jsonPath("$[0].user.email").value("john@example.com"))
                .andExpect(jsonPath("$[0].product.category.categoryname").value("Fruits"))
                .andExpect(jsonPath("$[0].product.productname").value("Apple"))
                .andExpect(jsonPath("$[0].product.stock").value(10))
                .andExpect(jsonPath("$[0].product.price").value(50));
	
	}
	
	
	//List Booking - No bookings
	
	@Test
	public void getBookingsByUserId_IfNotPresent() throws Exception {
		
        Mockito.when(bookingRepository.findByUserId(1L)).thenReturn(List.of());
        RequestBuilder request = MockMvcRequestBuilders
                .get("/booking/1")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("No bookings found for User ID: 1"));
	
	}
	
}
	
	