package com.peckingbird.avarice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidStateException extends AvariceException {
    public InvalidStateException(String message) {
        super("InvalidState", message);
    }
}
