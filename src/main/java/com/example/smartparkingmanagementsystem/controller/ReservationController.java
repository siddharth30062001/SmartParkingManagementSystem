package com.example.smartparkingmanagementsystem.controller;


import com.example.smartparkingmanagementsystem.dto.ReservationRequestDTO;
import com.example.smartparkingmanagementsystem.dto.ReservationResponseDTO;
import com.example.smartparkingmanagementsystem.model.Reservation;
import com.example.smartparkingmanagementsystem.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/addReservation")
    public ReservationResponseDTO getReservation(@RequestBody ReservationRequestDTO reservationRequestDTO){

        return reservationService.addReservation(reservationRequestDTO);
    }

    @PutMapping("/cancelReservation/{Id}")
    public void cancelReservation(@PathVariable Long reservationId){

        reservationService.cancelReservation(reservationId);
    }

    @GetMapping("/getReservation/{reservationId}")
    public List<ReservationResponseDTO> getReservationById(@PathVariable Long reservationId){

        return reservationService.getAllReservations(reservationId);
    }


}
