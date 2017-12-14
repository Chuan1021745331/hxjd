package com.base.service.app.AppHandle.AppBean;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: MessageBean
 * @包名: com.base.service.app.AppHandle.AppBean
 * @描述: 信息类
 * @所属: 华夏九鼎
 * @日期: 2017/6/6 9:10
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class MessageBean {
    private String sender;
    private int groupId;
    private String sendTime;
    private String msgType;
    private String msg;
    private String sendName;
    
//    private int offend=1;//离线消息标志，0表示离线消息已经发送完，1表示还有离线消息。

    
    

    public String getSendName() {
        return sendName;
    }

	public void setSendName(String sendName) {
        this.sendName = sendName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
