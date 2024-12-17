package com.ty.dto;

import com.ty.model.Category;

import lombok.Data;

@Data
public class ProductDto {

	private String name;

	private String description;

	private Double price;
	
	private Category category;

}
