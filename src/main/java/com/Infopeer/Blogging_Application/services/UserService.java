package com.Infopeer.Blogging_Application.services;

import com.Infopeer.Blogging_Application.payloads.UserDto;
import com.Infopeer.Blogging_Application.payloads.UserResponce;

public interface UserService {
	
	UserDto createUser(UserDto user);
	
	UserDto updateUser(UserDto user , Integer userId);
	
	UserDto getUserById(Integer userId);
	
	UserResponce getAllUsers(Integer pageNumber , Integer pageSize);
	
	void deleteUser(Integer userId);

}
