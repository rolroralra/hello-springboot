package com.example.demo.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {
    private final int errorCode;
    private final String errorDescription;

    public ClientException(String message, int errorCode, String errorDescription) {
        super(message);
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }
}
