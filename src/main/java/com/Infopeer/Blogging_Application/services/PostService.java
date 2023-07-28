package com.Infopeer.Blogging_Application.services;

import java.util.List;

import com.Infopeer.Blogging_Application.payloads.PostDto;
import com.Infopeer.Blogging_Application.payloads.PostResponce;

public interface PostService {
	
	//create
	PostDto createPost(PostDto postDto , Integer userId , Integer categoryId );	
	
	//update
	PostDto updatePost(PostDto postDto , Integer postId);
	
	//delete
	void deletePost(Integer postId);
	
	//getAll
	PostResponce getAllPost(Integer pageNumber , Integer pageSize , String sortBy,String sortDir);
	
	//getPostByPostId
	PostDto getByPostId(Integer postId);
	
	// Get all post by Category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	// Get all post by User
	List<PostDto> getPostsByUser(Integer userId);
	
	//serach posts
	List<PostDto> searchPosts(String keyword);
	

}
