package com.kunal.blog.controllers;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kunal.blog.config.AppConstants;
import com.kunal.blog.payloads.ApiResponse;
import com.kunal.blog.payloads.PostDto;
import com.kunal.blog.payloads.PostResponse;
import com.kunal.blog.services.FileService;
import com.kunal.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;


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

		return new ResponseEntity<>(updatedPostDto, HttpStatus.OK);
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
	public ResponseEntity<PostResponse> getPostByUser(
			@PathVariable Integer userId,
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize){

		PostResponse postResponse= this.postService.getPostByUser(userId, pageNumber, pageSize);	
		return new ResponseEntity<>(postResponse, HttpStatus.OK);
	}


	// GET POSTS BY CATEGORY
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId){

		List<PostDto> postDtos = this.postService.getPostByCategory(categoryId);	
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}
	
	
	
	// GET ALL POSTS
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){

		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize, sortBy, sortDir);

		return new ResponseEntity<>(postResponse, HttpStatus.OK);	

	}
	
	
	// SEARCHING POSTS
	@GetMapping("posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		
		List<PostDto> postDtos = this.postService.searchPost(keywords);
		
		return new ResponseEntity<>(postDtos, HttpStatus.OK);
	}
	
	
	//Post Image Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<>(updatePost, HttpStatus.OK);
	}
	
	
	// Method to serve file
	@GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(
			@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException{
		
		InputStream resource = this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
		
	}

}
