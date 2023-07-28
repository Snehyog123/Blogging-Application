package com.Infopeer.Blogging_Application.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Infopeer.Blogging_Application.payloads.UserDto;
import com.Infopeer.Blogging_Application.payloads.UserResponce;
import com.Infopeer.Blogging_Application.services.UserService;
import com.Infopeer.Blogging_Application.utils.AppConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	/**
	 * @author SUYOG SHIRSATH
	 * 
	 * @apiNote This Api is contains multiple Api for creating , 
	 *    updating , getting , deleting
	 *  @see createUser
	 *  @see updateUser
	 *  @see getUsergetUserById
	 *  @see getAllUsers
	 *  @see deleteUser
	 */
	
	
	
	
	
	Logger logger=LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This is createUser Api .This api send request to UserServiceImpl to save User in DB
	 * @param UserDto It takes parameter from UserDto
	 * @return  It return UserDto which takes parameter from user as a responce 
	 */
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
	{
		logger.info("Initiating request for save User Controller");
		UserDto createUser = this.userService.createUser(userDto);
		logger.info("Completed request for save User Controller");
		return new ResponseEntity<>(createUser , HttpStatus.CREATED);
		
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This api is used for update User data.This send data to UserServiceImpl
	 * @param userDto It takes parameter from UserDto
	 * @param userId It takes parameter as userId
	 * @return It returns String as updated message and {@code 200} created
	 */
	
	@PutMapping("/{userId}")
	public ResponseEntity<String> updateUser(@Valid  @RequestBody UserDto userDto , @PathVariable Integer userId)
	{
		logger.info("Initiating request for update User Controller");
		
		 this.userService.updateUser(userDto, userId);
		
		 logger.info("Completed request for update User Controller");
		 
		return new ResponseEntity<String>(AppConstant.USER_UPDATE_SUCCESS , HttpStatus.OK);
		
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This api is used to get User by userId , this api send request to userServiceImpl
	 * @param userId IT takes userId with PathParameter
	 * @return It return UserDto which takes data from User with {@code 200} ok
	 */
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getUsergetUserById(@PathVariable Integer userId)
	{
		logger.info("Initiating request for Get UserById Controller");
		UserDto userById = this.userService.getUserById(userId);
		
		logger.info("Completed request for Get UserById Controller");
		
		return new ResponseEntity<UserDto>(userById , HttpStatus.OK);
		
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This api is used for getting all User , It also contains pagination . It sends request to UserServiceImpl
	 * @param pageNumber we used RequestParameter to get pageNumber data.
	 * @param pageSize   we used RequestParameter to get pageSize data.
	 * @return UserResponce which contains List<UserDto> and PageDetails
	 */
	
	@GetMapping("/")
	public ResponseEntity<UserResponce> getAllUsers(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10",required = false) Integer pageSize )
	{
		logger.info("Initiating request for Get AllUser Controller");
		
		return ResponseEntity.ok(this.userService.getAllUsers(pageNumber , pageSize));
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This Api is for delete User . It sends request to UserServiceImpl.
	 * @param userId IT takes userId with PathParameter.
	 * @return It return String message and {@code 200} ok
	 */
	
	
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable Integer userId)
	{
		logger.info("Initiating request for Delete User Controller");
		this.userService.deleteUser(userId);
		logger.info("Completed request for Delete User Controller");
		return new ResponseEntity<String>(AppConstant.USER_DELETE_SUCCESS ,HttpStatus.OK);
		
	}
	

}
