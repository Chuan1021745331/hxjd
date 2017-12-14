package com.base.im.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.tio.client.intf.ClientAioListener;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.model.JMsg;
import com.base.service.GroupQuery;
import com.base.service.app.MsgQuery;
import com.jfinal.core.JFinal;

public class IMClientListener implements ClientAioListener<Object, IMPacket, Object> {

	@Override
	public void onAfterConnected(ChannelContext<Object, IMPacket, Object> channelContext, boolean isConnected, boolean isReconnect) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("==C==客户端onAfterConnected"+ " "+isConnected+ " "+isReconnect);
		
		IMcacheMap.cacheMap.remove("IM-C");

		IMcacheMap.cacheMap.put("IM-C", channelContext);
		System.out.println(channelContext!=null);  //TRUE
		System.out.println();
		System.out.println(isConnected);   	//FALSE
		System.out.println(!channelContext.isClosed());  //false
		System.out.println(!channelContext.isRemoved()); //TRUE
		System.out.println(channelContext!=null && (isConnected && !channelContext.isClosed()));
		
		if(channelContext!=null && (isConnected && !channelContext.isClosed())){//连接上就发送
			System.out.println("===getIn===");
			List<JMsg> textMsg =  MsgQuery.me().findByMsgType("C-0");
			List<JMsg> fileMsg = MsgQuery.me().findByMsgType("C-1");
				
			if(textMsg!=null && textMsg.size() > 0 ){
				for (JMsg jMsg : textMsg) {
					System.out.println("===向C平台发送离线文字消息===");
					IMPacket packet1 = new IMPacket();
					//packet1.setBody("$0".getBytes("utf-8"));
					packet1.setBody("$0".getBytes("GBK"));
					Aio.send(channelContext, packet1);//"$0"-->文本信息
					
					IMPacket packet2 = new IMPacket();
					String camp =  GroupQuery.me().GainCampByMediatorId(jMsg.getSenderId()).getCamp().toString();
					
        			packet2.setBody(camp.getBytes("GBK"));
        			Aio.send(channelContext, packet2);//阵营
        			
        			IMPacket packet4 = new IMPacket();
        			packet4.setBody(jMsg.getSenderId().toString().getBytes("GBK"));
        			Aio.send(channelContext, packet4);//发送人ID
        			
        			IMPacket packet5 = new IMPacket();
        			packet5.setBody(jMsg.getSendName().getBytes("GBK"));
        			Aio.send(channelContext, packet5);//发送人姓名
        			
        			IMPacket packet6 = new IMPacket();
        			packet6.setBody(jMsg.getSendTime().getBytes("GBK"));
        			Aio.send(channelContext, packet6);//发送时间
		    			
					IMPacket packet3 = new IMPacket();
					//packet3.setBody(object.getString("msg").getBytes("utf-8"));
					packet3.setBody(jMsg.getMessage().getBytes("GBK"));
					Aio.send(channelContext, packet3);//发送消息体
					System.out.println("===C平台离线文字消息发送完===");
					jMsg.delete();
				}
			}

			if(fileMsg!=null && fileMsg.size() > 0 ){
				for (JMsg jMsg : fileMsg) {
					System.out.println("===向C平台发送文件消息===");
					File file = new File(JFinal.me().getServletContext().getRealPath("") + jMsg.getMessage());
		            System.out.println(file.getAbsolutePath());
					String fileName = file.getName();
					System.out.println("name: " + fileName);
					byte[] nameBytes = fileName.getBytes("utf-8");
					IMPacket packet0 = new IMPacket();
					//packet0.setBody(ArrayUtils.addAll(front, "$1".getBytes("utf-8")));
					packet0.setBody("$1".getBytes("utf-8"));
					//=========================
					Aio.send(channelContext, packet0);  //"$1"
					IMPacket packet1 = new IMPacket();
					//packet1.setBody(ArrayUtils.addAll(front,nameBytes));
					packet1.setBody(nameBytes);
					System.out.println("文件名长度 " + fileName.length());;
					System.out.println("文件名： " + fileName);
					//=========================
					Aio.send(channelContext, packet1);  //"文件名"
					long fileLength = file.length();
					System.out.println("文件长度: " + fileLength);
					IMPacket packet2 = new IMPacket();
					//packet2.setBody(ArrayUtils.addAll(front,String.valueOf(fileLength).getBytes("utf-8")));
					packet2.setBody(String.valueOf(fileLength).getBytes("utf-8"));
					//=========================
					Aio.send(channelContext, packet2);  //"文件长度(大小)"
					IMPacket packet3 = new IMPacket();
					String camp =  GroupQuery.me().GainCampByMediatorId(jMsg.getSenderId()).getCamp().toString();
					packet3.setBody(camp.getBytes("GBK"));
					Aio.send(channelContext, packet3);//阵营
					//
					
					IMPacket packet4 = new IMPacket();
					packet4.setBody(jMsg.getSenderId().toString().getBytes("GBK"));
					Aio.send(channelContext, packet4);//发送者ID
					IMPacket packet5 = new IMPacket();
					packet5.setBody(jMsg.getSendName().getBytes("GBK"));
					Aio.send(channelContext, packet5);//发送者ID
					IMPacket packet6 = new IMPacket();
        			packet6.setBody(jMsg.getSendTime().getBytes("GBK"));
        			Aio.send(channelContext, packet6);//发送时间
					
					InputStream inputStream = new FileInputStream(file);
					byte[] bytes = IOUtils.toByteArray(inputStream);
					IMPacket cPacket = new IMPacket();
					//cPacket.setBody(ArrayUtils.addAll(front,bytes));
					cPacket.setBody(bytes);
					Aio.send(channelContext, cPacket);
					System.out.println("===C平台离线文件消息发送完===");
					jMsg.delete();
				}
			}
		}
	
	}

	@Override
	public void onAfterSent(ChannelContext<Object, IMPacket, Object> channelContext, IMPacket packet, boolean isSentSuccess) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println("===C===客户端onAfterSent===");
	}

	@Override
	public void onAfterReceived(ChannelContext<Object, IMPacket, Object> channelContext, IMPacket packet, int packetSize)
			throws Exception {
		// TODO Auto-generated method stub
		System.out.println("===C===客户端onAfterReceived===");
	}

	@Override
	public void onAfterClose(ChannelContext<Object, IMPacket, Object> channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
		// TODO Auto-generated method stub
		IMcacheMap.cacheMap.remove("IM-C");
		System.out.println("===C===服务端断开===");
	}

}
