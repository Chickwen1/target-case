package com.example.introapps.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.introapps.apiClient.ProductInfoClient;
import com.example.introapps.converters.ProductConverter;
import com.example.introapps.dtos.ProductDTO;
import com.example.introapps.exceptions.InvalidProductException;
import com.example.introapps.models.Product;
import com.example.introapps.repos.ProductRepo;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductInfoClient productInfoClient;
	
	public Product findProduct(String productId) throws JsonParseException, JsonMappingException, IOException {
		Product product = productRepo.getProductByproductId(productId);
		if (product == null) {
			throw new InvalidProductException("Invalid Product Id");
		}
		product.setProductName(this.getTitleForProduct(productId));
		return product;
	}
	

	@SuppressWarnings({"unchecked","rawtypes"}) 
	private String getTitleForProduct(String productId) throws JsonParseException, JsonMappingException, IOException {
		Map<String, Map> infoMap = getProductInfoFromProductInfoService(productId);

		Map<String,Map> productMap = infoMap.get("product");
        Map<String,Map> itemMap = productMap.get("item");
        Map<String,String> prodDescrMap = itemMap.get(("product_description"));
        
        return prodDescrMap.get("title");
	}
	
	@SuppressWarnings({"unchecked","rawtypes"})
	private Map<String, Map> getProductInfoFromProductInfoService(String productId) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper infoMapper = new ObjectMapper();
		System.out.println(productInfoClient);
		ResponseEntity<String> response = productInfoClient.getProductInfoById(productId);
		System.out.println(response.getStatusCode().value());
		Map<String, Map> infoMap = infoMapper.readValue(response.getBody(), Map.class);
		
		return infoMap;
	}
	
	public Product update(ProductDTO productDTO) {
		Product product = ProductConverter.convert(productDTO);
		return productRepo.save(product);
	}
}
