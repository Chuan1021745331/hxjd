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
import com.base.service.UserService;
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
		if (body != null)
		{
			logger.info("==normal--encode==");
			logger.info(""+bodyLen);
			buffer.put(body);				
		}
		//logger.info(buffer.toString());
		//logger.info("##encode##");
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
			//logger.info("-------------------");
		}else{
			//byte[] bytes = new byte[buffer.capacity()];
			//logger.info(buffer.toString());
		}
		logger.info("##decode##");
		
		buffer.position(0);

		byte[] head = new byte[4];
		buffer.get(head);
		boolean a = false;

		buffer.position(0);
		
		int readableLength = buffer.limit() - buffer.position();
		if (readableLength < IMPacket.HEADER_LENGHT)
		{
			return null;
		}

		int bodyLength,neededLength;

			bodyLength = buffer.limit();
			neededLength =  bodyLength;
			
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
		

		
	}
}
