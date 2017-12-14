package com.base.im.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.common.IMAbsAioHandler;
import com.base.im.common.IMPacket;
import com.base.model.JGroup;
import com.base.model.JMediator;
import com.base.model.JMsg;
import com.base.model.JObject;
import com.base.model.JOffuser;
import com.base.model.JTerminal;
import com.base.model.dto.*;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.OffUserQuery;
import com.base.service.TerminalQuery;
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
				System.out.println("===============心跳包===============");
				return null;
			}

			String str = StringGZIP.unCompress(body);
			System.out.println("收到消息：" + GZipUtil.unZipByte(body));
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
				System.out.println("requsetCode: " + requestDto.getCode());
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

			JMediator mediator = MediatorQuery.me().findById(isInitialize);
			JTerminal terminal = TerminalQuery.me().getByMediator(isInitialize);

			if (null != requestDto.getData()) {
				Map<String, Object> data = JSON.parseObject(JSON.toJSONString(requestDto.getData()));

				if (data.get("axesX") != null && !"0".equals(data.get("axesX"))) {
					mediator.setX(data.get("axesX").toString());
				}
				if (data.get("axesY") != null && !"0".equals(data.get("axesY"))) {
					mediator.setY(data.get("axesY").toString());
				}
				if (data.get("batter") != null && !"0".equals(data.get("batter"))) {
					terminal.setTerminalPower(data.get("batter").toString());
				}
				mediator.update();
				terminal.update();
			}

			// 绑定群组(GroupID) //调理员Id
			List<JGroup> groupList = GroupQuery.me().GainGroupByMediatorId(isInitialize);
			for (JGroup jGroup : groupList) {
				Aio.bindGroup(channelContext, jGroup.getId().toString());
			}

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

			JTerminal terminal = TerminalQuery.me().initializeTerminal(requestDto.getSdMac());
			JTerminal terminal_ = TerminalQuery.me().getTerminalByTernum(requestDto.getDevMac());
			// -------------------------
			if (terminal == null && terminal_ == null) {// 没注册
				Map<String, String> map = new HashMap<String, String>();
				map.put("sdMac", requestDto.getSdMac());
				map.put("devMac", requestDto.getDevMac());
				if (unTerminalList != null && unTerminalList.size() > 0) {// 以前有的就不加，避免重复
					boolean flag = true;
					for (Map<String, String> map_ : unTerminalList) {
						if (map_.get("sdMac").equals(map.get("sdMac"))
								&& map_.get("devMac").equals(map.get("devMac"))) {
							flag = false;
							break;
						}
					}
					if (flag) {
						unTerminalList.add(map);
					}
				} else {
					unTerminalList.add(map);
				}
			}

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
		System.out.println("ini-resppacket: " + respo);
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
		List<JOffuser> offusers = OffUserQuery.me().GainOffUserByMedatorId(mediator);
		List<MessageBean> messageBeans = null;
		// 判断当前连线是否有离线消息
		if (offusers != null) {
			for (JOffuser offuser : offusers) {
				List<String> imgIdList = new ArrayList<>();

				String[] imgIds = offuser.getMsgId().split("\\|");
				int msgCount = 0;

				// ResponseDto
				ResponseDto responseDto = new ResponseDto();
				responseDto.setHandle(2);
				// 此处code要改变
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置日期格式
				responseDto.setTime(df.format(new Date()));
				responseDto.setResponseStatus(1);
				// 109
				if (Integer
						.parseInt(offuser.getOffType() == null ? "0" : offuser.getOffType()) == OffMsgType.SCORE_TYPE) {
					// 推送离线评分
					responseDto.setCode(OffMsgType.SCORE_TYPE);
					List<ScoreBean> scoreBeans = new ArrayList<ScoreBean>();
					responseDto.setResponseStatus(1);
					for (String imgId : imgIds) {
						// msgIdList.add(imgId);

						msgCount++;
						JMsg msg = MsgQuery.me().findById(Integer.parseInt(imgId));

						if (!(msg.getMsgType().equalsIgnoreCase("C-0") || msg.getMsgType().equalsIgnoreCase("C-1"))) {
							imgIdList.add(imgId);
						}

						ScoreBean scoreBean = new ScoreBean();

						scoreBean.setMsgType(msg.getMsgType());
						scoreBean.setMsg(msg.getMessage());
						scoreBean.setSendTime(msg.getSendTime());
						scoreBeans.add(scoreBean);
					}
					System.out.println("msgIdList: " + JSON.toJSONString(scoreBeans));
					responseDto.setData(scoreBeans);

				} else {
					// 推送离线消息
					responseDto.setCode(13);
					messageBeans = new ArrayList<>();
					for (String imgId : imgIds) {
						msgCount++;
						JMsg msg = MsgQuery.me().findById(Integer.parseInt(imgId));

						// 消息
						if (!(msg.getMsgType().equalsIgnoreCase("C-0") || msg.getMsgType().equalsIgnoreCase("C-1"))) {
							imgIdList.add(imgId);
						}

						MessageBean messageBean = new MessageBean();
						messageBean.setSendName(msg.getSendName());
						messageBean.setMsg(msg.getMessage());
						messageBean.setSender(msg.getSenderId() + "");
						messageBean.setSendTime(msg.getSendTime());
						messageBean.setGroupId(offuser.getGroupId());
						messageBean.setMsgType(msg.getMsgType());

						messageBeans.add(messageBean);

					}
					responseDto.setData(messageBeans);
				}
				String respo = JSON.toJSONString(responseDto);
				IMPacket resppacket = new IMPacket();

				// 压缩数据
				byte[] doZipMsg = new byte[0];
				try {
					doZipMsg = GZipUtil.doZip(respo);
				} catch (Exception e) {
					e.printStackTrace();
				}
				resppacket.setBody(doZipMsg);
				System.out.println("resppacket: " + respo);
				Aio.send(channelContext, resppacket);
				delPush(imgIdList, offuser);// 删除离线信息
			}
		}
	}

	protected void delPush(List<String> pushMsgs, JOffuser offuser) {
		if (Integer.parseInt((offuser.getOffType() == null ? "0" : offuser.getOffType())) == OffMsgType.SCORE_TYPE) {
			for (String msgId : pushMsgs) {

				MsgQuery.me().delById(msgId);
			}
			OffUserQuery.me().delByMedid(offuser.getMediatorId() + "");
		} else {
			OffUserQuery.me().delByMediatorIdandGroupId(offuser.getMediatorId(), offuser.getGroupId());
		}

	}

}
