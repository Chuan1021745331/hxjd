package com.base.im.common;

import com.base.im.IMcacheMap;
import com.base.im.MsgProtocol;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.io.UnsupportedEncodingException;

/**
 * @类名: SenMsg
 * @包名: com.base.im.common
 * @描述: T-io给推流程序发送消息
 * @日期: 2018/1/11 14:21
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class SenMsg {
    /**
     * 关闭或者开启推送某个盾构机下的所有摄像头流
     * @param order：表示命令1:推送；0：停止推送
     * @param num：表示盾构机编号
     * @throws UnsupportedEncodingException
     */
    public static void sendMsg(int order,String num) throws UnsupportedEncodingException {
        String orderStr = null;
        if(order == 1){
            orderStr = MsgProtocol.START_PUSH+num;
        }else {
            orderStr = MsgProtocol.STOP_PUSH+num;
        }
        ChannelContext<Object, IMPacket, Object> channelContext = (ChannelContext<Object, IMPacket, Object>) IMcacheMap.cacheMap.get("127.0.0.1");
        IMPacket imPacket = new IMPacket();
        imPacket.setBody(orderStr.getBytes("utf-8"));
        Aio.send(channelContext,imPacket);
    }
}
