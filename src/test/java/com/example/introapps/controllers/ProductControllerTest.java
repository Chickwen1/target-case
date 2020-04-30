package com.example.introapps.controllers;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.example.introapps.converters.ProductConverter;
import com.example.introapps.dtos.ProductDTO;
import com.example.introapps.models.Product;
import com.example.introapps.services.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTest {
	
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productServiceMock;

	@InjectMocks
	ProductController productController;

	@Before
	public void setUp() {

		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void findProductById() throws Exception {

		Product productObj = new Product();

		productObj.setProductId(1);
		productObj.setProductName("Cat Food");
		productObj.setPrice(7.99);
		
		when(productServiceMock.findProduct(productObj.getProductId())).thenReturn(productObj);

		String productJson = new ObjectMapper().writeValueAsString(productObj);
		mockMvc.perform(get("/products/" + productObj.getProductId()).contentType(MediaType.APPLICATION_JSON).content(productJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.productId").value(1))
				.andExpect(jsonPath("$.productName").value("Cat Food")).andExpect(jsonPath("$.price").value(7.99));
	}
	
	@Test
	public void findProductByIdNull() throws Exception {

		Product productObj = new Product();

		productObj.setProductId(1);
		productObj.setProductName("Cat Food");
		productObj.setPrice(7.99);

		when(productServiceMock.findProduct(productObj.getProductId())).thenReturn(null);

		String assessJson = new ObjectMapper().writeValueAsString(productObj);
		mockMvc.perform(get("/products/" + productObj.getProductId()).contentType(MediaType.APPLICATION_JSON).content(assessJson))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void testUpdateProductById() throws Exception {

		ProductDTO productObj = new ProductDTO();

		productObj.setProductId(1);
		productObj.setProductName("Cat Food");
		productObj.setPrice(7.99);
		
		Product p = ProductConverter.convert(productObj);
		when(productServiceMock.update(any(ProductDTO.class))).thenReturn(p);

		String productJson = new ObjectMapper().writeValueAsString(productObj);
		mockMvc.perform(put("/products/update/" + productObj.getProductId()).contentType(MediaType.APPLICATION_JSON).content(productJson))
				.andExpect(status().isOk()).andExpect(jsonPath("$.productId").value(1))
				.andExpect(jsonPath("$.productName").value("Cat Food")).andExpect(jsonPath("$.price").value(7.99));
	}
	
	@Test
	public void testUpdateProductByIdNull() throws Exception {

		ProductDTO productObj = new ProductDTO();

		productObj.setProductId(1);
		productObj.setProductName("Cat Food");
		productObj.setPrice(7.99);

		when(productServiceMock.update(any(ProductDTO.class))).thenReturn(null);

		String assessJson = new ObjectMapper().writeValueAsString(productObj);
		mockMvc.perform(put("/products/update/" + productObj.getProductId()).contentType(MediaType.APPLICATION_JSON).content(assessJson))
				.andExpect(status().isNotFound());
	}
}
