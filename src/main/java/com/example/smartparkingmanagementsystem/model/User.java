package com.example.smartparkingmanagementsystem.model;

import com.example.smartparkingmanagementsystem.dto.UserRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String name;
    private String email;
    private String phone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Reservation> reservations;

    public User(UserRequestDTO userRequestDTO) {
        this.name= userRequestDTO.getName();
        this.email= userRequestDTO.getEmail();
        this.phone= userRequestDTO.getPhone();
    }



}
