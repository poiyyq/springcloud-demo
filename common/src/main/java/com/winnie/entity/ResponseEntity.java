package com.winnie.entity;

import lombok.Data;

@Data
public class ResponseEntity {
    private static final String SUCCESS = "200";
    private static final String ERROR = "500";
    private String retCode;
    private String msg;
    private Object data;

    public ResponseEntity(String retCode, String msg, Object data) {
        this.retCode = retCode;
        this.msg = msg;
        this.data = data;
    }
    public static ResponseEntity success(String msg){
        return success(msg, null);
    }
    public static ResponseEntity success(String msg, Object data){
        return new ResponseEntity(SUCCESS, msg, data);
    }

    public static ResponseEntity error(String msg){
        return error(msg, null);
    }
    public static ResponseEntity error(String msg, Object data){
        return new ResponseEntity(ERROR, msg, data);
    }
}
