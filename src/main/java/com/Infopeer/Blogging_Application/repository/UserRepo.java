package com.Infopeer.Blogging_Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.Infopeer.Blogging_Application.entities.User;


public interface UserRepo extends JpaRepository<User, Integer> {

}
