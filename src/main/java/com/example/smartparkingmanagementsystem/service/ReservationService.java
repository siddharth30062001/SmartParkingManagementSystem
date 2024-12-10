package com.example.smartparkingmanagementsystem.service;

import com.example.smartparkingmanagementsystem.dto.ReservationRequestDTO;
import com.example.smartparkingmanagementsystem.dto.ReservationResponseDTO;
import com.example.smartparkingmanagementsystem.exception.ReservationConflictException;
import com.example.smartparkingmanagementsystem.exception.SlotUnavailableException;
import com.example.smartparkingmanagementsystem.exception.UserNotFoundException;
import com.example.smartparkingmanagementsystem.model.Bill;
import com.example.smartparkingmanagementsystem.model.ParkingSlot;
import com.example.smartparkingmanagementsystem.model.Reservation;
import com.example.smartparkingmanagementsystem.model.User;
import com.example.smartparkingmanagementsystem.repository.ParkingSlotRepository;
import com.example.smartparkingmanagementsystem.repository.ReservationRepository;
import com.example.smartparkingmanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepo;
    @Autowired
    private ParkingSlotRepository parkingSlotRepo;
    @Autowired
    private UserRepository userRepo;

    public ReservationResponseDTO addReservation(ReservationRequestDTO reservationRequestDTO) {
        Reservation existingReservation = reservationRepo.findByVehicleNumber(reservationRequestDTO.getVehicleNumber());
        if (existingReservation != null && existingReservation.getStatus().equals("ACTIVE")) {
            throw new ReservationConflictException("Reservation already exists for this vehicle.");
        }

        ParkingSlot parkingSlot = parkingSlotRepo.findById(reservationRequestDTO.getSlotId()).orElse(null);
        if(!reservationRequestDTO.getVehicleType().equalsIgnoreCase(parkingSlot.getVehicleType())){
            throw new SlotUnavailableException("Slot is not reserved for this vehicle.");
        }

        ParkingSlot slot = parkingSlotRepo.findById(reservationRequestDTO.getSlotId())
                .orElseThrow(() -> new SlotUnavailableException("Invalid slot ID. Slot not found."));
        if (!slot.isAvailable()) {
            throw new SlotUnavailableException("The selected slot is not available.");
        }

        User user = userRepo.findById(reservationRequestDTO.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Invalid user ID. User not found."));

        Reservation reservation = new Reservation();
        reservation.setUser(user); // Set the user
        reservation.setSlotId(slot.getParkingSlotId()); // Set the slot ID
        reservation.setVehicleNumber(reservationRequestDTO.getVehicleNumber());
        reservation.setStartTime(reservationRequestDTO.getStartTime());
        reservation.setEndTime(reservationRequestDTO.getEndTime());
        reservation.setVehicleType(reservationRequestDTO.getVehicleType());
        reservation.setStatus("ACTIVE");

        Reservation savedReservation = reservationRepo.save(reservation);
        user.getReservations().add(savedReservation);
        slot.setAvailable(false);
        parkingSlotRepo.save(slot);

        return mapToDTO(savedReservation);
    }


    public ReservationResponseDTO cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepo.findById(reservationId).orElseThrow(() -> new ReservationConflictException("Invalid reservation ID."));
        ParkingSlot slot = parkingSlotRepo.findById(reservation.getSlotId()).orElseThrow(() -> new SlotUnavailableException("You have not registered slot."));
        if(reservation.getStatus().equals("ACTIVE") && !slot.isAvailable()) {
            reservation.setStatus("CANCELLED");
            slot.setAvailable(true);
        }
        Reservation savedReservation = reservationRepo.save(reservation);
        return mapToDTO(savedReservation);
    }

    public List<ReservationResponseDTO> getAllReservations(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new UserNotFoundException("Invalid user ID."));
        List<Reservation> reservations = user.getReservations();
        return mapToDTOList(reservations);
    }

    private List<ReservationResponseDTO> mapToDTOList(List<Reservation> reservations) {
        return reservations.stream()
                .map(reservation -> mapToDTO(reservation)).toList();
    }


    private ReservationResponseDTO mapToDTO(Reservation reservation) {
        ReservationResponseDTO reservationDTO = new ReservationResponseDTO();
        reservationDTO.setId(reservation.getReservationId());
        reservationDTO.setUserId(reservation.getUser().getUserId());
        reservationDTO.setSlotId(reservation.getSlotId());
        reservationDTO.setVehicleNumber(reservation.getVehicleNumber());
        reservationDTO.setStartTime(reservation.getStartTime());
        reservationDTO.setEndTime(reservation.getEndTime());
        reservationDTO.setVehicleType(reservation.getVehicleType());
        reservationDTO.setStatus(reservation.getStatus());
        return reservationDTO;

    }
}

