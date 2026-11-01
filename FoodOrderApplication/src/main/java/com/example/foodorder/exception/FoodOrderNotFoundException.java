package com.example.foodorder.exception;

public class FoodOrderNotFoundException extends RuntimeException {

    public FoodOrderNotFoundException(String message) {
        super(message);
    }
}