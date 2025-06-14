package com.medical.secretaireservice.Exception;

import java.util.HashMap;
import java.util.Map;

public class SecValidationEx extends RuntimeException
{
    private Map<String,String> errs=new HashMap<>();

    public SecValidationEx(Map<String, String> errs) {
        this.errs = errs;
    }

    public Map<String, String> getError() {
        return errs;
    }
}
