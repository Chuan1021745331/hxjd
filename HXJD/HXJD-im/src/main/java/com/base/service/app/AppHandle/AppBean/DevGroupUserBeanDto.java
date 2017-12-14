package com.base.service.app.AppHandle.AppBean;

import java.io.Serializable;

/**
 * Created by 104 on 2017/6/7.
 */
public class DevGroupUserBeanDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private  int userId;
    private  String userName;
    private  int camp;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getCamp() {
        return camp;
    }

    public void setCamp(int camp) {
        this.camp = camp;
    }



}
