package com.medical.adminservice.Controller;


import com.medical.adminservice.Exception.AdminNotFoundEx;
import com.medical.adminservice.Exception.PwdException;
import com.medical.adminservice.Exception.AdminValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class AdminControllerAdvice
{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MethodArgumentNotValidException ex)
    {
        Map<String,String> errors=new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(field -> {
            errors.put(field.getField(), field.getDefaultMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminNotFoundEx.class)
    public ResponseEntity<Map<String,String>> handleNotFoundException(AdminNotFoundEx ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error", ex.getMsg());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PwdException.class)
    public ResponseEntity<Map<String,String>> handlePwdException(PwdException ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error", ex.getError());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AdminValidationException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(AdminValidationException ex)
    {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String,String>> handleRequestPartException(MissingServletRequestPartException ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error","Inserer Une Image S'il Vous Plait");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
