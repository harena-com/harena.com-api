package com.harena.api.Exception;

public class InternalServerException extends Exception {
    public InternalServerException() {
        super("InternalServerException", "Unexpected error");
    }
}
