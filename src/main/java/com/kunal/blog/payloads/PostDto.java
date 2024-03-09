package com.kunal.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.kunal.blog.entities.Comment;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PostDto {
	
	private Integer postId;
	
	private String title;
		
	private String content;
	
	private String imageName;
	
	private Date addedDate;
		
	private CategoryDto category;
	
	private UserDto user;
	
	private Set<Comment> comments = new HashSet<>();

}
