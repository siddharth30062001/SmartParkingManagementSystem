package com.example.smartparkingmanagementsystem.repository;

import com.example.smartparkingmanagementsystem.model.Reservation;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Reservation findByVehicleNumber(@Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message = "Correct the vehicle number format") String vehicleNumber);

    List<Reservation> findByEndTimeBeforeAndStatus(LocalTime now, String active);
}
