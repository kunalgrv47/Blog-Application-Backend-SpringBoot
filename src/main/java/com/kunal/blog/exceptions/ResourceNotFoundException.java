package com.kunal.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	private String resoutceName;
	private String fieldName;
	private long fieldValue;
	
	
	public ResourceNotFoundException(String resoutceName, String fieldName, long fieldValue) {
		super(String.format("%s not found with %s : %s", resoutceName, fieldName, fieldValue));
		this.resoutceName = resoutceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}
