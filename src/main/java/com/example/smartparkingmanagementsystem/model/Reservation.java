package com.example.smartparkingmanagementsystem.model;

import com.example.smartparkingmanagementsystem.dto.ReservationRequestDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.example.smartparkingmanagementsystem.model.User;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservationId;
    @Column(insertable=true, updatable=true)
    private Long slotId;
    private String vehicleNumber;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String vehicleType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne(mappedBy = "reservation",fetch = FetchType.LAZY)
    @JsonIgnore
    private Bill bill;

    public Reservation(ReservationRequestDTO reservationRequestDTO){
        this.slotId= reservationRequestDTO.getSlotId();
        this.vehicleNumber= reservationRequestDTO.getVehicleNumber();
        this.startTime= reservationRequestDTO.getStartTime();
        this.endTime= reservationRequestDTO.getEndTime();
        this.vehicleType= reservationRequestDTO.getVehicleType();

    }

}
