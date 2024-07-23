package com.harena.api.Exception;

public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException() {
        super("ResourceNotFoundException", "Resource of type <T> identified by <I> not found");
    }
}
