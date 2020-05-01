package com.example.introapps.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.introapps.exceptions.ErrorMessage;
import com.example.introapps.exceptions.InvalidProductException;

import static com.example.introapps.Constants.INVALID_PRODUCT_EXCEPTION;

@ControllerAdvice
public class RESTExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(InvalidProductException.class)
	public ResponseEntity<?> handleException(InvalidProductException e) {
		ErrorMessage errorMessage = new ErrorMessage(INVALID_PRODUCT_EXCEPTION);
		return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
	}
}