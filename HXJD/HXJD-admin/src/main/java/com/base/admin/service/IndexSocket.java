package com.base.admin.service;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.jfinal.aop.Clear;
import com.jfinal.kit.JsonKit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: IndexSocket.java   
 * @包名: com.base.admin.service   
 * @描述: admin后台首页socket  
 * @所属: 华夏九鼎     
 * @日期: 2018年1月3日 下午5:05:37   
 * @版本: V1.0 
 * @创建人：kevin 
 * @修改人：kevin
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@ServerEndpoint("/IndexWebsocket.ws")
@Clear
public class IndexSocket {
    @OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {
    	
    	
    	if(Integer.parseInt(message)==1){
    		while(true){		
    				if(session.isOpen()){	
						Map<String, String> map = new HashMap<String, String>();
						long totalMemory = Runtime.getRuntime().totalMemory();		//虚拟机内存总量
						long freeMemory = Runtime.getRuntime().freeMemory();		//虚拟机空闲内存量
						//long maxMemory = Runtime.getRuntime().maxMemory();			//虚拟机使用最大内存量
						
						BigDecimal t = new BigDecimal(totalMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),3,RoundingMode.HALF_DOWN);
						BigDecimal f = new BigDecimal(freeMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),3,RoundingMode.HALF_DOWN);
						BigDecimal c = new BigDecimal(1).subtract(f.divide(t,3,RoundingMode.HALF_DOWN)).multiply(new BigDecimal(100));
						//BigDecimal m = new BigDecimal(maxMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),4,RoundingMode.HALF_DOWN);
						
						
						map.put("t", t.doubleValue()+"");
						map.put("f", f.doubleValue()+"");
						map.put("c", c.doubleValue()+"");
						
						session.getBasicRemote().sendText(JsonKit.toJson(map));
						Thread.sleep(1000);
											
					} else {
						session.close();
						break;
					}
    		}		
		} 

    }

    @OnOpen
    public void onOpen() {
        System.out.println("Client connected");
    }

    @OnClose
    public void onClose() {
        System.out.println("Connection closed");
    }
    
    public void startMessageBlock(){
    	System.out.println("startMessageBlock");
    }
}