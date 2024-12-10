package com.example.smartparkingmanagementsystem.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotRequestDTO {

    private Long slotNumber;
    private int level;
    private String vehicleType;
}
