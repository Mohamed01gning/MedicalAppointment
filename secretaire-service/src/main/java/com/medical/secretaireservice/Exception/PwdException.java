package com.medical.secretaireservice.Exception;

public class PwdException extends RuntimeException
{
    private String msg;

    public PwdException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
