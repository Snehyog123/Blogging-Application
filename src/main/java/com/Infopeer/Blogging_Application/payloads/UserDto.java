package com.Infopeer.Blogging_Application.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter@Setter
public class UserDto {
	
	
	private Integer id;
	
	@NotEmpty
	@Size(min = 4 , message="Username must be min of 4 character !!!")
	private String name;
	
	@Email(message = "Email Address is not valid !!!")
	private String email;
	
	@NotEmpty
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,16}$" ,
	message = "Password Must be with 8-16 Character,Must Contain 1 Uppercase, 1 lowercase, "
			+ "1 Special Character And Atleast one Numeric Between 0-9 !!!!!")
//	@Size(min = 4 , max = 10 , message="Password Must be within 3-10 Character !!!")
	private String password;
	
	@NotEmpty
	private String about;
	

	

}
