package com.peckingbird.avarice.exception;

public abstract class AvariceException extends RuntimeException {
    public final String code;

    public final String message;

    protected AvariceException(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
