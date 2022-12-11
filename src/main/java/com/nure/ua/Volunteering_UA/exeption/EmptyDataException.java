package com.nure.ua.Volunteering_UA.exeption;



public class EmptyDataException extends RuntimeException{
    private final String message;

    public EmptyDataException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
