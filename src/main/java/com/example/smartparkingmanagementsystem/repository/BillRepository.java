package com.example.smartparkingmanagementsystem.repository;

import com.example.smartparkingmanagementsystem.model.Bill;
import com.example.smartparkingmanagementsystem.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
    boolean existsByReservation(Reservation reservation);
}
