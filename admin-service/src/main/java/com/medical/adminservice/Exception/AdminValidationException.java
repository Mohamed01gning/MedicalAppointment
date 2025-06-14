package com.medical.adminservice.Exception;

import java.util.HashMap;
import java.util.Map;

public class AdminValidationException extends RuntimeException
{
    private Map<String,String> errors=new HashMap<>();

    public AdminValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
