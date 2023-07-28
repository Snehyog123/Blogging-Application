package com.Infopeer.Blogging_Application.services;

import java.util.List;

import com.Infopeer.Blogging_Application.payloads.CategoryDto;
import com.Infopeer.Blogging_Application.payloads.CategoryResponce;

public interface CategoryService {
	
	// create 
	CategoryDto createCategory(CategoryDto categoryDto);
	
	// update 
	CategoryDto updateCategory(CategoryDto categoryDto , Integer categoryId);
	
	// delete
	void deleteCategory(Integer categoryId);
	
	// get 
	CategoryDto getCategoryById(Integer categoryId);
	
	// getAll
	CategoryResponce getAllCategories(Integer pageNumber , Integer pageSize);
}
