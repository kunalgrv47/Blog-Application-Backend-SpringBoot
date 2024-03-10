package com.kunal.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.kunal.blog.entities.Comment;
import com.kunal.blog.entities.Post;
import com.kunal.blog.exceptions.ResourceNotFoundException;
import com.kunal.blog.payloads.CommentDto;
import com.kunal.blog.repositories.CommentRepo;
import com.kunal.blog.repositories.PostRepo;
import com.kunal.blog.services.CommentService;

public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	// Method to create a new comment for a post
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		// Retrieve the post using postId, or throw an exception if not found
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
		// Map the CommentDto to a Comment entity
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		// Set the post for the comment
		comment.setPost(post);
		// Save the comment to the database
		Comment savedComment = this.commentRepo.save(comment);
		// Map the saved comment back to a CommentDto and return
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	// Method to delete a comment by its id
	@Override
	public void deleteComment(Integer commentId) {
		// Retrieve the comment using commentId, or throw an exception if not found
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		// Delete the comment from the database
		this.commentRepo.delete(comment);
	}

}
