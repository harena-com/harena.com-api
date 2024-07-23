package com.harena.api.Exception;

public class TooManyRequestsException extends Exception {
    public TooManyRequestsException() {
        super("TooManyRequestsException", "Too many requests");
    }
}
