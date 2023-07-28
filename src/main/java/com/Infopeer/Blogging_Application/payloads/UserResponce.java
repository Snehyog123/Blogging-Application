package com.Infopeer.Blogging_Application.payloads;

import java.util.List;

import com.Infopeer.Blogging_Application.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class UserResponce {
	
	private  List<UserDto> content;
	
	private Integer pageNumber;
	
	private Integer pageSize;
	
	private Long totalElements;
	
	private Integer totalPages;
	
	private boolean lastPage;

}
