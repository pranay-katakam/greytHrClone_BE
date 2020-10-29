package com.nineleaps.greytHRClone.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
public class UnsupportedMediaTypeException extends RuntimeException{
    public UnsupportedMediaTypeException(String exception) {
        super(exception);
    }
}
