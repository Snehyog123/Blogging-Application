package com.Infopeer.Blogging_Application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Infopeer.Blogging_Application.entities.Category;
import com.Infopeer.Blogging_Application.entities.Post;
import com.Infopeer.Blogging_Application.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	

	List<Post> findByCategory(Category category);

	List<Post> findByUser(User user);
	
	List<Post> findByTitleContaining(String title);
	
}
