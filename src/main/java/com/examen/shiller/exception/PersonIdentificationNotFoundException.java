package com.examen.shiller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PersonIdentificationNotFoundException extends RuntimeException{
    public PersonIdentificationNotFoundException(String message) {
        super(message);
    }
}
