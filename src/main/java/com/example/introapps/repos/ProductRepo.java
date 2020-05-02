package com.example.introapps.repos;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.example.introapps.models.Product;

public interface ProductRepo extends MongoRepository<Product, String>{

	public Product getProductByproductId(String productId);
}