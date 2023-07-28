package com.Infopeer.Blogging_Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Infopeer.Blogging_Application.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
