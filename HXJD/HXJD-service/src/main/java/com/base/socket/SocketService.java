package com.base.socket;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

import com.base.model.JOption;
import com.base.query.OptionQuery;
import com.jfinal.log.Log;

public class SocketService {
	
	private static Log log=Log.getLog(SocketService.class);
	
	public static String getServerData(int toatlNum){
		DatagramSocket client = null;
		JOption socket = OptionQuery.me().findJOption4OptionKey("socket");
		
		String[] s = socket.getOptionValue().split("\\|");
		
		String ip = s[0].toString();
		int port_ = Integer.parseInt(s[1].toString());
		int timeOut = Integer.parseInt(s[2].toString());
		String encoder = s[3].toString();
		try {
			client = new DatagramSocket();
			client.setSoTimeout(timeOut);
			
			String sendSrt = "dddd";
			byte[] sendBuf  = sendSrt.getBytes(encoder);
			InetAddress addr =  InetAddress.getByName(ip);
			int port = port_;
			DatagramPacket sendPacket = new DatagramPacket(sendBuf,sendBuf.length,addr,port);
			client.send(sendPacket);
			
			byte[] recvBuf = new byte[4096];
			
			DatagramPacket recvPacket = new DatagramPacket(recvBuf,recvBuf.length);
			client.receive(recvPacket);
			byte[] d = recvPacket.getData();
			String recvStr = new String( d,encoder).trim();
//			log.info("第["+toatlNum+"]次请求，接收到:"+recvStr);
			client.close();
			return recvStr;
		} catch (SocketTimeoutException e) {
//			log.error("第["+toatlNum+"]次请求，连接超时");
		} catch(Exception e){
//			log.error("第["+toatlNum+"]次请求，系统异常");
		}
		finally {
			client.close();
		}
		return null;
	}
}
