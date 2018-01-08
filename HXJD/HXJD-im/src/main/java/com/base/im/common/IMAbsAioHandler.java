package com.base.im.common;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.ClientChannelContext;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.exception.AioDecodeException;
import org.tio.core.intf.AioHandler;

import com.base.im.IMcacheMap;
import com.base.utils.GZipUtil;

public abstract class IMAbsAioHandler implements AioHandler<Object, IMPacket, Object>
{
	private final static Logger logger= LoggerFactory.getLogger(IMAbsAioHandler.class);

	/**
	 * 编码：把业务消息包编码为可以发送的ByteBuffer
	 */
	@Override
	public ByteBuffer encode(IMPacket packet, GroupContext<Object, IMPacket, Object> groupContext, ChannelContext<Object, IMPacket, Object> channelContext)
	{
		byte[] body = packet.getBody();
		int bodyLen = 0;
		if (body != null)
		{
			bodyLen = body.length;
		}

		int allLen = IMPacket.HEADER_LENGHT + bodyLen;
		ByteBuffer buffer = ByteBuffer.allocate(allLen);
		buffer.order(groupContext.getByteOrder());
		
		//buffer.clear();
		//
		ClientChannelContext<Object, IMPacket, Object> clientChannelContext = (ClientChannelContext<Object, IMPacket, Object>) IMcacheMap.cacheMap.get("IM-C");
		
		
		
		if (body != null)
		{
			//byte[] headByte = ArrayUtils.subarray(body, 0, 2);
			try {
				//String headStr = new String(headByte, "utf-8");
				//if(headStr.equals("$C")){
				if(channelContext.equals(clientChannelContext==null?"":clientChannelContext)){//与C端通信	
					logger.info("==C--encode==");
					//byte[] temp = ArrayUtils.subarray(body, 2, body.length);
					
					byte[] bytes2 = new byte[4];				
					byte[] bytes3;
					bytes3 = String.valueOf(body.length).getBytes("utf-8");
					int j = 0;
					for(int i= 0; i<4; i++){	
						if(i < 4-bytes3.length){
							bytes2[i] = '0';
						} else{
							bytes2[i] = bytes3[j];
							j++;
						}
					}
					//buffer.clear();
					//buffer = ByteBuffer.allocate(allLen-2);
					//buffer.clear();
					buffer.put(bytes2);
					buffer.put(body);
				} else {//与Android、本机IM通信
					logger.info("==normal--encode==");
					buffer.putInt(bodyLen);
					buffer.put(body);				
				}
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		logger.info(buffer.toString());
		logger.info("##encode##");
		return buffer;
	}

	/**
	 * 解码：把接收到的ByteBuffer，解码成应用可以识别的业务消息包
	 */
	@Override
	public IMPacket decode(ByteBuffer buffer, ChannelContext<Object, IMPacket, Object> channelContext) throws AioDecodeException
	{
		//buffer.getI
		if(buffer==null){
			logger.info("-------------------");
		}else{
			//byte[] bytes = new byte[buffer.capacity()];
			logger.info(buffer.toString());
		}
		logger.info("##decode##");
		
		buffer.position(0);
		//byte[] all = new byte[buffer.limit()];
		//buffer.get(all);
		//buffer.position(0);
		byte[] head = new byte[4];
		buffer.get(head);
		boolean a = false;
/*		for (byte b : head) {
			if(b <= 0){
				a = true;
				break;
			}
		}*/
		buffer.position(0);
		
		int readableLength = buffer.limit() - buffer.position();
		if (readableLength < IMPacket.HEADER_LENGHT)
		{
			return null;
		}

		int bodyLength,neededLength;
/*		if(a){////原来的		
			bodyLength = buffer.getInt();
			neededLength =  IMPacket.HEADER_LENGHT +bodyLength;
		} else {*/////C通讯
			bodyLength = buffer.limit();
			neededLength =  bodyLength;
		//}
			
		if (bodyLength < 0)
		{
			throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
		}
									
		int test = readableLength - neededLength;
		if (test < 0) // 不够消息体长度(剩下的buffe组不了消息体)
		{
			logger.info("不够消息体长度");
			return null;
		} else
		{
			IMPacket imPacket = new IMPacket();
			if (bodyLength > 0)
			{
				byte[] dst = new byte[bodyLength];
				buffer.get(dst);
				imPacket.setBody(dst);
			}
			return imPacket;
		}
		
		////C通讯
		/*int bodyLength = buffer.limit();

		if (bodyLength < 0)
		{
			throw new AioDecodeException("bodyLength [" + bodyLength + "] is not right, remote:" + channelContext.getClientNode());
		}

		int neededLength =  bodyLength;
		int test = readableLength - neededLength;
		if (test < 0) // 不够消息体长度(剩下的buffe组不了消息体)
		{
			return null;
		} else
		{
			IMPacket imPacket = new IMPacket();
			if (bodyLength > 0)
			{
				byte[] dst = new byte[bodyLength];
				buffer.get(dst);
				imPacket.setBody(dst);
			}
			return imPacket;
		}*/
		
	}
}
