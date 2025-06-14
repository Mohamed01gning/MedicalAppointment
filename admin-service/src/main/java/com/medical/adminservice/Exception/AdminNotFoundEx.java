package com.medical.adminservice.Exception;

public class AdminNotFoundEx extends RuntimeException
{
    private String msg;

    public AdminNotFoundEx(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
