package com.nineleaps.greytHRClone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
public class InvalidPasswordException extends RuntimeException{
    public InvalidPasswordException(String exception) {
        super(exception);
    }

}
