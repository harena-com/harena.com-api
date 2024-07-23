package com.harena.api.Exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Exception extends RuntimeException {
    private final String type;
    private final String message;
}
