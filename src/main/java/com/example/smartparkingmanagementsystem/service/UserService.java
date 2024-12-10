package com.example.smartparkingmanagementsystem.service;

import com.example.smartparkingmanagementsystem.dto.UserRequestDTO;
import com.example.smartparkingmanagementsystem.dto.UserResponseDTO;
import com.example.smartparkingmanagementsystem.exception.UserNotFoundException;
import com.example.smartparkingmanagementsystem.model.Reservation;
import com.example.smartparkingmanagementsystem.model.User;
import com.example.smartparkingmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserRequestDTO registerUser(UserRequestDTO userRequestDTO) {
        User user=new User(userRequestDTO);
        return mapToDTO(userRepository.save(user));
    }

    private UserRequestDTO mapToDTO(User save) {

        UserRequestDTO requestDTO= new UserRequestDTO();
        requestDTO.setName(save.getName());
        requestDTO.setEmail(save.getEmail());
        requestDTO.setPhone(save.getPhone());

        return requestDTO;
    }

    public UserRequestDTO getUserDetailsById(Long userId) {
         User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
         return mapToDTO(user);
    }

    public UserRequestDTO updateUser(Long userId,UserRequestDTO userRequestDTO ) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("Id not found"));
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setPhone(userRequestDTO.getPhone());
        return mapToDTO(userRepository.save(user));
    }
}
