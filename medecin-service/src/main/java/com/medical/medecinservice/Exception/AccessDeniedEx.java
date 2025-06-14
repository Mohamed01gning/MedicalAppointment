package com.medical.medecinservice.Exception;

import java.util.Map;

public class AccessDeniedEx extends RuntimeException
{
    private String errorMsg;

    public AccessDeniedEx(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
