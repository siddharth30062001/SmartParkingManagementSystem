package com.example.smartparkingmanagementsystem.service;

import com.example.smartparkingmanagementsystem.dto.BillResponseDTO;
import com.example.smartparkingmanagementsystem.model.Bill;
import com.example.smartparkingmanagementsystem.model.Reservation;
import com.example.smartparkingmanagementsystem.repository.BillRepository;
import com.example.smartparkingmanagementsystem.repository.ReservationRepository;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class BillService {

    private BillRepository billRepository;
    private ReservationRepository reservationRepository;

    @Autowired
    private EmailService emailService;
    public BillService(BillRepository billRepository, ReservationRepository reservationRepo) {
        this.billRepository = billRepository;
        this.reservationRepository = reservationRepo;

    }

    public BillResponseDTO generateBill(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId).get();
        if(billRepository.existsByReservation(reservation)){
            throw new IllegalArgumentException("Bill already exists for this reservation");
        }

        Long duration = Duration.between(reservation.getStartTime(), reservation.getEndTime()).toMinutes();
        Double durationInMinutes = duration / 60.0;
        Bill bill = new Bill();
        if(reservation.getVehicleType().equalsIgnoreCase("Bike")) {
            bill.setAmount((durationInMinutes*1.0));
        }
        if(reservation.getVehicleType().equalsIgnoreCase("Car")) {
            bill.setAmount(durationInMinutes*3.0);
        }
        if(reservation.getVehicleType().equalsIgnoreCase("Truck")) {
            bill.setAmount(durationInMinutes*5.0);
        }
        bill.setPaymentStatus("Unpaid");
        bill.setReservation(reservation);
        return mapToDTO(billRepository.save(bill));
    }

    public String payBill(Long billId) {
        Bill bill = billRepository.findById(billId).orElse(null);
        if(bill.getPaymentStatus().equalsIgnoreCase("Paid")) {
            throw new IllegalArgumentException("Bill payment already paid");
        }
        bill.setPaymentStatus("Paid");
        billRepository.save(bill);
        String subject = "Payment successful";
        String text = "You have paid your parking reservation bill "+bill.getAmount()+"$ successfully.\n Visit again...";
        emailService.sendEmail(bill.getReservation().getUser().getEmail(),subject,text);
        return "Payment Successful";
    }

    private BillResponseDTO mapToDTO(Bill bill) {
        BillResponseDTO billDTO = new BillResponseDTO();
        billDTO.setBillId(bill.getBillId());
        billDTO.setAmount(bill.getAmount());
        billDTO.setPaymentStatus(bill.getPaymentStatus());
        billDTO.setReservation(bill.getReservation());
        return billDTO;
    }
}

