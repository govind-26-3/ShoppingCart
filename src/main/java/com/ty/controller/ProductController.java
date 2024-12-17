package com.ty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ty.model.Product;
import com.ty.service.ProductService;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	@Autowired
	private ProductService pService;

	@PostMapping("/{id}")
	public ResponseEntity<?> createProduct(@PathVariable Integer id, @RequestBody Product product) {
		// TODO: process POST request

		return pService.createProduct(id, product);
	}

	@GetMapping("")
	public ResponseEntity<?> getAllProducts(@RequestParam Integer page) {
		return pService.getAllProducts(page);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getProductById(@PathVariable Integer id) {
		return pService.getProductById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
		// TODO: process PUT request

		return pService.updateProductById(id, product);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletById(@PathVariable Integer id) {
		// TODO: process PUT request

		return pService.deleteProductById(id);
	}

}
