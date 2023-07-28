package com.Infopeer.Blogging_Application.payloads;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter

public class CategoryDto {
	
	private Integer categoryId;
	@NotNull
	@Size(min = 4 , message = "Min size of character should be more than 4")
	private String categoryTitle;
	
	@NotNull
	@Size(min = 10 , message = "Min size of character should be more than 10")
	private String categoryDiscription;

}
