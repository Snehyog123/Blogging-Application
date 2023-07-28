package com.Infopeer.Blogging_Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Infopeer.Blogging_Application.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
	

}
