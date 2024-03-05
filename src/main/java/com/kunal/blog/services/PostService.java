package com.kunal.blog.services;

import java.util.List;

import com.kunal.blog.payloads.PostDto;
import com.kunal.blog.payloads.PostResponse;

public interface PostService {


	// CREATE / ADD POST
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	// UPDATE POST
	PostDto updatePost(PostDto postDto, Integer postId);

	// DELETE POST
	void deletePost(Integer postId);

	// GET ALL POSTS
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	// GET POST BY POST_ID
	PostDto getPostById(Integer postId);

	// GET POSTS BY CATEGORY
	List<PostDto> getPostByCategory(Integer categoryId);

	// GET POSTS BY USER
	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize);

	// SEARCH POSTS
	List<PostDto> searchPost(String keyword);












}
