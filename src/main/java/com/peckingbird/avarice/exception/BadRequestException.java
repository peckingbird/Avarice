package com.peckingbird.avarice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends AvariceException {
    public BadRequestException(String message) {
        super("BadRequest", message);
    }
}
