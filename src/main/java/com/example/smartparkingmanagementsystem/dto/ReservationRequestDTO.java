package com.example.smartparkingmanagementsystem.dto;

;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationRequestDTO {

    private Long userId;
    private Long slotId;
    @Pattern(regexp = "^[A-Z]{2}[0-9]{2}[A-Z]{2}[0-9]{4}$", message = "Check VehicleNumber format")
    private String vehicleNumber;
    @PastOrPresent(message = "Start time should be past or present")
    private LocalDateTime startTime;
    @Future(message = "End time should be future")
    private LocalDateTime endTime;
    private String vehicleType;
}
