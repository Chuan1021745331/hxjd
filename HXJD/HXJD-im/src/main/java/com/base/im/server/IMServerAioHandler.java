package com.base.im.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.common.IMAbsAioHandler;
import com.base.im.common.IMPacket;
import com.base.model.dto.*;
import com.base.service.app.AppHandle.AppBean.DevInformationBean;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.service.app.AppHandle.AppBean.ScoreBean;
import com.base.service.app.AppInterface.AppService;
import com.base.service.app.AppInterface.OffMsgType;
import com.base.service.app.MsgQuery;
import com.base.utils.GZipUtil;
import com.base.utils.StringGZIP;
import com.base.utils.StringUtils;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import org.apache.commons.lang.ArrayUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.client.ClientChannelContext;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.server.intf.ServerAioHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			// String b_ = GZipUtil.unZipByte(body);
			body = ArrayUtils.subarray(body, 4, body.length);
			if (body.length <= 4) {
				logger.info("===============心跳包===============");
				return null;
			}

			String str = StringGZIP.unCompress(body);
			logger.info("收到消息：" + GZipUtil.unZipByte(body));
			RequestDto requestDto = JSON.parseObject(str, RequestDto.class);

			ChannelContext context = isNullOrYes(channelContext, requestDto);// 否初始化

			// 判断是否初始化
			if (context != null && requestDto.getCode() != 1) {// 为true只是测试用，原本为context!=null
				// 已经初始化成功，可以做消息处理

				// =============
				/*
				 * org.json.JSONObject jsonObject = new
				 * org.json.JSONObject(GZipUtil.unZipByte(body));
				 * if(jsonObject.has("data")){ String msg = (String)
				 * jsonObject.getJSONObject("data").get("msg");
				 * 
				 * ClientChannelContext<Object, IMPacket, Object>
				 * clientChannelContext = (ClientChannelContext<Object,
				 * IMPacket, Object>) IMcacheMap.cacheMap.get("IM-C"); IMPacket
				 * packet1 = new IMPacket();
				 * packet1.setBody("$0".getBytes("utf-8"));
				 * Aio.send(clientChannelContext, packet1); IMPacket packet_ =
				 * new IMPacket(); packet.setBody(msg.getBytes("utf-8"));
				 * Aio.send(clientChannelContext, packet_); }
				 */
				// =========================
				logger.info("requsetCode: " + requestDto.getCode());
				//
				String respo = AppService.handleCore(requestDto);// --------------
				IMPacket imPacket = new IMPacket();
				// 如果请求处理结果失败
				if (respo == null) {

					ResponseDto responseDto = new ResponseDto();
					responseDto.setResponseStatus(0);
					requestDto.setHandle(2);
					requestDto.setCode(requestDto.getCode());
					respo = JSON.toJSONString(responseDto);
				}
				// ======
				ResponseDto responseDto_ = JSON.parseObject(respo, ResponseDto.class);
				if (responseDto_.getCode() == 6) {
					List<JSONObject> devPelBeanDtos = (List<JSONObject>) responseDto_.getData();
					if (devPelBeanDtos.size() <= 100) {// 不足100直接发送
						byte[] doZipMsg = GZipUtil.doZip(respo);
						imPacket.setBody(doZipMsg);
						// 发送响应数据
						Aio.send(channelContext, imPacket);
					} else {

						int count = devPelBeanDtos.size();
						int index = 0;
						for (int i = 0; i < devPelBeanDtos.size(); i++) {
							if (count > 100) {
								List<DevPelBeanDto> tempList = new ArrayList<DevPelBeanDto>();
								for (int j = count; j > count - 100; j--) {

									String str_ = devPelBeanDtos.get(j - 1).toJSONString();
									DevPelBeanDto devPelBeanDto1 = JSON.parseObject(str_, DevPelBeanDto.class);
									tempList.add(devPelBeanDto1);
								}

								// System.arraycopy(devPelBeanDtos, index,
								// tempList, 0, 100);
								ResponseDto responseDto = new ResponseDto();
								responseDto.setData(tempList);
								responseDto.setHandle(2);
								responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
								responseDto.setResponseStatus(1);
								responseDto.setTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
								String respon = JSON.toJSONString(responseDto);

								byte[] doZipMsg = GZipUtil.doZip(respon);
								imPacket.setBody(doZipMsg);
								// 发送响应数据
								Aio.send(channelContext, imPacket);
							} else {// 剩下不满100
								List<DevPelBeanDto> tempList = new ArrayList<DevPelBeanDto>();
								for (int j = index; j < devPelBeanDtos.size(); j++) {
									String str_ = devPelBeanDtos.get(j).toJSONString();
									DevPelBeanDto devPelBeanDto1 = JSON.parseObject(str_, DevPelBeanDto.class);
									tempList.add(devPelBeanDto1);
								}

								// System.arraycopy(devPelBeanDtos, index,
								// tempList, 0, count);
								ResponseDto responseDto = new ResponseDto();
								responseDto.setData(tempList);
								responseDto.setHandle(2);
								responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
								responseDto.setResponseStatus(1);
								responseDto.setTime(DateTime.now().toString("yyyy-MM-dd HH:mm:ss"));
								String respon = JSON.toJSONString(responseDto);
								byte[] doZipMsg = GZipUtil.doZip(respon);
								imPacket.setBody(doZipMsg);
								// 发送响应数据
								Aio.send(channelContext, imPacket);
								break;
							}
							count = count - 100;// 完成一次
							index += 100;
						}

					}

				} else {
					byte[] doZipMsg = GZipUtil.doZip(respo);
					imPacket.setBody(doZipMsg);
					// 发送响应数据
					Aio.send(channelContext, imPacket);
				}

				// ======

				// 压缩传输数据
				// if(respo.length()>200){

				// if(requestDto.getCode()==
				// Integer.parseInt(RequestCodeConstants.OWN_GROUP_CODE)){
				// }
			} else {
				// 还没有初始化,开始初始化
				int mediatorId = initializeUser(channelContext, requestDto);
				// 初始化完成，开始推送离线消息,为0则初始化不成功,
				// 此处为测试用
				if (mediatorId != 0) {
					pushMsg(channelContext, mediatorId);
				}
			}
		}

		return null;
	}

	/**
	 * 判断是否初始化
	 *
	 * @param channelContext
	 * @param requestDto
	 * @return
	 */
	private ChannelContext isNullOrYes(ChannelContext<Object, IMPacket, Object> channelContext, RequestDto requestDto) {
		ChannelContext context = null;
		// 判断消息是否合法
		if (requestDto.getHandle() == 1) {
			if (StringUtils.isNotBlank(requestDto.getSdMac())) {
				// 通过sd码从缓存中获取ChannelContext

				context = (ChannelContext) IMcacheMap.cacheMap.get(requestDto.getSdMac());
				// context如果是null的表明没有初始化，如果不是null则要比较当前这个ChannelContext与context是否相同
				if (context != null){
					if (channelContext != context) {
						// 如果不相同，则删除原先的，在缓存进当前的
						IMcacheMap.cacheMap.remove(requestDto.getSdMac());
						IMcacheMap.cacheMap.put(requestDto.getSdMac(), channelContext);
						context = channelContext;
					}
				//
				}
			} else {
				// sd值传输异常
			}
		}
		return context;
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

		if (isInitialize != 0) {

			// 绑定长连接
			Aio.bindUser(channelContext, isInitialize.toString());


			if (null != requestDto.getData()) {
				Map<String, Object> data = JSON.parseObject(JSON.toJSONString(requestDto.getData()));

			}

			// 绑定群组(GroupID) //调理员Id

			// 将数据装到缓存cacheMap
			IMcacheMap.cacheMap.put(requestDto.getSdMac(), channelContext);

			Record record = Db.findFirst(
					"SELECT jt.sdnum,jt.terminal_name,jt.terminal_power,jt.terminal_stauts,jm.mediator_name,jm.mediator_grade,jm.camp,jm.id FROM j_terminal jt JOIN j_mediatorterminal jmt on jt.id=jmt.terminal_id JOIN j_mediator jm on jmt.mediator_id=jm.id WHERE jt.terminal_num=? and jt.sdnum=?",
					requestDto.getDevMac(), requestDto.getSdMac());
			if (record == null) {
				return mID;
			}
			DevInformationBean devInformationBean = new DevInformationBean();

			devInformationBean.setSdNum(record.getStr("sdnum"));
			devInformationBean.setCamp(record.getStr("camp"));
			devInformationBean.setMediatorGrade(record.getStr("mediator_grade"));
			devInformationBean.setMediatorName(record.getStr("mediator_name"));
			devInformationBean.setTerminalName(record.getStr("terminal_name"));
			devInformationBean.setTerminalStauts(record.getStr("terminal_stauts"));
			devInformationBean.setSdNum(requestDto.getDevMac());
			devInformationBean.setMediatorId(record.getInt("id"));
			responseDto.setData(devInformationBean);
			mID = devInformationBean.getMediatorId();
			responseDto.setResponseStatus(1);
		} else {
			List<Map<String, String>> unTerminalList = (List<Map<String, String>>) IMcacheMap.cacheMap.get("unTerminalList");
			if (unTerminalList == null) {
				unTerminalList = new ArrayList<Map<String, String>>();
			}

			// -------------------------

			IMcacheMap.cacheMap.remove("unTerminalList");
			IMcacheMap.cacheMap.put("unTerminalList", unTerminalList);

			responseDto.setResponseStatus(0);
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
		responseDto.setTime(df.format(new Date()));
		String respo = JSON.toJSONString(responseDto);
		//
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

	/**
	 * 推送消息
	 *
	 * @param mediator
	 */
	private void pushMsg(ChannelContext<Object, IMPacket, Object> channelContext, int mediator) {
		// 推送标志点 1：初始化推送，2；标志点信息更改推送

		// 获取当前连线的离线消息
		List<MessageBean> messageBeans = null;
		// 判断当前连线是否有离线消息
	}

}
