package com.base.im.client;

import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.model.JDrillcode;
import com.base.service.DrillQuery;
import com.base.service.DrillcodeQuery;
import com.base.service.OptionQuery;
import com.base.utils.StringUtils;
import com.jfinal.plugin.IPlugin;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.tio.client.AioClient;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.ReconnConf;
import org.tio.client.intf.ClientAioHandler;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.Node;


public class IMClientStarter2 implements IPlugin {
	private static Node serverNode = null;
	private static AioClient<Object, IMPacket, Object> aioClient;
	private static ClientGroupContext<Object, IMPacket, Object> clientGroupContext = null;
	private static ClientAioHandler<Object, IMPacket, Object> aioClientHandler = null;
	private static ClientAioListener<Object, IMPacket, Object> aioListener = null;
	private static ReconnConf<Object, IMPacket, Object> reconnConf = new ReconnConf<Object, IMPacket, Object>(
			5000L);// 用来自动连接的，不想自动连接请传null

	public static void main(String[] args) throws Exception {
		String serverIp = "192.168.0.188"; 
		int serverPort = 8000;
				
		//String serverIp = "127.0.0.1";
		//int serverPort = 9091; 
				
		serverNode = new Node(serverIp, serverPort);
		aioClientHandler = new IMClientAioHandler();
		aioListener = new IMClientListener();

		clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);
		aioClient = new AioClient<>(clientGroupContext);

		ClientChannelContext<Object, IMPacket, Object> clientChannelContext = aioClient.connect(serverNode);
	}

	@Override
	public boolean start() {
		// TODO Auto-generated method stub
		System.out.println("====================C连接启动！！！！==========================");
//		String config = OptionQuery.me().findJOption4OptionKey("c_serverConfig").getOptionValue();
//		String[] configs = config.split("\\|");
		String serverIp;
		int serverPort;
		
		
		JDrillcode drill = DrillcodeQuery.me().getActiveDrill();//
		
		if(drill != null){//同时只能进行一个训练--》单条数据
			if(StringUtils.areNotEmpty(drill.getIp(),drill.getPort())){
				serverIp =  drill.getIp();
				serverPort = Integer.parseInt(drill.getPort());
			} else {
				return true;
			}			
		} else {
			return true;
		}
		
		//String serverIp = "192.168.0.188"; 
		//int serverPort = 8000;

				
		serverNode = new Node(serverIp, serverPort);
		aioClientHandler = new IMClientAioHandler();
		aioListener = new IMClientListener();

		clientGroupContext = new ClientGroupContext<>(aioClientHandler, aioListener, reconnConf);
		try {
			aioClient = new AioClient<>(clientGroupContext);
			ClientChannelContext<Object, IMPacket, Object> clientChannelContext = aioClient.connect(serverNode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
/*	public boolean end(){

	}*/
	

	@Override
	public boolean stop() { 
		// TODO Auto-generated method stub
		System.out.println("====================C连接关闭！！！！==========================");
		return aioClient.stop(); 
		
/*		try {
			aioClient.stop();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		//return false;
*/	}

}
