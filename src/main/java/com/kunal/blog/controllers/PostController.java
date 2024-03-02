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
import com.kunal.blog.payloads.PostDto;
import com.kunal.blog.services.PostService;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;


	// SAVE POST TO DATABASE
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,@PathVariable Integer userId,@PathVariable Integer categoryId){

		PostDto savedPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<>(savedPost, HttpStatus.CREATED);

	}

	// UPDATE POST
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId){

		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);

		return new ResponseEntity<PostDto>(updatedPostDto, HttpStatus.OK);
	}
	
	
	// DELETE POST
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		
		this.postService.deletePost(postId);
		return new ResponseEntity<>(new ApiResponse("Post deleted successfullt", true), HttpStatus.OK);
	}


	// GET POST BY POST ID
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){

		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<>(postDto, HttpStatus.OK);
	}



	// GET POSTS BY USER
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

		List<PostDto> postDtos = this.postService.getPostByUser(userId);	
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}


	// GET POSTS BY CATEGORY
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){

		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);	
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}
	
	
	
	// GET ALL POSTS
	@GetMapping("/posts")
	public ResponseEntity<List<PostDto>> getAllPost(){

		List<PostDto> allPost = this.postService.getAllPost();

		return new ResponseEntity<>(allPost, HttpStatus.OK);	

	}




}
