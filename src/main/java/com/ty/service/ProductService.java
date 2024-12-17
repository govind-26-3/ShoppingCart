package com.ty.service;

import org.springframework.http.ResponseEntity;

import com.ty.model.Product;

public interface ProductService {

	ResponseEntity<?> getAllProducts(Integer pageNumber);

	ResponseEntity<?> createProduct(Integer cid,Product product);

	ResponseEntity<?> getProductById(Integer id);

	ResponseEntity<?> updateProductById(Integer id, Product product);

	ResponseEntity<?> deleteProductById(Integer id);
}
