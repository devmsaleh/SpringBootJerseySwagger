package com.his.exceptions;

public class CustomException extends Exception {

    public CustomException() {
        super("Custom Exception happened !");
    }

    public CustomException(String message) {
        super(message);
    }

}
