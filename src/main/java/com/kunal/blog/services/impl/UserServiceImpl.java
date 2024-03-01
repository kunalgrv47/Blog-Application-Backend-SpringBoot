package com.kunal.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kunal.blog.entities.User;
import com.kunal.blog.exceptions.ResourceNotFoundException;
import com.kunal.blog.payloads.UserDto;
import com.kunal.blog.repositories.UserRepo;
import com.kunal.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    // Method to create a new user
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = this.dtoToUser(userDto);
        User savedUser = this.userRepo.save(user);
        return this.userToDto(savedUser);
    }

    // Method to update an existing user
    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        // Retrieve the user from the database, or throw a ResourceNotFoundException if not found
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        // Update user details
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());

        // Save the updated user
        User updatedUser = this.userRepo.save(user);

        return this.userToDto(updatedUser);
    }

    // Method to get user by ID
    @Override
    public UserDto getUserById(Integer userId) {

        // Retrieve the user from the database by ID, or throw a ResourceNotFoundException if not found
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));

        return this.userToDto(user);
    }

    // Method to get all users
    @Override
    public List<UserDto> getAllUsers() {

        // Retrieve all users from the database
        List<User> users = this.userRepo.findAll();

        // Convert User objects to UserDto objects
        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

        return userDtos;
    }

    // Method to delete a user by ID
    @Override
    public void deleteUser(Integer userId) {

        // Retrieve the user from the database by ID, or throw a ResourceNotFoundException if not found
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", " Id ", userId));
        
        // Delete the user from the database
        this.userRepo.delete(user);

    }

    // Method to convert UserDto object to User object
    private User dtoToUser(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
        return user;
    }

    // Method to convert User object to UserDto object
    private UserDto userToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

}
