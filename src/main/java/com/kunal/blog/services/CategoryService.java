package com.kunal.blog.services;

import java.util.List;

import com.kunal.blog.payloads.CategoryDto;

public interface CategoryService {
	
	// CREATE
	CategoryDto createCategory(CategoryDto categoryDto);
	
	
	// UPDATE
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	
	// DELETE
	void deleteCategory(Integer categoryId);
	
	
	// GET
	CategoryDto getCategory(Integer categoryId);
	
	
	// GET ALL
	List<CategoryDto> getAllCategory();
	

}
