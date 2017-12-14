package com.base.model.dto;

import java.io.Serializable;

/**
 * Created by hxjd on 2017/5/26.
 */
public class RequestDto implements Serializable{
    private static final long serialVersionUID = 1L;

    private int handle;
    private int code;
    private String sdMac;
    private String devMac;
    private String time;
    private Object data;

	public String getSdMac() {
        return sdMac;
    }

    public void setSdMac(String sdMac) {
        this.sdMac = sdMac;
    }

    public String getDevMac() {
        return devMac;
    }

    public void setDevMac(String devMac) {
        this.devMac = devMac;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
