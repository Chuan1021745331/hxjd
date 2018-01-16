package com.base.im.server;

import com.alibaba.fastjson.JSON;
import com.base.im.IMcacheMap;
import com.base.im.MsgProtocol;
import com.base.im.common.IMAbsAioHandler;
import com.base.im.common.IMPacket;
import com.base.model.dto.*;
import com.base.service.app.AppInterface.AppService;
import com.base.utils.GZipUtil;
import com.base.utils.StringGZIP;


import org.apache.commons.lang.ArrayUtils;
import org.tio.core.Aio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.base.im.MsgProtocol.isInitMsg;

/**
 * @author tanyaowu
 * @创建时间 2016年11月18日 上午9:13:15
 * @操作列表 编号 | 操作时间 | 操作人员 | 操作说明 (1) | 2016年11月18日 | tanyaowu | 新建类
 */
public class IMServerAioHandler extends IMAbsAioHandler implements ServerAioHandler<Object, IMPacket, Object> {

	// private static List<Map<String, String>> unTerminalList = new
	// ArrayList<Map<String, String>>();
	private final static Logger logger= LoggerFactory.getLogger(IMServerAioHandler.class);

	
	/**
	 * 处理消息
	 */
	@Override
	public Object handler(IMPacket packet, ChannelContext<Object, IMPacket, Object> channelContext) throws Exception {
		byte[] body = packet.getBody();

		if (body != null) {

			body = ArrayUtils.subarray(body, 4, body.length);
			if (body.length <= 4) {
				logger.info("===============心跳包===============");
				return null;
			}
			String msg = new String(body,"utf-8");
			boolean initMsgBool = MsgProtocol.isInitMsg(msg);
			if(initMsgBool){
				//注册信息
				//todo 解析出mac地址当做key
				IMcacheMap.cacheMap.put(channelContext.getClientNode().getIp(),channelContext);
				IMPacket imPacket = new IMPacket();
				imPacket.setBody(MsgProtocol.INIT_MSG.getBytes("utf-8"));
				Aio.send(channelContext,imPacket);
			}

//			String msg = new String(body,"utf-8");
//			String str = StringGZIP.unCompress(body);
//			logger.info("收到消息：" + GZipUtil.unZipByte(body));
//			RequestDto requestDto = JSON.parseObject(str, RequestDto.class);
		}
		return null;
	}


	/**
	 * 初始化
	 *
	 * @param channelContext
	 * @param requestDto
	 */
	private int initializeUser(ChannelContext<Object, IMPacket, Object> channelContext, RequestDto requestDto) {
		int mID = 0;
		IMPacket resppacket = new IMPacket();
		Integer isInitialize = AppService.initializeTerminal(requestDto.getSdMac());// 55
		ResponseDto responseDto = new ResponseDto();
		responseDto.setCode(1);
		responseDto.setHandle(2);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		responseDto.setTime(df.format(new Date()));
		String respo = JSON.toJSONString(responseDto);
		// if(respo.length()>0){
		byte[] doZipMsg = new byte[0];
		try {
			doZipMsg = GZipUtil.doZip(respo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resppacket.setBody(doZipMsg);
		// }else {
		// resppacket.setBody(respo.getBytes(IMPacket.CHARSET));
		// }
		Aio.send(channelContext, resppacket);
		logger.info("ini-resppacket: " + respo);
		return mID;
	}



}
