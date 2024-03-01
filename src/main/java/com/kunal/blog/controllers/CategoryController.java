package com.kunal.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kunal.blog.payloads.ApiResponse;
import com.kunal.blog.payloads.CategoryDto;
import com.kunal.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;

	// CREATE
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto){
		
		CategoryDto savedCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
		
	}

	// UPDATE
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Integer categoryId){
		
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<>(updateCategory, HttpStatus.OK);
	}


	// DELETE
	@DeleteMapping("/{catId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer catId){
		
		this.categoryService.deleteCategory(catId);
		return new ResponseEntity<>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
	}


	// GET (get single category)
	@GetMapping("/{catId}")
	public ResponseEntity<CategoryDto> getCategory(@PathVariable Integer catId){
		
		CategoryDto categoryDto = this.categoryService.getCategory(catId);
		return new ResponseEntity<>(categoryDto, HttpStatus.OK);
	}


	// GET (get all categories)
	@GetMapping("/")
	public ResponseEntity<List<CategoryDto>> getAllCategory(){
		
		return new ResponseEntity<>(this.categoryService.getAllCategory(), HttpStatus.OK);
	}
}
