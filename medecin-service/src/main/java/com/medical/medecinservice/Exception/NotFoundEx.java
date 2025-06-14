package com.medical.medecinservice.Exception;

import java.util.HashMap;
import java.util.Map;

public class NotFoundEx extends RuntimeException
{
    private String message;
    private Map<String,String> msg=new HashMap<>();

    public NotFoundEx(Map<String, String> msg) {
        this.msg = msg;
    }

    public Map<String,String> getMsg()
    {
        return msg;
    }
}
