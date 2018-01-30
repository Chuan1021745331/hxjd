package com.base.admin.service;

import com.base.im.common.SenMsg;
import com.base.model.JTbm;
import com.base.service.TbmService;
import com.jfinal.aop.Clear;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Session;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


/**
 * Time: 12:00
 * Date: 2017/9/25
 * Corp: 华夏九鼎
 * Name: Nandem(nandem@126.com)
 * ----------------------------
 * Desc: 用于实时更新界面数据，前后端数据处理需要自己实现
 */
@ServerEndpoint("/HeartBeat.ws")
@Clear
public class CameraAliveHeartBeat {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static CopyOnWriteArraySet<CameraAliveHeartBeat> webSocketSet = new CopyOnWriteArraySet<>();
    private static Map<String,List<CameraAliveHeartBeat>> sessionMaps = new HashMap<>();


    private Session session;

    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        webSocketSet.add(this);
        send("您已成功连接服务器");
//        logger.info("客户端【" + session.getId() + "】已连接");
    }

    @OnClose
    public void onClose() throws IOException {
        webSocketSet.remove(this);
        if (webSocketSet.size() == 0){
            sessionMaps.clear();
            //0000:表示关闭全部推流
            List<JTbm> tbms = TbmService.me().findAllTbm();
            for(JTbm tbm:tbms){
                SenMsg.sendMsg(0,"0000",tbm.getMac());
            }
            return;
        }
        removeSessionByBrowserExceptionClose();
    }

    /**
     * 浏览器异常关闭，从map中清除该链接
     */
    public void removeSessionByBrowserExceptionClose(){
        for (Map.Entry<String,List<CameraAliveHeartBeat>> entry:sessionMaps.entrySet()){
            String machineNum = entry.getKey();
            List<CameraAliveHeartBeat> cameraAliveHeartBeatList = entry.getValue();
            boolean ishas = false;
            for (CameraAliveHeartBeat cameraAliveHeartBeat:cameraAliveHeartBeatList){
                if(this == cameraAliveHeartBeat){
                    ishas = true;
                    break;
                }
            }
            cameraAliveHeartBeatList.remove(this);
        }
    }
    /**
     * message的消息格式：#hxjd#oldMachineName#newMachineName
     * oldMachineName:表示要关闭的盾构机摄像头组(盾构机名；与推流配置文件中的一致)
     * newMachineName：表示将要观看的盾构机摄像头组(盾构机名；与推流配置文件中的一致)
     * @param message
     * @param session
     * @throws IOException
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if (message.startsWith("#hxjd#")){
            String[] megs = message.split("#");
            List<CameraAliveHeartBeat> oldSessionList = sessionMaps.get(megs[2]);
            if(oldSessionList != null){
                /*从megs[1]的观众中移除此人*/
                oldSessionList.remove(this);

                if(oldSessionList.size() == 0){
                /*没有人观看此盾构机的摄像头*/
                    sessionMaps.remove(megs[2]);
                    //此处要从数据库中通过megs[2]查出该盾构机摄像头推流程序所属电脑的ip
                    int tid = Integer.parseInt(megs[2]);
                    JTbm tbm = TbmService.me().findTbmById(tid);
                    if(tbm != null){
                        SenMsg.sendMsg(0,megs[2],tbm.getMac());
                    }
                }
            }

            List<CameraAliveHeartBeat> yangSessionList = sessionMaps.get(megs[3]);
            /*在megs[2]的观众中加入此人*/
            if (yangSessionList == null){
                yangSessionList = new ArrayList<>();
                yangSessionList.add(this);
                sessionMaps.put(megs[3],yangSessionList);
                //此处要从数据库中通过megs[3]查出该盾构机摄像头推流程序所属电脑的ip
                int tid = Integer.parseInt(megs[3]);
                JTbm tbm = TbmService.me().findTbmById(tid);
                if(tbm != null){
                    SenMsg.sendMsg(1,megs[3],tbm.getMac());
                }
            } else {
                yangSessionList.add(this);
            }
        }

    }

    private void send(String data) {
        /*try {
            this.session.getBasicRemote().sendText(data);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    public static void sendMessage(String message) {
        for (CameraAliveHeartBeat dw : webSocketSet) {
            dw.send(message);
        }
    }
}
