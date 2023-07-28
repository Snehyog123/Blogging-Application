package com.Infopeer.Blogging_Application.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.Infopeer.Blogging_Application.controllers.UserController;
import com.Infopeer.Blogging_Application.entities.User;
import com.Infopeer.Blogging_Application.exceptions.ResourceNotFoundException;
import com.Infopeer.Blogging_Application.payloads.UserDto;
import com.Infopeer.Blogging_Application.payloads.UserResponce;
import com.Infopeer.Blogging_Application.repository.UserRepo;
import com.Infopeer.Blogging_Application.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	
	@Override
	public UserDto createUser(UserDto userdto) {
		logger.info("Initiating request for save User ServiceImpl");
		
		User user = this.modelMapper.map(userdto, User.class);
  //	User user = this.dtoToUser(userdto);
		User savedUser = this.userRepo.save(user);
		// return this.userDtoToUser(savedUser);
		
		logger.info("Completed request for save User ServiceImpl");
		
		return this.modelMapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userdto, Integer userId) {
		logger.info("Initiating request for update User ServiceImpl");
		
	User user = this.userRepo.findById(userId)
	      .orElseThrow(() -> new ResourceNotFoundException("User" , "id",userId));
	
	user.setName(userdto.getName());
	user.setEmail(userdto.getEmail());
	user.setPassword(userdto.getPassword());
	user.setAbout(userdto.getAbout());
	
	User updatedUser = this.userRepo.save(user);
	UserDto userDto2 = this.modelMapper.map(updatedUser, UserDto.class);
	
	logger.info("Completed request for update User ServiceImpl");
	
		return userDto2;
	}
	
	

	@Override
	public UserDto getUserById(Integer userId) {
		logger.info("Initiating request for Get UserById ServiceImpl");
		
		 User user = this.userRepo.findById(userId).
				 orElseThrow(() -> new ResourceNotFoundException("User", " id", userId));
		 System.out.println(user);
		 
		 logger.info("Completed request for Get UserById ServiceImpl");
		 
		return this.modelMapper.map(user, UserDto.class);
	}
	

	@Override
	public UserResponce getAllUsers(Integer pageNumber , Integer pageSize) {
		logger.info("Initiating request for Get AllUser ServiceImpl ");
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		
		 Page<User> pageUser = this.userRepo.findAll(p);
		 
		 List<User> users = pageUser.getContent();
		 
		List<UserDto> userDtos = users.stream().map((user) -> this.modelMapper.map(user, UserDto.class))
				.collect(Collectors.toList());
		
		UserResponce userResponce=new UserResponce();
		userResponce.setContent(userDtos);
		userResponce.setPageNumber(pageUser.getNumber());
		userResponce.setPageSize(pageUser.getSize());
		userResponce.setTotalElements(pageUser.getTotalElements());
		userResponce.setTotalPages(pageUser.getTotalPages());
		userResponce.setLastPage(pageUser.isLast());
		
		logger.info("Completed request for Get AllUser ServiceImpl");
		return userResponce;
	
	}

	@Override
	public void deleteUser(Integer userId) {
		logger.info("Initiating request for Delete User ServiceImpl");
		User user = this.userRepo.findById(userId).
		orElseThrow(() -> new ResourceNotFoundException("user","Id", userId));
		this.userRepo.delete(user);
		logger.info("Completed request for Delete User ServiceImpl");

	}
	
//	public User dtoToUser(UserDto userDto)
//	{
//	  this.modelMapper.modelMapper().map(userDto, null)
//		user.setId(userDto.getId());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
//		return user;	
//	}
//	
//	public UserDto userDtoToUser(User user)
//	{
//		UserDto userDto=new UserDto();
//		userDto.setId(user.getId());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
//		return userDto;
//		
//	}

}
