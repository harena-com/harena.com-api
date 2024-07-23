package com.harena.api.Exception;

public class NotAuthorizedException extends Exception {
    public NotAuthorizedException() {
        super("NotAuthorizedException", "Not authorized");
    }
}
