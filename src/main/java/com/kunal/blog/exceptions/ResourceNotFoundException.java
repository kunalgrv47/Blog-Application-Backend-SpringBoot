package com.kunal.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String resoutceName;
	String fieldName;
	long fieldValue;
	
	
	public ResourceNotFoundException(String resoutceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %l", resoutceName, fieldName, fieldValue));
		this.resoutceName = resoutceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
