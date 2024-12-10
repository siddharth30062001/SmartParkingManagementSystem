package com.example.smartparkingmanagementsystem.model;

import com.example.smartparkingmanagementsystem.dto.ParkingSlotRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long parkingSlotId;
    @Column(unique = true, nullable = false)
    private Long slotNumber;
    private int level;
    private boolean isAvailable=true;
    private String vehicleType;

    public ParkingSlot(ParkingSlotRequestDTO parkingSlotRequestDTO){
        this.slotNumber= parkingSlotRequestDTO.getSlotNumber();
        this.level= parkingSlotRequestDTO.getLevel();
        //this.isAvailable= parkingSlotRequestDTO.g
        this.vehicleType= parkingSlotRequestDTO.getVehicleType();


    }
}
