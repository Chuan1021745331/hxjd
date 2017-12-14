package com.base.im.client;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.base.model.dto.RequestDto;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.utils.GZipUtil;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;

import com.base.im.common.Const;
import com.base.im.common.IMPacket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class IMClientStarter {
	private static Node serverNode = null;
	private static AioClient<Object, IMPacket, Object> aioClient;
	private static ClientGroupContext<Object, IMPacket, Object> clientGroupContext = null;
	private static ClientAioHandler<Object, IMPacket, Object> aioClientHandler = null;
	private static ClientAioListener<Object, IMPacket, Object> aioListener = null;
	private static ReconnConf<Object, IMPacket, Object> reconnConf = new ReconnConf<Object, IMPacket, Object>(
			5000L);// 用来自动连接的，不想自动连接请传null

	public static void main(String[] args) throws Exception {
		
		
		String serverIp = "127.0.0.1";
		int serverPort = Const.PORT;
		serverNode = new Node(serverIp, serverPort);
		aioClientHandler = new IMClientAioHandler();
		aioListener = null;

		clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);
		aioClient = new AioClient<>(clientGroupContext);

		ClientChannelContext<Object, IMPacket, Object> clientChannelContext = aioClient.connect(serverNode);

		// 以下内容不是启动的过程，而是属于发消息的过程
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		while (true) {
			String inString = in.readLine();
				if(!StringUtils.isEmpty(inString)){
				RequestDto r=new RequestDto();
				r.setCode(Integer.parseInt(inString));
				r.setDevMac("3da95062f87ef5d2b6d744a7f9f1a016");
				r.setSdMac("672a63302a453c13665b121fa1a26d61");	
				r.setHandle(1);
				//r.setCode(10);
				  
				MessageBean mesg=new MessageBean();
		        mesg.setMsgType("0");
		        mesg.setSendTime("20170101");
		        mesg.setSender("52");
		        mesg.setSendName("叶凡");
		        mesg.setGroupId(5);
		        mesg.setMsg("dsfsfdsf");
		        r.setData(mesg);
		
				IMPacket packet = new IMPacket();
				packet.setBody(GZipUtil.doZip(JSON.toJSONString(r)));
		//		packet.setBody("hello world".getBytes(IMPacket.CHARSET));/**/
				Aio.send(clientChannelContext, packet);
			}
		}
	}
}
