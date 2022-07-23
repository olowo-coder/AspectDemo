package com.example.aspecttest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApplicationErrors> handleUnknownClient(UnknownClientException ex, WebRequest request){
        ApplicationErrors errors = new ApplicationErrors(ex.getMessage(), "403", request.getDescription(false));
        errors.setDate(new Date());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }
}
