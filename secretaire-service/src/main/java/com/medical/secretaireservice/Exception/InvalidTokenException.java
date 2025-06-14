package com.medical.secretaireservice.Exception;

public class InvalidTokenException extends RuntimeException
{
    private String msg;

    public InvalidTokenException(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
