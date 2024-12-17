package com.ty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ty.model.Category;
import com.ty.service.CategoryService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategoryService cService;

	@GetMapping("")
	public ResponseEntity<?> getAllCategories(@RequestParam Integer page) {
		return cService.getAllCategories(page);
	}

	@PostMapping("")
	public ResponseEntity<?> createCategory(@RequestBody Category category) {
		// TODO: process POST request

		return cService.createCategory(category);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getMethodName(@PathVariable Integer id) {
		return cService.getCategoryById(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> putMethodName(@PathVariable Integer id, @RequestBody Category category) {
		// TODO: process PUT request

		return cService.updateCategoryById(id, category);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@PathVariable Integer id) {
		return cService.deleteCategoryById(id);
	}

}
