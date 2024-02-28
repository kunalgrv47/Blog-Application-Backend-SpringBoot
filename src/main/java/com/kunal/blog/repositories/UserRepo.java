package com.kunal.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kunal.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}
