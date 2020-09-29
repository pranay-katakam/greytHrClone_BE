package com.nineleaps.greytHRClone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class UnauthorisedException extends RuntimeException {
    public UnauthorisedException(String exception) {
        super(exception);
    }
}


