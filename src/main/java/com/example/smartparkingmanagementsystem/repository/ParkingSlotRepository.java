package com.example.smartparkingmanagementsystem.repository;

import com.example.smartparkingmanagementsystem.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlot,Long> {
    public ParkingSlot findBySlotNumber(Long slotNumber);
}
