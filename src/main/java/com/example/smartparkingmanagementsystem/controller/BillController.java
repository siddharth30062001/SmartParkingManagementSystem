package com.example.smartparkingmanagementsystem.controller;

import com.example.smartparkingmanagementsystem.dto.BillResponseDTO;
import com.example.smartparkingmanagementsystem.model.Bill;
import com.example.smartparkingmanagementsystem.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/bills")
public class BillController {

    @Autowired
    private BillService bill;

    @GetMapping("generate/{reservationId}")
    public BillResponseDTO generateBill(@PathVariable Long reservationId) {
        return bill.generateBill(reservationId);
    }

    @PutMapping("/pay/{billId}")
    public String payBill(@PathVariable Long billId) {
        return bill.payBill(billId);
    }
}
