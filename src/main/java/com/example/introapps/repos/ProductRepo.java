package com.example.introapps.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.introapps.models.Product;

public interface ProductRepo extends JpaRepository<Product,Integer>{

	Product save(Product product);
	
	@Query("select p from Product p where p.productId = :productId")
	Product findProductById(@Param("productId") Integer productId);
}