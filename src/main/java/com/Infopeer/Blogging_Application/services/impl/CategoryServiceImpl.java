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

import com.Infopeer.Blogging_Application.entities.Category;
import com.Infopeer.Blogging_Application.exceptions.ResourceNotFoundException;
import com.Infopeer.Blogging_Application.payloads.CategoryDto;
import com.Infopeer.Blogging_Application.payloads.CategoryResponce;
import com.Infopeer.Blogging_Application.repository.CategoryRepo;
import com.Infopeer.Blogging_Application.services.CategoryService;


@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelMapper;
	
	Logger logger=LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		logger.info("Initiating request for save Category ServiceImpl");
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category addedCat = categoryRepo.save(category);
		logger.info("Completed request for save Category ServiceImpl");
		return this.modelMapper.map(addedCat, CategoryDto.class);
	}
	
	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		logger.info("Initiating request for Update Category ServiceImpl");
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDiscription(categoryDto.getCategoryDiscription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		logger.info("Completed request for Update Category ServiceImpl");
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}
	
	@Override
	public void deleteCategory(Integer categoryId) {
		logger.info("Initiating request for Delete Category ServiceImpl");
		Category category = this.categoryRepo.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		this.categoryRepo.delete(category);
		logger.info("Completed request for Delete Category ServiceImpl");
	}
	
	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		logger.info("Initiating request for Get CategoryById ServiceImpl");
		Category category = this.categoryRepo.findById(categoryId)
		.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		logger.info("Completed request for Get CategoryById ServiceImpl");
		return this.modelMapper.map(category, CategoryDto.class);
	}
	
	@Override
	public CategoryResponce getAllCategories(Integer pageNumber , Integer pageSize) {
		logger.info("Initiating request for Get AllCategory ServiceImpl");
		
		Pageable p=PageRequest.of(pageNumber, pageSize);
		 Page<Category> pageCategory = this.categoryRepo.findAll(p);
		 
		 List<Category> categories = pageCategory.getContent();
		List<CategoryDto> categoryDtos = categories.stream().map((cat) -> this.modelMapper.map(cat, CategoryDto.class))
		.collect(Collectors.toList());
		logger.info("Completed request for Get AllCategory ServiceImpl");
		
		CategoryResponce categoryResponce=new CategoryResponce();
		categoryResponce.setContent(categoryDtos);
		categoryResponce.setPageNumber(pageCategory.getNumber());
		categoryResponce.setPageSize(pageCategory.getSize());
		categoryResponce.setTotalElements(pageCategory.getTotalElements());
		categoryResponce.setTotalPages(pageCategory.getTotalPages());
		categoryResponce.setLastPage(pageCategory.isLast());
		
		return categoryResponce;
	}

	

}
