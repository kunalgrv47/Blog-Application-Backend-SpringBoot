package com.kunal.blog.services;

import java.util.List;

import com.kunal.blog.payloads.PostDto;

public interface PostService {
	
	
	// CREATE / ADD POST
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
	
	// UPDATE POST
	PostDto updatePost(PostDto postDto, Integer postId);
	
	// DELETE POST
	void deletePost(Integer postId);
	
	// GET ALL POSTS
	List<PostDto> getAllPost();
	
	// GET POST BY POST_ID
	PostDto getPostById(Integer postId);
	
	// GET POSTS BY CATEGORY
	List<PostDto> getPostByCategory(Integer categoryId);
	
	// GET POSTS BY USER
	List<PostDto> getPostByUser(Integer userId);
	
	// SEARCH POSTS
	List<PostDto> searchPost(String keyword);
	
	

}
