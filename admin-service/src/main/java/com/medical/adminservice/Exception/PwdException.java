package com.medical.adminservice.Exception;

public class PwdException extends RuntimeException
{

    private String error;

    public PwdException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
