package com.example.demo.controller;

import com.example.demo.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    // up to down... so first exception is more specific exception.
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Arithmetic Exception")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Arithmetic Exception")
    @ExceptionHandler(value = ArithmeticException.class)
    public Map<String, String> handleArithmeticException(ArithmeticException e) {
        Map<String, String> result = new HashMap<>();
        result.put("errorMsg", e.getMessage());
        result.put("status", "error");
        return result;
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Request")
    @ExceptionHandler(value = {BaseException.class})
    public String handleBaseException(BaseException e) {
        return e.getMessage();
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
    @ExceptionHandler(value = Exception.class)
    public Map<String, String> handleException(Exception e) {
        Map<String, String> result = new HashMap<>();
        result.put("errorMsg", e.getMessage());
        result.put("status", "error");

        return result;
    }

}
