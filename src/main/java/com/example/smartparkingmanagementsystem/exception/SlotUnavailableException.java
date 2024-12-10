package com.example.smartparkingmanagementsystem.exception;

public class SlotUnavailableException extends RuntimeException{

    public SlotUnavailableException(String message){
        super(message);
    }
}
