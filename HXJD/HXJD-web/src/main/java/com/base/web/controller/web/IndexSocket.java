package com.base.web.controller.web;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jfinal.aop.Clear;
import com.jfinal.kit.JsonKit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
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
@ServerEndpoint("/IndexWebsocketTest.ws")
@Clear
public class IndexSocket {
	private final static Logger logger= LoggerFactory.getLogger(IndexSocket.class);
	private static DecimalFormat df = new DecimalFormat("#.00"); 
	@OnMessage
    public void onMessage(String message, Session session) throws IOException, InterruptedException {	
    	if(Integer.parseInt(message)==1){		
    				if(session.isOpen()){	
    					Map<String, Object> map = new HashMap<String, Object>();
						map.put("a1", df.format(Math.random()*100));
						map.put("a2", df.format(Math.random()*100));
						map.put("b1", df.format(Math.random()*100));
						map.put("b2", df.format(Math.random()*100));
						map.put("c1", df.format(Math.random()*100));
						map.put("c2", df.format(Math.random()*100));
						map.put("d1", df.format(Math.random()*100));
						map.put("d2", df.format(Math.random()*100));
						
						map.put("outRing1", df.format(Math.random()*100));
						map.put("outRing2", df.format(Math.random()*100));
						map.put("outRing3", df.format(Math.random()*100));
						map.put("outRing4", df.format(Math.random()*100));
						map.put("outRing5", df.format(Math.random()*100));
						
						map.put("ring1", df.format(Math.random()*100));
						map.put("ring2", df.format(Math.random()*100));
						map.put("ring3", df.format(Math.random()*100));
						map.put("ring4", df.format(Math.random()*100));
						map.put("ring5", df.format(Math.random()*100));
						map.put("ring6", df.format(Math.random()*100));
						map.put("ring7", df.format(Math.random()*100));
						map.put("ring8", df.format(Math.random()*100));
												
						session.getBasicRemote().sendText(JsonKit.toJson(map));	
						//System.out.println("Socket-----send");
					} else {
						session.close();
					}	
		} 

    }

    @OnOpen
    public void onOpen() {
    	logger.info("Client connected");
    }

    @OnClose
    public void onClose() {
    	logger.info("Connection closed");
    }
    
    public void startMessageBlock(){
    	logger.info("startMessageBlock");
    }
}