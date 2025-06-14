package com.medical.medecinservice.Exception;

public class MedecinNotFoundEx extends RuntimeException
{
    private String msg;

    public MedecinNotFoundEx(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
