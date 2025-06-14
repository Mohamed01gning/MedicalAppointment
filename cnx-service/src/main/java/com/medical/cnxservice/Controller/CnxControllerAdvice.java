package com.medical.cnxservice.Controller;

import com.medical.cnxservice.Exception.NotFoundEx;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class CnxControllerAdvice
{
    @ExceptionHandler(NotFoundEx.class)
    public ResponseEntity<Map<String,String>> notfoundEx(NotFoundEx ex)
    {
        return new ResponseEntity<>(ex.getMsg(), HttpStatus.BAD_REQUEST);
    }
}
