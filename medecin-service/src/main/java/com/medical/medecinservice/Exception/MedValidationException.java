package com.medical.medecinservice.Exception;

import java.util.HashMap;
import java.util.Map;

public class MedValidationException extends RuntimeException {

    private Map<String, String> errors=new HashMap<>();

    public MedValidationException(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }
}
