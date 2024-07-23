package com.harena.api.Exception;

public class BadRequestException extends Exception {
    public BadRequestException() {
        super("BadRequestException", "Bad request");
    }
}
