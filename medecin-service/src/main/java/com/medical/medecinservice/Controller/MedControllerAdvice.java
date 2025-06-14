package com.medical.medecinservice.Controller;

import com.medical.medecinservice.Exception.AccessDeniedEx;
import com.medical.medecinservice.Exception.MedecinNotFoundEx;
import com.medical.medecinservice.Exception.PwdException;
import com.medical.medecinservice.Exception.MedValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class MedControllerAdvice
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

    @ExceptionHandler(MedecinNotFoundEx.class)
    public ResponseEntity<Map<String,String>> handleNotFoundException(MedecinNotFoundEx ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error", ex.getMsg());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedEx.class)
    public ResponseEntity<Map<String,String>> handleAccessDeniedEx(AccessDeniedEx ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error", ex.getErrorMsg());
        return new ResponseEntity<>(errors, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(PwdException.class)
    public ResponseEntity<Map<String,String>> handleNotFoundException(PwdException ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error", ex.getMsg());
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ResponseEntity<Map<String,String>> handleRequestPartException(MissingServletRequestPartException ex)
    {
        Map<String,String> errors=new HashMap<>();
        errors.put("error", "Veuillez ajouter une image de profil");
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedValidationException.class)
    public ResponseEntity<Map<String,String>> handleValidationException(MedValidationException ex)
    {
        return new ResponseEntity<>(ex.getErrors(), HttpStatus.BAD_REQUEST);
    }
}
