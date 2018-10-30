package com.mix.tpl.entity;

public class Pack extends Base{
    private Integer status = 200;
    private Object data;
    private String msg;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Pack(Object data) {
        this.data = data;
    }

    public Pack(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
