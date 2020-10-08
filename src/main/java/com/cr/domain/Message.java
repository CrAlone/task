package com.cr.domain;

public class Message {
    private Object data;
    private String msg;
    private Integer code;

    public Message() {
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
    public Message(Object data, String msg, Integer code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }
}
