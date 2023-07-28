package com.Infopeer.Blogging_Application.controllers;

import java.util.List;

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
import com.Infopeer.Blogging_Application.payloads.CategoryDto;
import com.Infopeer.Blogging_Application.payloads.CategoryResponce;
import com.Infopeer.Blogging_Application.services.CategoryService;
import com.Infopeer.Blogging_Application.utils.AppConstant;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class CategoryController {
	
	/**
	 * @author SUYOG SHIRSATH
	 * 
	 * @apiNote This Api is contains multiple Api for creating , 
	 *    updating , getting , deleting
	 *  @see createCategory
	 *  @see updateCategory
	 *  @see deleteCategory
	 *  @see getCategoryById
	 *  @see getAllCategories
	 */
	
	@Autowired
	private CategoryService categoryService;
	
	Logger logger=LoggerFactory.getLogger(CategoryController.class);
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This is createCategory Api .This api send request to CategoryServiceImpl to save User in DB
	 * @param categoryDto It takes parameter from CategoryDto
	 * @return  It return CategoryDto which takes parameter from user as a responce 
	 */
	
	@PostMapping("/categories")
	public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto)
	{
		logger.info("Initiating request for save Category Controller");
		CategoryDto createCategory = this.categoryService.createCategory(categoryDto);
		logger.info("Completed request for save Category Controller");
		return new ResponseEntity<CategoryDto>(createCategory , HttpStatus.CREATED);
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This api is used for update Category data.This send data to CategoryServiceImpl
	 * @param categoryDto It takes parameter from CategoryDto
	 * @param categoryId It takes parameter as categoryId
	 * @return It returns CategoryDto and {@code 200} ok
	 */
	
	@PutMapping("/categories/{categoryId}")
	public ResponseEntity<String> updateCategory(@Valid @RequestBody CategoryDto categoryDto , @PathVariable Integer categoryId)
	{
		logger.info("Initiating request for update Category Controller");
		CategoryDto updateCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		logger.info("Completed request for update Category Controller");
		return new ResponseEntity<>(AppConstant.CATEGORY_UPDATE_SUCCESS , HttpStatus.OK);
		
	}
	
	@DeleteMapping("/categories/{categoryId}")
	public ResponseEntity<String> deleteCategory(@PathVariable Integer categoryId)
	{
		logger.info("Initiating request for Delete Category Controller");
		this.categoryService.deleteCategory(categoryId);
		logger.info("Completed request for Delete Category Controller");
		return new ResponseEntity<String>(AppConstant.CATEGORY_DELETE_SUCCESS , HttpStatus.OK);
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This api is used to get Category by categoryId , this api send request to CategoryServiceImpl
	 * @param categoryId IT takes categoryId with PathParameter
	 * @return It return CategoryDto which takes data from User with {@code 302} found
	 */
	
	@GetMapping("/categories/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId)
	{
		logger.info("Initiating request for Get CategoryById Controller");
		CategoryDto categoryById = this.categoryService.getCategoryById(categoryId);
		logger.info("Completed request for Get CategoryById Controller");
		return new ResponseEntity<CategoryDto>(categoryById , HttpStatus.FOUND);
	}
	
	/**
	 * @author SUYOG SHIRSATH
	 * @apiNote This api is used for getting all Category , It also contains pagination . It sends request to CategoryServiceImpl
	 * @param pageNumber we used RequestParameter to get pageNumber data.
	 * @param pageSize   we used RequestParameter to get pageSize data.
	 * @return CategoryResponce which contains List<CategoryDto> and PageDetails
	 */
	
	@GetMapping("/categories")
	public ResponseEntity<CategoryResponce> getAllCategories(
			@RequestParam(value = "pageNumber",defaultValue = "0",required = false) Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize )
	{
		logger.info("Initiating request for Get AllCategory Controller");
		CategoryResponce allCategories = this.categoryService.getAllCategories(pageNumber, pageSize);
		logger.info("Completed request for Get AllCategory Controller");
		return new ResponseEntity<CategoryResponce>(allCategories , HttpStatus.FOUND);	
	}
	

}
