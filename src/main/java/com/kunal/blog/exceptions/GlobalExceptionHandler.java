package com.kunal.blog.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.kunal.blog.payloads.ApiResponse;

// Global exception handler to manage exceptions thrown by controllers

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Handles ResourceNotFoundException
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){

		// Extracts error message from the exception
		String message = ex.getMessage();

		// Creates an ApiResponse object with the error message and sets success to false
		ApiResponse apiResponse = new ApiResponse(message, false);

		// Returns a ResponseEntity with the ApiResponse and HTTP status NOT_FOUND
		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}


	// Handles MethodArgumentNotValidException
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){

		// Creates a map to hold field names and error messages
		Map<String, String> resp = new HashMap<>();

		// Iterates through all validation errors
		ex.getBindingResult().getAllErrors().forEach(error -> {
			// Retrieves the field name causing the validation error
			String fieldName = ((FieldError)error).getField();
			// Retrieves the error message associated with the validation error
			String message = error.getDefaultMessage();
			// Adds the field name and error message to the response map
			resp.put(fieldName, message);
		});
		// Returns a ResponseEntity with the response map and HTTP status BAD_REQUEST
		return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
	}
}
