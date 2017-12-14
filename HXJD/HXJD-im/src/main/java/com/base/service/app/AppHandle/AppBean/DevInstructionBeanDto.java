package com.base.service.app.AppHandle.AppBean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 104 on 2017/6/8.
 */
public class DevInstructionBeanDto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int senderId;
    private String senderName;
    private String instructionType;
    private int camp;
    private String sendTime;
    private String msg;
    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getInstructionType() {
        return instructionType;
    }

    public void setInstructionType(String instructionType) {
        this.instructionType = instructionType;
    }

    public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public List<JMediator> getMediators() {
//        return mediators;
//    }
//
//    public void setMediators(List<JMediator> mediators) {
//        this.mediators = mediators;
//    }




}

