package com.Infopeer.Blogging_Application.services;

import com.Infopeer.Blogging_Application.payloads.CommentDto;

public interface CommentService {
	
	CommentDto createComment(CommentDto commentDto ,Integer postId);
	
	void deleteComment(Integer commentId);

}
