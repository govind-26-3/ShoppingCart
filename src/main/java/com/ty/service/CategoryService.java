package com.ty.service;

import org.springframework.http.ResponseEntity;

import com.ty.model.Category;

public interface CategoryService {

	ResponseEntity<?> getAllCategories(Integer pageNumber);

	ResponseEntity<?> createCategory(Category category);

	ResponseEntity<?> getCategoryById(Integer id);

	ResponseEntity<?> updateCategoryById(Integer id,Category category);

	ResponseEntity<?> deleteCategoryById(Integer id);
}
