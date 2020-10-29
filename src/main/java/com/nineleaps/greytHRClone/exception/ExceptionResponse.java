package com.nineleaps.greytHRClone.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExceptionResponse {
    private Date timestamp;
    private String error;
    private String message;
}

