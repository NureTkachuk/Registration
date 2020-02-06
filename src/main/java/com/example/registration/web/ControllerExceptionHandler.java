package com.example.registration.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public String businessExceptionHandling(BusinessException businessException) {
        return businessException.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public String entityNotFoundHandling(EntityNotFoundException entityNotFoundException) {
        return entityNotFoundException.getMessage();
    }
}
