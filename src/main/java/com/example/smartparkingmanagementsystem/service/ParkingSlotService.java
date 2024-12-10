package com.example.smartparkingmanagementsystem.service;

import com.example.smartparkingmanagementsystem.dto.ParkingSlotRequestDTO;
import com.example.smartparkingmanagementsystem.dto.ParkingSlotResponseDTO;
import com.example.smartparkingmanagementsystem.exception.SlotUnavailableException;
import com.example.smartparkingmanagementsystem.model.ParkingSlot;
import com.example.smartparkingmanagementsystem.repository.ParkingSlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ParkingSlotService {
    @Autowired
    private ParkingSlotRepository parkingSlotRepository;

    public List<ParkingSlotRequestDTO> getAllSlots() {
        return parkingSlotRepository.findAll().stream()
                .map((slot)->new ParkingSlotRequestDTO(slot.getSlotNumber(),slot.getLevel(),slot.getVehicleType()))
                .toList();
    }

    public ParkingSlotRequestDTO reserveSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new SlotUnavailableException("Slot not available"));
        if (!slot.isAvailable()) {
            throw new SlotUnavailableException("Slot already reserved");
        }
        slot.setAvailable(false);
        return mapToDTO(parkingSlotRepository.save(slot));
    }

    public ParkingSlotRequestDTO releaseSlot(Long slotId) {
        ParkingSlot slot = parkingSlotRepository.findById(slotId)
                .orElseThrow(() -> new SlotUnavailableException("Slot not found"));
        slot.setAvailable(true);
        return mapToDTO(parkingSlotRepository.save(slot));
    }


    public ParkingSlotRequestDTO addSlot(ParkingSlotRequestDTO parkingSlotRequestDTO) {
        ParkingSlot getParkingSlot= parkingSlotRepository.findBySlotNumber(parkingSlotRequestDTO.getSlotNumber());
        ParkingSlot parkingSlot = new ParkingSlot();
        parkingSlot.setSlotNumber(parkingSlotRequestDTO.getSlotNumber());
        parkingSlot.setLevel(parkingSlotRequestDTO.getLevel());
        parkingSlot.setVehicleType(parkingSlotRequestDTO.getVehicleType());
        if(getParkingSlot==null) {
            parkingSlotRepository.save(parkingSlot);
            return parkingSlotRequestDTO;
        }
        else
            throw new SlotUnavailableException("Slot number "+parkingSlot.getSlotNumber()+" already exists");
    }

    private ParkingSlotRequestDTO mapToDTO(ParkingSlot parkingSlot) {
        ParkingSlotRequestDTO parkingSlotDTO = new ParkingSlotRequestDTO();
        parkingSlotDTO.setSlotNumber(parkingSlot.getSlotNumber());
        parkingSlotDTO.setLevel(parkingSlot.getLevel());
        parkingSlotDTO.setVehicleType(parkingSlot.getVehicleType());
        return parkingSlotDTO;
    }

    public HashMap<Long, String> getStatus() {
        HashMap<Long, String> status = new HashMap<>();
        List<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
        parkingSlots.forEach(parkingSlot -> {status.put(parkingSlot.getSlotNumber(), parkingSlot.isAvailable()?"available":"occupied");});
        return status;
    }

    public List<ParkingSlotRequestDTO> filterParkingSlotsByVehicleType() {
        List<ParkingSlot> parkingSlots = parkingSlotRepository.findAll();
        List<ParkingSlot> filteredParkingSlots = parkingSlots.stream()
                .filter(parkingSlot -> parkingSlot.getVehicleType() != null)
                .sorted((slot1, slot2) -> slot1.getVehicleType().compareToIgnoreCase(slot2.getVehicleType()))
                .toList();
        return mapToDTOList(filteredParkingSlots);
    }

    private List<ParkingSlotRequestDTO> mapToDTOList(List<ParkingSlot> parkingSlots) {
        return parkingSlots.stream().map(parkingSlot -> {
            ParkingSlotRequestDTO parkingSlotDTO = new ParkingSlotRequestDTO();
            parkingSlotDTO.setSlotNumber(parkingSlot.getSlotNumber());
            parkingSlotDTO.setLevel(parkingSlot.getLevel());
            parkingSlotDTO.setVehicleType(parkingSlot.getVehicleType());
            return parkingSlotDTO;
        }).toList();
    }
}



