package com.nineleaps.greytHRClone.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleAlreadyExistsException(DataAlreadyExistsException ex) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), "Already Exists", ex.getMessage()), CONFLICT);

    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), "Invalid Data", ex.getMessage()), HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UnauthorisedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorisedException(UnauthorisedException ex) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), "Unauthorised", ex.getMessage()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), "Resource not found", ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnsupportedMediaTypeException.class)
    public ResponseEntity<ExceptionResponse> handleUnsupportedMediaTypeException(UnsupportedMediaTypeException ex) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), "Only Image files allowed", ex.getMessage()), HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getDefaultMessage());
        }
        return ResponseEntity.status(BAD_REQUEST).body(errors);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionResponse> handleUnauthorizedException(UnauthorizedException ex) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(), "Unauthorized", ex.getMessage()), HttpStatus.UNAUTHORIZED);

    }
}

