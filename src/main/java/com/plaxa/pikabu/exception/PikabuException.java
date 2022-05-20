package com.plaxa.pikabu.exception;

public class PikabuException extends RuntimeException {

    public PikabuException() {
    }

    public PikabuException(String message) {
        super(message);
    }

    public PikabuException(String message, Throwable cause) {
        super(message, cause);
    }

    public PikabuException(Throwable cause) {
        super(cause);
    }
}
