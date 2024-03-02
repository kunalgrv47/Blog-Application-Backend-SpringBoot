package com.kunal.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kunal.blog.entities.Category;
import com.kunal.blog.exceptions.ResourceNotFoundException;
import com.kunal.blog.payloads.CategoryDto;
import com.kunal.blog.repositories.CategoryRepo;
import com.kunal.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Autowired
	private ModelMapper modelMapper;

	// Method to create a new category
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	// Method to update an existing category
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// Find the category by its id or throw an exception if not found
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		// Update the category details
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());

		// Save the updated category
		Category updatedCategory = this.categoryRepo.save(category);

		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	// Method to delete a category
	@Override
	public void deleteCategory(Integer categoryId) {
		// Find the category by its id or throw an exception if not found
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		// Delete the category
		this.categoryRepo.delete(category);
	}

	// Method to get a category by its id
	@Override
	public CategoryDto getCategory(Integer categoryId) {
		// Find the category by its id or throw an exception if not found
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		return this.modelMapper.map(category, CategoryDto.class);
	}

	// Method to get all categories
	@Override
	public List<CategoryDto> getAllCategory() {
		// Retrieve all categories from the repository
		List<Category> categoryList = this.categoryRepo.findAll();

		// Convert the list of categories to a list of category DTOs
		List<CategoryDto> categoryDtoList = new ArrayList<>();
		for (Category cat : categoryList) {
			categoryDtoList.add(this.modelMapper.map(cat, CategoryDto.class));
		}

		// Alternatively, use a one-liner with streams
		// List<CategoryDto> categoryListDto = categoryList.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());

		return categoryDtoList;
	}

}
