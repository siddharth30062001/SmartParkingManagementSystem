package com.example.smartparkingmanagementsystem.dto;

import com.example.smartparkingmanagementsystem.model.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillResponseDTO {
    private Long billId;
    private Reservation reservation;
    private Double amount;
    private String paymentStatus;
}
