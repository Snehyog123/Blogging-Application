package com.Infopeer.Blogging_Application.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.Infopeer.Blogging_Application.payloads.PostDto;
import com.Infopeer.Blogging_Application.payloads.PostResponce;
import com.Infopeer.Blogging_Application.services.FileService;
import com.Infopeer.Blogging_Application.services.PostService;
import com.Infopeer.Blogging_Application.utils.AppConstant;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {
	
	/**
	 * @author SUYOG SHIRSATH
	 * 
	 * This is PostController Later
	 *  
	 */
	
	Logger logger=LoggerFactory.getLogger(PostController.class);
	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	@Value("${project.image}")
	private String path;
	
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost
	       (@RequestBody PostDto postDto, 
			@PathVariable Integer userId ,
			@PathVariable Integer categoryId)
	{
		logger.info("Initiating request for save Post Controller");
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		logger.info("Completed request for save Post Controller");
		return new ResponseEntity<PostDto>(createPost , HttpStatus.CREATED);
	}
	
	@PutMapping("/post/{postId}")
	public ResponseEntity<String> updatePost(@RequestBody PostDto postDto ,@PathVariable Integer postId)
	{
		 this.postService.updatePost(postDto, postId);
		
		return new ResponseEntity<>(AppConstant.POST_UPDATE_SUCCESS , HttpStatus.OK);	
	}
	
	@DeleteMapping("/post/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Integer postId)
	{
		this.postService.deletePost(postId);
		return new ResponseEntity<String>(AppConstant.POST_DELETE_SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/post")
	public ResponseEntity<PostResponce> getAllPost(
			@RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) Integer pageNumber ,
			@RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) Integer pageSize ,
			@RequestParam(value = "sortBy" , defaultValue = AppConstant.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstant.SORT_DIR , required = false) String sortDir
			)
	{
		 PostResponce allPost = this.postService.getAllPost(pageNumber, pageSize , sortBy ,sortDir);
		return new ResponseEntity<PostResponce>(allPost , HttpStatus.FOUND);
	}
	
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId)
	{
		PostDto postDto = this.postService.getByPostId(postId);
		return new ResponseEntity<PostDto>(postDto , HttpStatus.FOUND);
	}
	
	
	@GetMapping("/posts/categoryId/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId)
	{
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCategory , HttpStatus.FOUND);
	}
	
	
	@GetMapping("/posts/userId/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId)
	{
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser , HttpStatus.FOUND);
	}
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostsByTitle(@PathVariable("keywords") String keywords)
	{
		List<PostDto> searchPosts = this.postService.searchPosts(keywords);
		return new ResponseEntity<List<PostDto>>(searchPosts , HttpStatus.OK);
		
	}
	
	// Post Iamge Upload
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(
			@RequestParam("image") MultipartFile image ,
			@PathVariable Integer postId) throws IOException
	{
		PostDto postDto = this.postService.getByPostId(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost ,HttpStatus.OK);
		
	}
	
	//method to serve files
		@GetMapping(value= "/post/image/{imageName}",produces = MediaType.IMAGE_JPEG_VALUE)
		public void downloadImage(
				@PathVariable("imageName") String imageName,
				HttpServletResponse response
				)throws IOException {
			
			InputStream resource =this.fileService.getResource(path, imageName);
			response.setContentType(MediaType.IMAGE_JPEG_VALUE);
			StreamUtils.copy(resource, response.getOutputStream());
		}
	
	

}
