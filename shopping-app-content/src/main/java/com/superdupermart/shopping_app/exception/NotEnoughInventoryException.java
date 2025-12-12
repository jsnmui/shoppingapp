package com.superdupermart.shopping_app.exception;

public class NotEnoughInventoryException extends RuntimeException {
    public NotEnoughInventoryException(String message) {
        super(message);
    }
}