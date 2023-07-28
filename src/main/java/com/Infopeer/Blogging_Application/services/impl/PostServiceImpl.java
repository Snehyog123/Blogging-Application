package com.Infopeer.Blogging_Application.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Infopeer.Blogging_Application.entities.Category;
import com.Infopeer.Blogging_Application.entities.Post;
import com.Infopeer.Blogging_Application.entities.User;
import com.Infopeer.Blogging_Application.exceptions.ResourceNotFoundException;
import com.Infopeer.Blogging_Application.payloads.PostDto;
import com.Infopeer.Blogging_Application.payloads.PostResponce;
import com.Infopeer.Blogging_Application.repository.CategoryRepo;
import com.Infopeer.Blogging_Application.repository.PostRepo;
import com.Infopeer.Blogging_Application.repository.UserRepo;
import com.Infopeer.Blogging_Application.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	Logger logger=LoggerFactory.getLogger(PostServiceImpl.class);
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		logger.info("Initiating request for save Post ServiceImpl");
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "UserId", userId));
	
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "CategoryId", categoryId));

		
//		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setAddedDate(new Date());
		post.setImageName("default.png");
        post.setCategory(category);
        post.setUser(user);
		
		
		Post addedPost = this.postRepo.save(post);
	
		logger.info("Completed request for save Post ServiceImpl");
		
		return this.modelMapper.map(addedPost, PostDto.class);
	}


	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		
		Post updatedUser = this.postRepo.save(post);
		return this.modelMapper.map(updatedUser, PostDto.class);
	}


	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		this.postRepo.delete(post);
	}


	@Override
	public PostResponce getAllPost(Integer pageNumber , Integer pageSize , String sortBy , String sortDir)  {
		
		Sort sort=(sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
	
		
		Pageable p=PageRequest.of(pageNumber, pageSize, sort);
		
	Page<Post> pagePost = this.postRepo.findAll(p);
	
	   List<Post> posts = pagePost.getContent();
	
		 List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	
		
		PostResponce postResponce=new PostResponce();
				postResponce.setContent(postDtos);
				postResponce.setPageNumber(pagePost.getNumber());
				postResponce.setPageSize(pagePost.getSize());
				postResponce.setTotalElements(pagePost.getTotalElements());
				postResponce.setTotalPages(pagePost.getTotalPages());
				postResponce.setLastPage(pagePost.isLast());
		
		
		return postResponce;
		
	}


	@Override
	public PostDto getByPostId(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		
		return this.modelMapper.map(post, PostDto.class);
	}


	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}


	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
		List<Post> users = this.postRepo.findByUser(user);
		List<PostDto> postDtos = users.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}


	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> postDto = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDto;
	}

}
