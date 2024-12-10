package com.example.smartparkingmanagementsystem.controller;

import com.example.smartparkingmanagementsystem.dto.UserRequestDTO;
import com.example.smartparkingmanagementsystem.dto.UserResponseDTO;
import com.example.smartparkingmanagementsystem.model.User;
import com.example.smartparkingmanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public UserRequestDTO registerUser(@RequestBody UserRequestDTO userRequestDTO) {

        return userService.registerUser(userRequestDTO);
    }

    @GetMapping("/get/{userId}")
    public UserRequestDTO getUserDetailsBYId(@PathVariable Long userId) {

        return userService.getUserDetailsById(userId);
    }

    @PutMapping("/update/{userId}")
    public UserRequestDTO updateUser(@PathVariable Long userId, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(userId, userRequestDTO);
    }
}

