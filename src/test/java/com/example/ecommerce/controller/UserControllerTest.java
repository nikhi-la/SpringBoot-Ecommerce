package com.example.ecommerce.controller;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import com.example.ecommerce.baseclasses.UserWeb;
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

@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserRepository userRepository;
	
	//-------------------------------------------------------------------------------
	
	//For register - new user
	
	@Test
	public void registerUser_UserNotExist() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john\", \"password\":\"1234\", \"email\":\"john@example.com\"}")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully"));
	
	}
	
	//Register existing user
	
	@Test
	public void registerUser_UserExist() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.of(mockUser));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john\", \"password\":\"1234\", \"email\":\"john@example.com\"}")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Username already exists!"));
	
	}
	
	//-------------------------------------------------------------------------------
	
	//For Login - registered user
	
	@Test
	public void loginUser_UserExist() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.of(mockUser));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john\", \"password\":\"1234\", \"email\":\"john@example.com\"}")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Login successful"))
                .andExpect(jsonPath("$.userid").value("1"))
                .andExpect(jsonPath("$.usertype").value("customer"));

	}
	
	//Login - not registered user

	@Test
	public void loginUser_UserNotExist() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\":\"john\", \"password\":\"1234\", \"email\":\"john@example.com\"}")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Invalid username or password"));

	}
	
	//Login -  registered user but password mismatches

		@Test
		public void loginUser_UserExistWrongPassword() throws Exception {
			
			UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
	        Mockito.when(userRepository.findByUsername("john")).thenReturn(Optional.of(mockUser));

	        RequestBuilder request = MockMvcRequestBuilders
	                .post("/login")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"username\":\"john\", \"password\":\"wrongpassword\", \"email\":\"john@example.com\"}")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.message").value("Invalid username or password"));

		}

	
	
	//------------------------------------------------------------------------------
	
	// Retrieve specific user - user exists
		
	@Test
	public void getOneUser_userExist() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/1")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1)) // If `getId()` is present
                .andExpect(jsonPath("$.username").value("john"))
                .andExpect(jsonPath("$.password").value("1234"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.usertype").value("customer"));
		
		
		
	
	}
	
	// Retrieve specific user - user Not exists
	
	
	@Test
	public void getOneUser_userNotExist() throws Exception {
		
		UserWeb mockUser = new UserWeb(1L, "john", "1234", "john@example.com");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .get("/user/1")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
        		.andExpect(status().isOk())
                .andExpect(content().string("User Not Found"));
	
	}

}

