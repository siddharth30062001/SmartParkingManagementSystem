package com.example.smartparkingmanagementsystem.controller;

import com.example.smartparkingmanagementsystem.dto.ParkingSlotRequestDTO;
import com.example.smartparkingmanagementsystem.model.ParkingSlot;
import com.example.smartparkingmanagementsystem.service.ParkingSlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/parking-slots")
public class ParkingSlotController {
    @Autowired
    private ParkingSlotService parkingSlotService;

    @PostMapping("/addSlot")
    public ParkingSlotRequestDTO addSlot(@RequestBody ParkingSlotRequestDTO parkingSlotRequestDTO) {

        return parkingSlotService.addSlot(parkingSlotRequestDTO);
    }

    @GetMapping("/getAllSlots")
    public List<ParkingSlotRequestDTO> getAllSlots() {

        return parkingSlotService.getAllSlots();
    }

    @PostMapping("/reserve")
    public ParkingSlotRequestDTO reserveSlot(@RequestParam Long slotId) {

        return parkingSlotService.reserveSlot(slotId);
    }

    @PostMapping("/release")
    public ParkingSlotRequestDTO releaseSlot(@RequestParam Long slotId) {

        return parkingSlotService.releaseSlot(slotId);
    }

    @GetMapping("/filter")
    public List<ParkingSlotRequestDTO> filterParkingSlotsByVehicleType() {
        return parkingSlotService.filterParkingSlotsByVehicleType();
    }

    @GetMapping("/getStatus")
    public ResponseEntity<Map<Long,String>> getStatus() {

        return new ResponseEntity<>(parkingSlotService.getStatus(),HttpStatus.OK);
    }
}
