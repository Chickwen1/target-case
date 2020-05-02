package com.example.introapps.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.introapps.dtos.ProductDTO;
import com.example.introapps.models.Product;
import com.example.introapps.services.ProductService;

@Component
@RestController
@RequestMapping(value="/products")
public class ProductController {
	
	@Autowired
	ProductService productService;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Product> findProduct(@PathVariable("productId") String productId) throws Exception {
		Product p = productService.findProduct(productId);
		if (p != null) {
			return new ResponseEntity<Product>(p ,HttpStatus.OK);
		}
		return new ResponseEntity<Product>(p ,HttpStatus.NOT_FOUND);
		
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, produces = "application/json")
	public @ResponseBody ResponseEntity<Product> update(@Valid @RequestBody ProductDTO productDTO, 
			@PathVariable("id") String productId) {        
		Product temp = productService.update(productDTO);
		if(temp == null) return new ResponseEntity<>(temp, HttpStatus.NOT_FOUND);
	      return new ResponseEntity<>(temp, HttpStatus.OK);
	} 
}
