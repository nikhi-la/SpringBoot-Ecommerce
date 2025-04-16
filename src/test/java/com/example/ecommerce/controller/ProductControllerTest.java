package com.example.ecommerce.controller;
import org.junit.jupiter.api.Test;
import com.example.ecommerce.baseclasses.Category;
import com.example.ecommerce.baseclasses.Product;
import com.example.ecommerce.repository.CategoryRepository;
import com.example.ecommerce.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.GetMapping;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ProductRepository productRepository;
	
	//------------------------------------------------------------------------------
	
	
	//View product - if product present 
	@Test
	public void getAllProducts_IfPresent() throws Exception {
		
		List<Product> mockProductList = List.of(new Product(1L, new Category(1L,"Fruits"), "Apple",10,50));
		
        Mockito.when(productRepository.findAll()).thenReturn(mockProductList);
        RequestBuilder request = MockMvcRequestBuilders
                .get("/product")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].category.id").value(1))
                .andExpect(jsonPath("$[0].category.categoryname").value("Fruits"))
                .andExpect(jsonPath("$[0].productname").value("Apple"))
                .andExpect(jsonPath("$[0].stock").value("10"))
                .andExpect(jsonPath("$[0].price").value("50"));
	
	}
	
	//View product - if product not present 
		@Test
		public void getAllProducts_IfNotPresent() throws Exception {
			
	        Mockito.when(productRepository.findAll()).thenReturn(List.of());
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/product")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.length()").value(0));
		}
	
	
	//------------------------------------------------------------------------------
		
	
	//Retreive specific product - If product present
	
	@Test
	public void getOneProduct_IfPresent() throws Exception {
		
		Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
		
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(mockProduct));
        RequestBuilder request = MockMvcRequestBuilders
                .get("/product/1")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category.id").value(1))
                .andExpect(jsonPath("$.category.categoryname").value("Fruits"))
                .andExpect(jsonPath("$.productname").value("Apple"))
                .andExpect(jsonPath("$.stock").value("10"))
                .andExpect(jsonPath("$.price").value("50"));
	
	}
	
	//Retreive specific product - If product not present
	
		@Test
		public void getOneProduct_IfNotPresent() throws Exception {
			
			
	        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/product/1")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(content().string("Product Not Found"));
		
		}
		
		//------------------------------------------------------------------------------
		
		// Search product by category - if product present
		
		@Test
		public void getProductByCategory_IfPresent() throws Exception {
			
			List<Product> mockProductList = List.of(new Product(1L, new Category(1L,"Fruits"), "Apple",10,50));
			
	        Mockito.when(productRepository.findByCategoryId(1L)).thenReturn(mockProductList);
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/product/category/1")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$[0].id").value(1))
	                .andExpect(jsonPath("$[0].category.id").value(1))
	                .andExpect(jsonPath("$[0].category.categoryname").value("Fruits"))
	                .andExpect(jsonPath("$[0].productname").value("Apple"))
	                .andExpect(jsonPath("$[0].stock").value("10"))
	                .andExpect(jsonPath("$[0].price").value("50"));
		
		}
		
		// Search product by category - if product not present
		
				@Test
				public void getProductByCategory_IfNotPresent() throws Exception {
					
			        Mockito.when(productRepository.findByCategoryId(1L)).thenReturn(List.of());
			        RequestBuilder request = MockMvcRequestBuilders
			                .get("/product/category/1")
			                .accept(MediaType.APPLICATION_JSON);
			        
			        mockMvc.perform(request)
			                .andExpect(status().isOk())
			                .andExpect(content().string("Product Not Found"));
				
				}
				
		//------------------------------------------------------------------------------
		
		// Search by product and category - if product present
		@Test
		public void getProductByCategoryAndProduct_IfPresent() throws Exception {
			
			Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
			
	        Mockito.when(productRepository.findByIdAndCategoryId(1L,1L)).thenReturn(Optional.of(mockProduct));
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/product/1/category/1")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.id").value(1))
	                .andExpect(jsonPath("$.category.id").value(1))
	                .andExpect(jsonPath("$.category.categoryname").value("Fruits"))
	                .andExpect(jsonPath("$.productname").value("Apple"))
	                .andExpect(jsonPath("$.stock").value("10"))
	                .andExpect(jsonPath("$.price").value("50"));
		
		}		
		
		// Search by product and category - if product present
				@Test
				public void getProductByCategoryAndProduct_IfNotPresent() throws Exception {
					
					Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
					
			        Mockito.when(productRepository.findByIdAndCategoryId(1L,1L)).thenReturn(Optional.empty());
			        RequestBuilder request = MockMvcRequestBuilders
			                .get("/product/1/category/1")
			                .accept(MediaType.APPLICATION_JSON);
			        
			        mockMvc.perform(request)
			                .andExpect(status().isOk())
			                .andExpect(content().string("No matching product found in this category"));
				
				}		
				
				
		
		//------------------------------------------------------------------------------
		
		// Add product - if product not present
		@Test
		public void addProduct_IfNotPresent() throws Exception {
			
			Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
			
	        Mockito.when(productRepository.findByProductname("Apple")).thenReturn(Optional.empty());
	        RequestBuilder request = MockMvcRequestBuilders
	                .post("/product")
	                .contentType(MediaType.APPLICATION_JSON)
	                .content("{\"categoryname\":\"Fruits\", \"productname\":\"Apple\", \"stock\":\"10\",\"quantity\":\"50\"}")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(content().string("Product Added"));
		
		}
		
		// Add product - if product  present
				@Test
				public void addProduct_IfPresent() throws Exception {
					
					Product mockProduct = new Product(1L, new Category(1L,"Fruits"), "Apple",10,50);
					
			        Mockito.when(productRepository.findByProductname("Apple")).thenReturn(Optional.of(mockProduct));
			        RequestBuilder request = MockMvcRequestBuilders
			                .post("/product")
			                .contentType(MediaType.APPLICATION_JSON)
			                .content("{\"categoryname\":\"Fruits\", \"productname\":\"Apple\", \"stock\":\"10\",\"quantity\":\"50\"}")
			                .accept(MediaType.APPLICATION_JSON);
			        
			        mockMvc.perform(request)
			                .andExpect(status().isOk())
			                .andExpect(content().string("Product Exist"));
				
				}
}
