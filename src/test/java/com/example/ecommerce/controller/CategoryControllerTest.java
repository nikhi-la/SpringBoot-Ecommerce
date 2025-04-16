package com.example.ecommerce.controller;
import org.junit.jupiter.api.Test;
import com.example.ecommerce.baseclasses.Category;import com.example.ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.mockito.Mockito;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(CategoryController.class)
public class CategoryControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	//-------------------------------------------------------------------------------
	
	//Add category - new category
	@Test
	public void createCategories_NewCategory() throws Exception {
		
		Category mockCategory = new Category(1L,"Fruits");
		
        Mockito.when(categoryRepository.findByCategoryname("Fruits")).thenReturn(Optional.empty());

        RequestBuilder request = MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryname\":\"fruits\"}")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Category Added"));
	
	}
	
	//Add category - existing category
	
	@Test
	public void createCategories_ExistingCategory() throws Exception {
		
		Category mockCategory = new Category(1L,"Fruits");
		
        Mockito.when(categoryRepository.findByCategoryname("Fruits")).thenReturn(Optional.of(mockCategory));

        RequestBuilder request = MockMvcRequestBuilders
                .post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"categoryname\":\"Fruits\"}")
                .accept(MediaType.APPLICATION_JSON);
        
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().string("Category Exist"));
	
	}
	
	
	//-------------------------------------------------------------------------------
	
		//View category - if category present 
		@Test
		public void getAllCategories_IfPresent() throws Exception {
			
			List<Category> mockCategoryList = List.of(new Category(1L, "Fruits"),new Category(2L, "Vegetables"));
			
	        Mockito.when(categoryRepository.findAll()).thenReturn(mockCategoryList);
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/category")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.length()").value(2))
	                .andExpect(jsonPath("$[0].id").value(1))
	                .andExpect(jsonPath("$[0].categoryname").value("Fruits"))
	                .andExpect(jsonPath("$[1].id").value(2))
	                .andExpect(jsonPath("$[1].categoryname").value("Vegetables"));
		
		}
		
		
		//View category - if no categories present 
		
		@Test
		public void getAllCategories_IfNotPresent() throws Exception {
			
			Mockito.when(categoryRepository.findAll()).thenReturn(List.of());
	        RequestBuilder request = MockMvcRequestBuilders
	                .get("/category")
	                .accept(MediaType.APPLICATION_JSON);
	        
	        mockMvc.perform(request)
	                .andExpect(status().isOk())
	                .andExpect(jsonPath("$.length()").value(0));
		
		}
			
	
}
