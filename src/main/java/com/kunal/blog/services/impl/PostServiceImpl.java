package com.kunal.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kunal.blog.entities.Category;
import com.kunal.blog.entities.Post;
import com.kunal.blog.entities.User;
import com.kunal.blog.exceptions.ResourceNotFoundException;
import com.kunal.blog.payloads.PostDto;
import com.kunal.blog.payloads.PostResponse;
import com.kunal.blog.repositories.CategoryRepo;
import com.kunal.blog.repositories.PostRepo;
import com.kunal.blog.repositories.UserRepo;
import com.kunal.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	// Method to create a new post
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		// Retrieve the user by userId, throw ResourceNotFoundException if not found
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "User Id", userId));
		// Retrieve the category by categoryId, throw ResourceNotFoundException if not found
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));

		// Map the PostDto to Post entity
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);

		// Save the post
		Post savedPost = this.postRepo.save(post);

		// Map the saved post entity back to PostDto and return
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	// Method to update an existing post
	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		// Retrieve the post by postId, throw ResourceNotFoundException if not found
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post Id", postId));
		// Update post details
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		this.postRepo.save(post);

		// Map the updated post entity back to PostDto and return
		return this.modelMapper.map(post, PostDto.class);
	}

	// Method to delete a post by postId
	@Override
	public void deletePost(Integer postId) {
		// Retrieve the post by postId, throw ResourceNotFoundException if not found
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		// Delete the post
		this.postRepo.delete(post);

	}

	// Method to retrieve all posts with pagination and sorting
	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		// Determine sorting direction
		Sort sort = (sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending());

		// Create Pageable object for pagination and sorting
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);

		// Retrieve a page of posts from the repository
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> allPosts = pagePost.getContent();

		// Map Post entities to PostDto objects
		List<PostDto> postDtos = allPosts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		// Create and populate PostResponse object with paginated post data
		PostResponse postResponse = new PostResponse();
		postResponse.setContenet(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	// Method to retrieve a post by postId
	@Override
	public PostDto getPostById(Integer postId) {
		// Retrieve the post by postId, throw ResourceNotFoundException if not found
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post ID", postId));
		// Map the post entity to PostDto and return
		return this.modelMapper.map(post, PostDto.class);
	}

	// Method to retrieve posts by category
	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		// Retrieve the category by categoryId, throw ResourceNotFoundException if not found
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "category Id", categoryId));
		// Retrieve posts belonging to the specified category
		List<Post> posts = this.postRepo.findByCategory(cat);

		// Map Post entities to PostDto objects
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		return postDtos;
	}

	// Method to retrieve posts by user with pagination
	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
		// Retrieve the user by userId, throw ResourceNotFoundException if not found
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "user id", userId));
		// Create Pageable object for pagination
		Pageable p = PageRequest.of(pageNumber, pageSize);

		// Retrieve a page of posts belonging to the specified user
		Page<Post> pagePost = this.postRepo.findByUser(user, p);
		List<Post> posts = pagePost.getContent();

		// Map Post entities to PostDto objects
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

		// Create and populate PostResponse object with paginated post data
		PostResponse postResponse = new PostResponse();
		postResponse.setContenet(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	// Method to search posts by keyword
	@Override
	public List<PostDto> searchPost(String keyword) {
		// Retrieve posts containing the specified keyword in their title
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		// Map Post entities to PostDto objects
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
