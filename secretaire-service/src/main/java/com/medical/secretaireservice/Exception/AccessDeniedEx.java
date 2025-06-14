package com.medical.secretaireservice.Exception;

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
