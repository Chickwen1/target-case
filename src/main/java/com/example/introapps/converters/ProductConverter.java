package com.example.introapps.converters;

import com.example.introapps.dtos.ProductDTO;
import com.example.introapps.models.Product;

public class ProductConverter {

private ProductConverter() {
		
	}
	
	public static ProductDTO convert(Product p) {
		ProductDTO product = new ProductDTO();
		product.setProductId(p.getProductId());
		product.setProductName(p.getProductName());
		product.setPrice(p.getPrice());
		return product;
	}
	
	public static Product convert(ProductDTO p) {
		Product product = new Product();
		product.setProductId(p.getProductId());
		product.setProductName(p.getProductName());
		product.setPrice(p.getPrice());
		return product;
	}
}
