package com.base.model.dto;

import java.io.Serializable;

/**
 * Created by hxjd on 2017/5/26.
 */
public class ResponseDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private int handle;
    private int code;
    private String time;
    private int responseStatus;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getHandle() {
        return handle;
    }

    public void setHandle(int handle) {
        this.handle = handle;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}
