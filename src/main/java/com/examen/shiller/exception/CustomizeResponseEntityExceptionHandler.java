package com.examen.shiller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
@RestController
public class CustomizeResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllException(Exception ex,
                                                           WebRequest request){
        return new ResponseEntity<>(new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false)), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({PersonNotFoundException.class,PersonIdentificationNotFoundException.class})
    public final ResponseEntity<Object> handleNotFoundException(Exception ex,
                                                           WebRequest request){
        return new ResponseEntity<>(new ExceptionResponse(new Date(),ex.getMessage(),request.getDescription(false)), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ExceptionResponse(new Date(),"Validation failed",
                ex.getBindingResult().getAllErrors().toString()), HttpStatus.BAD_REQUEST);
    }
}
