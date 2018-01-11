package com.base.admin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.Session;
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
@ServerEndpoint("/IndexWebsocket.ws")//todo 配置websocket
public class CameraAliveHeartBeat {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static int onlineCount = 0;
    private static CopyOnWriteArraySet<CameraAliveHeartBeat> webSocketSet = new CopyOnWriteArraySet<>();

    /*
     * key:摄像头id
     *
     */
    private static Map<String, Long> camLinkMapping = new HashMap<>();
    private static Set<String> camIdSet = new HashSet<>();

    static {
        Timer timer = new Timer();
        timer.schedule(new HeatBeatCheck(), 1000, 10000);//
    }

    private static class HeatBeatCheck extends TimerTask {
        @Override
        public void run() {
            check();
        }
    }


    private static void check() {

        /*SavePlayInfoUtil savePlayInfoUtil = new SavePlayInfoUtil();
        Set<String> invalidCamIdSet = new HashSet<>();

        long serverCurrentTime = System.currentTimeMillis();
        for (String camId : camIdSet) {
            long heartBeatArrivalTime = camLinkMapping.get(camId);

            if((serverCurrentTime - heartBeatArrivalTime) / 1000 > 10)
            {
                //TODO 关闭流
                String videoId = "videoRtmp"+camId;
                manager.stop(videoId);
                savePlayInfoUtil.delect(videoId);
                System.out.println("关闭视频流ID号为："+camId);
                invalidCamIdSet.add(camId);
            }
        }*/

        /*for (String invalidCamId : invalidCamIdSet) {
            try{
                camLinkMapping.remove(invalidCamId);
                camIdSet.remove(invalidCamId);
            }catch (Exception e){
                System.out.println("-----------------------------");
            }
        }*/
    }

    public CameraAliveHeartBeat() {
        logger.info("客户端心跳监测初始化完毕");
    }

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
//        logger.info("控制中心【" + this.session.getId() + "】已关闭");
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        //#init#摄像头id
        if (message.startsWith("#init#")) {
            camLinkMapping.put(message.split("#")[2], System.currentTimeMillis());
            camIdSet.add(message.split("#")[2]);
        }
        //#heartbeat#摄像头id
        else if (message.startsWith("#heartbeat#")) {
            if(!camIdSet.contains(message.split("#")[2]))
            {
                sendMessage("#timeout#连接超时，视频流已关闭");
            }
            camLinkMapping.put(message.split("#")[2], System.currentTimeMillis());

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
