package com.peckingbird.avarice.exception;

public class NotFoundException extends AvariceException {

    public NotFoundException(String message) {
        super("NotFound", message);
    }
}
