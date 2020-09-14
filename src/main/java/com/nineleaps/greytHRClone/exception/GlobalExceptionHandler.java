package com.nineleaps.greytHRClone.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(DataAlreadyExistsException ex) {
        return ResponseEntity.status(CONFLICT).body(new ExceptionResponse(new Date(), "Already Exists", ex.getMessage()));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
        return ResponseEntity.status(BAD_REQUEST).body(new ExceptionResponse(new Date(), "Bad Request", ex.getMessage()));
    }
}