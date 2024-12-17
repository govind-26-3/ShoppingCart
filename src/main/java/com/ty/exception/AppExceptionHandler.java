package com.ty.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ty.responsestructure.ResponseStructure;

@RestControllerAdvice
public class AppExceptionHandler {

	@ExceptionHandler(CategoryNotFound.class)
	public ResponseEntity<ResponseStructure<String>> catchCategoryNotFound(CategoryNotFound exception) {

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage(exception.getMessage());
		rs.setData("Not Found");

		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(ProductNotFound.class)
	public ResponseEntity<ResponseStructure<String>> catchProductNotFound(ProductNotFound exception) {

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.NOT_FOUND.value());
		rs.setMessage(exception.getMessage());
		rs.setData("Not Found");

		return new ResponseEntity<ResponseStructure<String>>(rs, HttpStatus.NOT_FOUND);
	}
}