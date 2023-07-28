package com.Infopeer.Blogging_Application.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Infopeer.Blogging_Application.entities.Comment;
import com.Infopeer.Blogging_Application.entities.Post;
import com.Infopeer.Blogging_Application.exceptions.ResourceNotFoundException;
import com.Infopeer.Blogging_Application.payloads.CommentDto;
import com.Infopeer.Blogging_Application.repository.CommentRepo;
import com.Infopeer.Blogging_Application.repository.PostRepo;
import com.Infopeer.Blogging_Application.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);

		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);

		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentId) {

		Comment com = this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "CommentId", commentId));
		this.commentRepo.delete(com);

	}

}
