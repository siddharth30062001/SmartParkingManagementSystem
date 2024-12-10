package com.example.smartparkingmanagementsystem.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotResponseDTO {

    private String slotNumber;
    private int level;
    private boolean isAvailable;
    private String vehicleType;
}
