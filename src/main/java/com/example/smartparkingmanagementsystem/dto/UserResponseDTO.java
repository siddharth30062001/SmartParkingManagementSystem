package com.example.smartparkingmanagementsystem.dto;

import com.example.smartparkingmanagementsystem.model.Reservation;
import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {

    private String name;
    private String email;
    private String phone;
    private List<Reservation> registeredVehicles;

}
