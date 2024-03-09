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
	

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post id", postId));
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {
		
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		this.commentRepo.delete(comment);
	}

}
