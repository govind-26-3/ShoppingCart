package com.ty.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ty.dto.CategoryDto;

import com.ty.exception.CategoryNotFound;
import com.ty.model.Category;

import com.ty.repository.CategoryRepository;
import com.ty.responsestructure.ResponseStructure;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository cRepo;

	@Override
	public ResponseEntity<?> getAllCategories(Integer pageNumber) {

		if (pageNumber == null || pageNumber < 1) {
			pageNumber = 1;
		}

		int pageSize = 3;

		Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);

		Page<Category> paginatedCategories = cRepo.findAll(pageable);

		if (paginatedCategories.isEmpty()) {
			ResponseStructure<String> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.NOT_FOUND.value());
			rs.setMessage("Invalid page number. No categories available.");
			rs.setData("Page number: " + pageNumber);
			return new ResponseEntity<>(rs, HttpStatus.NOT_FOUND);
		}

		ResponseStructure<List<Category>> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Categories fetched successfully.");
		rs.setData(paginatedCategories.getContent());

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<?> createCategory(Category category) {
		// TODO Auto-generated method stub
		Optional<Category> getCategory = cRepo.findByName(category.getName());

		if (getCategory.isPresent()) {
			ResponseStructure<String> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.BAD_REQUEST.value());
			rs.setData("Cannot add category: " + category.getName());
			rs.setMessage("Category already present.");

			return new ResponseEntity<>(rs, HttpStatus.BAD_REQUEST);
		}

		else {
			cRepo.save(category);

			ResponseStructure<Category> rs = new ResponseStructure<>();
			rs.setStatusCode(HttpStatus.CREATED.value());
			rs.setMessage("New Category added successfully.");
			rs.setData(category);

			return new ResponseEntity<>(rs, HttpStatus.CREATED);

		}

	}

	@Override
	public ResponseEntity<?> getCategoryById(Integer id) {
		// TODO Auto-generated method stub
		Category getCategory = cRepo.findById(id)
				.orElseThrow(() -> new CategoryNotFound("Category not found with id: " + id));

		CategoryDto dto = new CategoryDto();
		BeanUtils.copyProperties(getCategory, dto);

		ResponseStructure<CategoryDto> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Category Found.");
		rs.setData(dto);

		return new ResponseEntity<>(rs, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> updateCategoryById(Integer id, Category category) {
		// TODO Auto-generated method stub
		Category getCategory = cRepo.findById(id)
				.orElseThrow(() -> new CategoryNotFound("Category not found with id: " + id));

		if (category.getName() != null && !category.getName().isEmpty()) {
			getCategory.setName(category.getName());
		}

		Category updateCategory = cRepo.save(getCategory);

		CategoryDto dto = new CategoryDto();
		BeanUtils.copyProperties(updateCategory, dto);

		ResponseStructure<CategoryDto> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Category Updated Successfully.");
		rs.setData(dto);

		return new ResponseEntity<>(rs, HttpStatus.OK);

	}

	@Override
	public ResponseEntity<?> deleteCategoryById(Integer id) {
		// TODO Auto-generated method stub
		cRepo.findById(id).orElseThrow(() -> new CategoryNotFound("Category not found with id: " + id));

		cRepo.deleteById(id);

		ResponseStructure<String> rs = new ResponseStructure<>();
		rs.setStatusCode(HttpStatus.OK.value());
		rs.setMessage("Category Deleted successfully.");
		rs.setData("Category deleted with id: " + id);

		return new ResponseEntity<>(rs, HttpStatus.OK);
	}

}
