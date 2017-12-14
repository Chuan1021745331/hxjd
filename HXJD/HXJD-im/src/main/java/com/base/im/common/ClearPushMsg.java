package com.base.im.common;

import com.base.model.JMsg;
import com.base.model.JOffuser;
import com.base.service.OffUserQuery;
import com.base.service.app.MsgQuery;
import com.base.service.app.AppInterface.OffMsgType;
import com.sun.org.apache.xml.internal.serialize.LineSeparator;

import javax.management.JMException;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.base.im.common
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/9/20 9:16
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class ClearPushMsg {

    private static String[] getCanDelMsg(){
        List<JMsg> msgs = MsgQuery.me().selectAll();
        List<JOffuser> offusers = OffUserQuery.me().selectAll();
        List<JMsg> msgsList = new ArrayList<>();
        for(JMsg msg:msgs){
            boolean isas = false;
            for(JOffuser jOffuser : offusers){
                String[] msgsId = jOffuser.getMsgId().split("\\|");
                for(String msgid : msgsId){
                    if(msg.getId() == Integer.parseInt(msgid)){
                        isas = true;
                    }
                }
            }
            if(!isas&&(!(msg.getMsgType().equalsIgnoreCase("C-0") || msg.getMsgType().equalsIgnoreCase("C-1")))){
                msgsList.add(msg);
            }
        }
        if(msgsList.size() == 0){
            return null;
        }else {
            String[] msgIds = new String[msgsList.size()];
            for (int i = 0;i< msgsList.size();i++){
                msgIds[i] = msgsList.get(i).getId()+"";
            }
            return msgIds;
        }
    }
    public static void delPushMsg(){
        String[] msgs =getCanDelMsg();
        if(msgs == null){
            return;
        }
        MsgQuery.me().delPushMsgByIds(msgs);
    }
}
