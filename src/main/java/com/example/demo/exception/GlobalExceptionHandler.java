package com.example.demo.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * @author codeeasily
 * @date 2022/07/18 09:28
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        if (e instanceof BindException) {
            BindException be = (BindException) e;
            return be.getBindingResult().getFieldError().getDefaultMessage();
        }
        return e.getMessage();
    }
}
