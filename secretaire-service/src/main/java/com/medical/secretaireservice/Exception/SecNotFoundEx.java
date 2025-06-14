package com.medical.secretaireservice.Exception;

public class SecNotFoundEx extends RuntimeException
{
    private String msg;

    public SecNotFoundEx(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
