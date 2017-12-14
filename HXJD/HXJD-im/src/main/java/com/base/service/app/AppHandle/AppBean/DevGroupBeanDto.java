package com.base.service.app.AppHandle.AppBean;

import java.io.Serializable;

/**
 * Created by 104 on 2017/6/6.
 */
public class DevGroupBeanDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private  int groupId;
    private String groupName;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }






}
