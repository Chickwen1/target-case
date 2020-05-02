package com.example.introapps.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.introapps.apiClient.ProductInfoClient;
import com.example.introapps.converters.ProductConverter;
import com.example.introapps.dtos.ProductDTO;
import com.example.introapps.exceptions.InvalidProductException;
import com.example.introapps.models.Product;
import com.example.introapps.repos.ProductRepo;

@Service
public class ProductService {

	@Autowired
	private ProductRepo productRepo;
	
	@Autowired
	private ProductInfoClient productInfoClient;
	
	@Transactional
	public Product findProduct(Integer productId) {
		Product product = productRepo.findProductById(productId);
		if (product == null) {
			throw new InvalidProductException("Invalid Product Id");
		}
		return product;
	}
	
	public Product update(ProductDTO productDTO) {
		Product product = ProductConverter.convert(productDTO);
		if(productRepo.findProductById(product.getProductId()) == null) return null;
		return productRepo.save(product);
	}
}
