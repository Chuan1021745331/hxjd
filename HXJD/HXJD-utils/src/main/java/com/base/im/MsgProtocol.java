package com.base.im;

/**
 * @类名: MsgProtocol
 * @包名: com.hxjd.IMProtocol
 * @描述: 服务端客户端的协议
 * @日期: 2018/1/11 11:52
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class MsgProtocol {
    public static String INIT_MSG = "M:666666:D";
    /*开始推流*/
    public static String START_PUSH = "HXJD:M000:D000:9999:";
    /*停止推流*/
    public static String STOP_PUSH = "HXJD:M000:D000:0000:";

    /**
     * 注册到服务器的返回信息判断
     * @param msg
     * @return
     */
    public static boolean isInitMsg(String msg){
        if(INIT_MSG.equals(msg)){
            return true;
        }
        return false;
    }

    /**
     * 推流
     * @param order
     * @return
     */
    public static boolean isStart(String order){
        if (START_PUSH.equals(order)) {
            return true;
        }
        return false;
    }

    /**
     * 停止推流
     * @param order
     * @return
     */
    public static boolean isStope(String order){
        if(STOP_PUSH.equals(order)){
            return true;
        }
        return false;
    }
}
