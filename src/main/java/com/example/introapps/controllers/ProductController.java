package com.example.introapps.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.introapps.dtos.ProductDTO;
import com.example.introapps.models.Product;
import com.example.introapps.services.ProductService;

@RestController
@RequestMapping("products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@GetMapping("/{id}")
	public ResponseEntity<Object> findProduct(@PathVariable("productId") Integer productId) throws Exception {
		Product p = productService.findProduct(productId);
		if (p != null) {
			return new ResponseEntity<Object>(p ,HttpStatus.OK);
		}
		return new ResponseEntity<Object>(p ,HttpStatus.NOT_FOUND);
		
	}
	
	@PutMapping(value="/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
	@Transactional(isolation=Isolation.READ_COMMITTED, propagation=Propagation.REQUIRED)
	public @ResponseBody ResponseEntity<Product> update(@Valid @RequestBody ProductDTO productDTO) {        
		Product temp = productService.update(productDTO);
		if(temp == null) return new ResponseEntity<>(temp, HttpStatus.BAD_REQUEST);
	      return new ResponseEntity<>(temp, HttpStatus.OK);
	} 
}
