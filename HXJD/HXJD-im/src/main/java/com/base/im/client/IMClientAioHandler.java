package com.base.im.client;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.ArrayUtils;
import org.tio.client.ClientChannelContext;
import org.tio.client.intf.ClientAioHandler;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.core.GroupContext;
import org.tio.core.ObjWithLock;

import com.alibaba.fastjson.JSON;
import com.base.constants.MessageConstants;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.common.IMAbsAioHandler;
import com.base.im.common.IMPacket;
import com.base.model.JGroup;
import com.base.model.JMediator;
import com.base.model.JMessageall;
import com.base.model.JMsg;
import com.base.model.JOffuser;
import com.base.model.JPel;
import com.base.model.JTerminal;
import com.base.model.JUserrole;
import com.base.model.dto.ResponseDto;
import com.base.service.DrillcodeQuery;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.OffUserQuery;
import com.base.service.PelQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.utils.GZipUtil;
import com.base.utils.GsonUtil;
import com.base.utils.StringUtils;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.Record;

public class IMClientAioHandler extends IMAbsAioHandler implements ClientAioHandler<Object, IMPacket, Object>
{
	//保存接受的字节，最后组合成文件
	private static byte[] bytes = null;
		
	//文件名
	private static String fileName = "";
		
	//文件长度
	private static BigInteger fileLength = new BigInteger("0");
		
	//当前已读取文件长度
	private static BigInteger currentLength = new BigInteger("0");
		
	//
	private static Integer num = 0;
	
	private static String camp = null; 
	
	//文件类型
	private static String[] video = {"mp4","rmvb","mkv","mov","avi","3gp","wmv","flv","f4v","ts","vob","m2ts"};
	private static String[] audio = {"mp3","amr","wav","flac","ape","aac"};
	private static String[] picture = {"jpg","png","bmp","gif","tif","jpeg","ico"};
	
	//
	//private static int state = 0; 
	
	//
	//private static int count = 0;
	//private static int count_ = 0;
	
	/** 
	 * 处理消息
	 */
	@Override 
	public Object handler(IMPacket packet, ChannelContext<Object, IMPacket, Object> channelContext) throws Exception
	{
		System.out.println("handler");
		byte[] body = packet.getBody();
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		
		IMPacket msgResPacket = new IMPacket();
		
		
		
		if (body != null)
		{			
		try {	
			
			if(num > 0){//第二个包开始，后面都是文件
				System.out.println("==文件数据== " + num);
				//byte[] dataByte = ArrayUtils.subarray(body,0,body.length);
				//数组附加
				bytes = ArrayUtils.addAll(bytes, body);
				currentLength = currentLength.add(new BigInteger(body.length + ""));
				System.out.println("currentLength: " + currentLength);
				num++;
				System.out.println("boolean: " + (currentLength.compareTo(fileLength)==0));
				if(currentLength.compareTo(fileLength)==0){//所有数据传完了  
						//-->将合并数据写入磁盘
					String prefix = JFinal.me().getServletContext().getRealPath("/attachment/");	
					
					//String prefix = "C:/Users/kevin/Desktop/IMTEST/";				
					JMsg msg = new JMsg();
					System.out.println("fileName: " + fileName);
					String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
					String typePath = "";
					for (String str : video) {
						if(str.equalsIgnoreCase(fileType)){
							msg.setMsgType("2");
							typePath="video";
						}
					}
					for (String str : audio) {
						if(str.equalsIgnoreCase(fileType)){
							msg.setMsgType("3");
							typePath="audio";
						}
					}
					for (String str : picture) {
						if(str.equalsIgnoreCase(fileType)){
							msg.setMsgType("1");
							typePath="image";
						}
					}
					if(!StringUtils.isNotEmpty(msg.getMsgType())){
						msg.setMsgType("4");
						typePath="document";
					}
					
					//防止中文图片名导致的URL编码乱码，文件名改为UUID
					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					String date = format.format(new Date());
					fileName= UUID.randomUUID().toString().replace("-", "")+"."+fileType;
					msg.setMessage("\\attachment\\"+ typePath+"\\" +date +"\\" +fileName);
					msg.setSendTime(df.format(new Date()));
					msg.setSenderId(0);
					msg.setSendName("平台");
					saveFile(prefix + "/" + typePath + "/" +date);
					msg.saveOrUpdate(); 
					
					saveMessage(msg);
					
					ChannelContext context = null;
					JGroup group =  GroupQuery.me().getGroupByCamp(Integer.parseInt(camp));
					List<JTerminal> tList = TerminalQuery.me().getTerminaByGroupId(group.getId());
					for (JTerminal jTerminal : tList) {
						context = (ChannelContext) IMcacheMap.cacheMap.get(jTerminal.getSdnum());
						if(context!=null){
							break;
						}
					}
					saveOffMsg(msg,context,camp);	
					camp = null;
		
				}
		}else if(num == 0){
				//头信息-->长度
				byte[] byte1 = ArrayUtils.subarray(body, 0, 4); 
				String byteStr = new String(byte1, "gbk");

				if(!"0003".equals(byteStr)&&!"0002".equals(byteStr)){
						return null;					
				}
				BigInteger len = new BigInteger(byteStr); //单个包消息体长度
				String headStr  = new String(ArrayUtils.subarray(body, 4, 4+len.intValue()),"gbk");
				
				byte[] tempByte = body;				
				int currentPosition = 0;
				int bodyLength = body.length;
				
				if("$0".equals(headStr)){//文字信息
					byte[] bytes1 = ArrayUtils.subarray(body,10,11);
					camp = new String (bytes1, "gbk"); //从C端获取
					
					byte[] bytes = ArrayUtils.subarray(body,11,body.length);
					String str = new String (bytes, "gbk");
					System.out.println("收到消息： " + str + " 发给阵营: " + camp);
				
					//msgResPacket.setBody(GZipUtil.doZip(str));
					//Aio.sendToGroup(channelContext.getGroupContext(), groupId, msgResPacket);//发送消息到相应群组
					
					//保存离线信息...					
					JMsg msg = new JMsg();
					msg.setMessage(str);
					msg.setSendTime(df.format(new Date()));
					msg.setMsgType("0");
					msg.setSenderId(0);
					msg.setSendName("平台");
					msg.saveOrUpdate();
					
					//保存信息
					saveMessage(msg);
					
					ChannelContext context = null;
					JGroup group =  GroupQuery.me().getGroupByCamp(Integer.parseInt(camp));
					List<JTerminal> tList = TerminalQuery.me().getTerminaByGroupId(group.getId());
					for (JTerminal jTerminal : tList) {
						if(jTerminal!=null){
							context = (ChannelContext) IMcacheMap.cacheMap.get(jTerminal.getSdnum());
							if(context!=null){
								break;
							}
						}
						
					}
					
					saveOffMsg(msg,context,camp);
					camp = null;
					
				}else if("$1".equals(headStr)){//传文件  第一个包
						System.out.println("==文件名== ");
						tempByte = ArrayUtils.subarray( tempByte,4+len.intValue(),tempByte.length);
						byte[] nameLengthByte = ArrayUtils.subarray(tempByte, 0, 4);
						String namelengthStr = new String(nameLengthByte, "gbk");
						BigInteger nameLen = new BigInteger(namelengthStr);
						
						byte[] nameByte = ArrayUtils.subarray(tempByte, 4, 4+nameLen.intValue());
						fileName = new String(nameByte, "gbk");
						System.out.println("名字 ： " + fileName);
						
						
						System.out.println("==文件长度== ");
						tempByte = ArrayUtils.subarray(tempByte,4+nameLen.intValue(),tempByte.length);
						
						byte[] lengthByte = ArrayUtils.subarray(tempByte, 0, 4);
						BigInteger fileLengthLen = new BigInteger(new String(lengthByte, "gbk"));
						
						byte[] fileLenByte = ArrayUtils.subarray(tempByte, 4, 4+fileLengthLen.intValue());
						fileLength =new BigInteger (new String(fileLenByte, "gbk"));
						System.out.println("长度： " + fileLength);
												//$1              //文件名                                            //文件长 
						currentPosition = 4+len.intValue()+4+nameLen.intValue()+4+fileLengthLen.intValue();
						 
						System.out.println("==群组==");					//currentPosition+4
						byte[] toByte = ArrayUtils.subarray(body, currentPosition+4, currentPosition+5);
						for (byte b : toByte) {
							System.out.println("toByte: " + b);
						}
						
						camp = new String(toByte, "gbk");
						System.out.println("发送给阵营: " + camp);
						
						currentPosition = currentPosition +5;
						
						if(body.length > currentPosition){//后面还有消息体
							
							//byte[] dataByte = ArrayUtils.subarray(tempByte,4,body.length);
							System.out.println("==文件数据== " + num);
							
							byte[] dataByte = ArrayUtils.subarray(body,currentPosition,body.length);
							//数组附加
							bytes = ArrayUtils.addAll(bytes, dataByte);
							//长度累加
							currentLength = currentLength.add(new BigInteger(dataByte.length + ""));
							System.out.println("currentLength: " + currentLength);
							num++;
							System.out.println("boolean: " + (currentLength.compareTo(fileLength)==0));
							if(currentLength.compareTo(fileLength)==0){//所有数据传完了  
								String prefix = JFinal.me().getServletContext().getRealPath("/attachment/");
								//String prefix = "C:/Users/kevin/Desktop/IMTEST/";
								
								JMsg msg = new JMsg();
								
								String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
								String typePath = "";
								for (String str : video) {
									if(str.equalsIgnoreCase(fileType)){
										msg.setMsgType("2");
										typePath="video";
									}
								}
								for (String str : audio) {
									if(str.equalsIgnoreCase(fileType)){
										msg.setMsgType("3");
										typePath="audio";
									}
								}
								for (String str : picture) {
									if(str.equalsIgnoreCase(fileType)){
										msg.setMsgType("1");
										typePath="image";
									}
								}
								if(!StringUtils.isNotEmpty(msg.getMsgType())){
									msg.setMsgType("4");
									typePath="document";
								}
								
								//防止中文图片名导致的URL编码乱码，文件名改为UUID
								SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
								String date = format.format(new Date());
								fileName = UUID.randomUUID().toString().replace("-", "")+"."+fileType;
								msg.setMessage("\\attachment\\"+ typePath+"\\" +date +"\\" + fileName);
								msg.setSendTime(df.format(new Date()));
								msg.setSenderId(0);
								msg.setSendName("平台");
								saveFile(prefix + "/" + typePath + "/" +date);
								msg.saveOrUpdate(); 
								
								//保存信息
								saveMessage(msg);
							//=====
							//通过groupId 得到用户-->得到chanelContext	
								//List<JMediator> mList = MediatorQuery.me().getMediatorByGroupId(Integer.parseInt(groupId));
								ChannelContext context = null;
								JGroup group =  GroupQuery.me().getGroupByCamp(Integer.parseInt(camp));
								List<JTerminal> tList = TerminalQuery.me().getTerminaByGroupId(group.getId());
								for (JTerminal jTerminal : tList) {
									if(jTerminal!=null){
										context = (ChannelContext) IMcacheMap.cacheMap.get(jTerminal.getSdnum());
										if(context!=null){
											break;
										}
									}
									
								}
								
							//=====	
								saveOffMsg(msg, context, camp);
								camp = null;
	  						}
						}else{
							num ++;
						}
					} else if("$99".equals(headStr)){//下发数据
						//count_++;
						System.out.println("===下发数据===");
						List<JPel> jPelList = new ArrayList<JPel>();
						
						tempByte = ArrayUtils.subarray( tempByte,4+len.intValue(),tempByte.length);
						String str = new String(tempByte, "gbk").trim();
						//System.out.println("str: " + str);
						//str = str.split("\\@")[0];
						//System.out.println(str.length());
						//System.out.println("===下发数据===: " + str);
						
						String[] s = str.split("\\@");
/*						for (String string : s) {
							System.out.println("String[]: " + string);
						}*/

						/*for (int i = 0; i<s.length-1 ;i++) {
							temps[i] = s[i];
						}*/
						
						
						
					/*	if(str.contains("0003$99")){
							System.out.println("==contains==");
							s = str.split("0003\\$99");
						} else { 
							System.out.println("==not--contains==");
							s = new String[1]; 
							s[0] = str;
						}*/

						for (String a : s) {
							if(!a.startsWith("[{\"") || !"\"}]".equals(a.subSequence(a.length()-3, a.length())) ){
								continue;
							}
							
							if(!com.alibaba.druid.util.StringUtils.isEmpty(a)){
								try {
									List<Map<String, Object>> gson = GsonUtil.jsonToListMap(a);
								
								
								
								String code = gson.get(0).get("id").toString();
								String axesX = gson.get(0).get("axesX").toString();
								String axesY = gson.get(0).get("axesY").toString();
								String dir = gson.get(0).get("dir").toString();
								String att = gson.get(0).get("att").toString();
								String name = gson.get(0).get("name").toString();
								String ax="0.0";
								String ay="0.0";
								BigDecimal a60 = new BigDecimal("60");
								BigDecimal a3600 = new BigDecimal("3600");
								if(Double.parseDouble(axesX)!=0.0){
									ax = new BigDecimal((Integer.parseInt(axesX.substring(0, 3).toString())) + (new BigDecimal(axesX.substring(3, 5).toString()).divide(a60,10,BigDecimal.ROUND_HALF_EVEN).doubleValue()) + (new BigDecimal(axesX.substring(5, axesX.length()).toString()).divide(a3600,10,BigDecimal.ROUND_HALF_EVEN).doubleValue())).setScale(10,BigDecimal.ROUND_HALF_EVEN).toString();
								}
								
								if(Double.parseDouble(axesY)!=0.0){
									ay = new BigDecimal((Integer.parseInt(axesY.substring(0, 2).toString())) + (new BigDecimal(axesY.substring(2, 4).toString()).divide(a60,10,BigDecimal.ROUND_HALF_EVEN).doubleValue()) + (new BigDecimal(axesY.substring(4, axesY.length()).toString()).divide(a3600,10,BigDecimal.ROUND_HALF_EVEN).doubleValue())).setScale(10,BigDecimal.ROUND_HALF_EVEN).toString();
								}
//								double[] ddd = PositionUtil.gcj02_To_Bd09(Double.parseDouble(ay), Double.parseDouble(ax));
//								ax = ddd[1]+"";
//								ay = ddd[0]+"";
								String camp = code.substring(2, 3).toString();
								
//								BigDecimal a60 = new BigDecimal("60");
//								BigDecimal a3600 = new BigDecimal("3600");
								
//								String ax = (Double.parseDouble(axesX.substring(0, 3).toString())+new BigDecimal(axesX.substring(3, 5).toString()).divide(a60,5,BigDecimal.ROUND_HALF_EVEN).doubleValue()+new BigDecimal(axesX.substring(5, axesX.length()).toString()).divide(a3600,5,BigDecimal.ROUND_HALF_EVEN).doubleValue())+"";
//								String ay = (Double.parseDouble(axesY.substring(0, 2).toString())+new BigDecimal(axesY.substring(2, 4).toString()).divide(a60,5,BigDecimal.ROUND_HALF_EVEN).doubleValue()+new BigDecimal(axesY.substring(4, axesY.length()).toString()).divide(a3600,5,BigDecimal.ROUND_HALF_EVEN).doubleValue())+"";
								
								JPel oldPel = PelQuery.me().findPelByCode(code);
								//count++;
								if(null==oldPel){//新增
									oldPel = new JPel();
									oldPel.setOid(att);
									oldPel.setPelname(name);
									oldPel.setCode(code);
									oldPel.setShape("0");
									oldPel.setX(ax);
									oldPel.setY(ay);
									oldPel.setState(1);
									oldPel.setCamp((Integer.parseInt(camp)-1)+"");
									oldPel.setLocation(dir);
									oldPel.setType(new Integer(2));
									
									if(null != DrillcodeQuery.me().getActiveDrill()){
										oldPel.setDrill(DrillcodeQuery.me().getActiveDrill().getId());
									}
									oldPel.saveOrUpdate();
									sendPelToterminal(oldPel,2);
								}else{//更新
									
									if((oldPel.getX()!=ax && !oldPel.getX().equals(ax)) || (oldPel.getY()!=ay && !oldPel.getY().equals(ay))){
										oldPel.setOid(att);
										oldPel.setPelname(name);
										oldPel.setCode(code);
										oldPel.setShape("0");
										oldPel.setX(ax);
										oldPel.setY(ay);
										oldPel.setState(1);
										oldPel.setCamp((Integer.parseInt(camp)-1)+"");
										oldPel.setLocation(dir);
										oldPel.setType(new Integer(2));
										if(null !=  DrillcodeQuery.me().getActiveDrill()){
											oldPel.setDrill(DrillcodeQuery.me().getActiveDrill().getId());
										}
										oldPel.saveOrUpdate();
										sendPelToterminal(oldPel,3);
									}
								}
								//jPelList.add(oldPel);
								//succNum++;
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							}
						}
						
						
						
						
					}else{
						byte[] temp = ArrayUtils.subarray(body, 4, body.length);
						//普通消息(终端过来的数据)
						//System.out.println("客户端收到消息：" + GZipUtil.unZipByte(temp));	
						System.out.println("头未定义：" + body);
					}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("==ClienthandlerError!==");
			num = 0;
			bytes = null;
			fileName = "";
			fileLength = new BigInteger("0");
			currentLength = new BigInteger("0");
			camp = null;
			e.printStackTrace(); 
		}
		} else{
			System.out.println("body null");
		}

		return null;
	}
	
	/**
	 * 
	 * saveFile
	 * 保存文件到本地
	 * @param prefix
	 * @throws FileNotFoundException   
	 *void  
	 * @exception   
	 * @since  1.0.0
	 */
	private void saveFile(String prefix) throws FileNotFoundException{
		System.out.println("==文件数据完毕== ");
		//-->将合并数据写入磁盘
		/* 
		* *文件路径根据文件类型储存，把路径传给终端，不在线-->离线信息
		*/
		//String prefix = JFinal.me().getServletContext().getRealPath("/attachment/");						
		File file = new File(prefix+"/" + fileName);  //D:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\HHT-web\attachment\document\20171018\a0836242259a4f5daf6219a3859a6b6d.txt
		
		//prefix    D:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\HHT-web\attachment/document/20171018
		//
		
			File existFile = new File(prefix);//D:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\HHT-web\attachment\document\20171018
			//File existFile = new File("C:/Users/kevin/Desktop/IMTEST/" + fileName);
		      //创建文件夹
	        if (!existFile.exists()) {
	        	existFile.mkdirs();
	        }
			if(null != file){//已存在同名文件
	        	File[] fileReadName = existFile.listFiles();
	            for (File files : fileReadName) {
	            	if(files.getName().equals(fileName)){
	            		int point = fileName.lastIndexOf(".");
	            		String front = fileName.substring(0, point);
	            		String end = fileName.substring(point);
	            		fileName = front + "-" + System.currentTimeMillis()+end;
	    			}
	    		}
	        }

			OutputStream output;
			try {
				output = new FileOutputStream(file);
				BufferedOutputStream bufferedOutput = new BufferedOutputStream(output);
				bufferedOutput.write(bytes);
				bufferedOutput.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				num =0 ;
				bytes = null;
				fileName = "";
				fileLength = new BigInteger("0");
				currentLength = new BigInteger("0");
				//isMulit = false;
				System.out.println("==文件已写入磁盘！！！==");
			}

	}
	
	/**
	 * 
	 * saveOffMsg(这里用一句话描述这个方法的作用)  
	 * 发送指令，并保存离线信息
	 * @param message   
	 *void  
	 * @throws Exception 
	 * @exception   
	 * @since  1.0.0
	 */
	private void saveOffMsg(JMsg msg,  ChannelContext<Object, IMPacket, Object> channelContext, String camp_) throws Exception{
		//List<JMediator> mediatorList = MediatorQuery.me().getMediatorByGroupId(Integer.parseInt(groupId));//groupId
		List<Record> list2 = TerminalQuery.me().GainTerminaByGroupId(GroupQuery.me().getGroupByCamp(Integer.parseInt(camp_)).getId());//调理员+终端ID
		//List<JTerminal> list3 = TerminalQuery.me().getAllTerminal();
/*		JMsg msg = new JMsg();
		msg.setMessage(message);		
		msg.setMsgType("0");//一般消息都为0//1 - 图片；2 - 视频； 3- 语音
		msg.setSendTime(df.format(new Date()));
		msg.save();*/
		IMPacket pac = new IMPacket();
		//pac.setBody(GZipUtil.doZip(JSON.toJSONString(msg)));
		//Aio.getChannelContextsByGroup(channelContext.getGroupContext(), groupId);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		
		ResponseDto responseDto = new ResponseDto();
		MessageBean messageBean = new MessageBean();
		messageBean.setGroupId(2);//Integer.parseInt(groupId)//指令
		messageBean.setMsg(msg.getMessage());
		messageBean.setSendTime(msg.getSendTime());
		messageBean.setMsgType(msg.getMsgType());
		messageBean.setSendName("平台");
		messageBean.setSender("0");
		responseDto.setData(messageBean);
		responseDto.setCode(13);
		responseDto.setTime(df.format(new Date()));
		String respo = JSON.toJSONString(responseDto);
		//压缩数据
        byte[] doZipMsg = new byte[0];
        try {
            doZipMsg = GZipUtil.doZip(respo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        pac.setBody(doZipMsg);
                
/*        if(channelContext!=null){
        	if(Aio.getChannelContextsByGroup(channelContext.getGroupContext(), groupId)!=null){
        		Aio.sendToGroup(channelContext.getGroupContext(), groupId, pac);
        		System.out.println("--groupSize--: " + Aio.getChannelContextsByGroup(channelContext.getGroupContext(), groupId).getObj().size());
        		System.out.println("===send to group: "+ groupId + " ===");
        	}       	
        }*/


		for (Record record : list2) {
			String sdNum = record.get("sdnum");
			ChannelContext context = (ChannelContext)IMcacheMap.cacheMap.get(sdNum);
			if(context==null){//没有这个连接--->离线
				
				JOffuser offuser = new JOffuser();
				offuser.setMsgId(msg.getId().toString());
				offuser.setMediatorId((Integer)record.get("mediator_id"));
				offuser.setGroupId(GroupQuery.me().getGroupByCamp(Integer.parseInt(camp_)).getId());
				//offuser.setGroupId(new Integer(2));
				OffUserQuery.me().updateAndSave(offuser);
				
			} else {
/*				String s="++++++++++++++++++++++++++++++++++++++++++++++++/r/n"
						+ "+++++++++++++++++++++++++++++++++++++++++++++++/r/n"
						+ "======发送给【"+ record.get("terminal_name")+"】 : " + messageBean.getMsg()+"=====/r/n";
				System.out.println(s);*/
				Aio.send(context, pac);
			}
		}
		
	}
	
	private void saveMessage(JMsg msg){
		JMessageall message = new JMessageall();
		message.setSenderId(msg.getSenderId());
		message.setMsgType(msg.getMsgType());
		message.setMessage(msg.getMessage());
		message.setSendTime(msg.getSendTime());
		message.setSendName(msg.getSendName());
		message.setCamp(camp);
		message.setState(1);
		message.save();
	}
	
	private void sendPelToterminal(JPel pel, int state){
		List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
		
		DevPelBeanDto devPelBeanDto=new DevPelBeanDto();
        devPelBeanDto.setId(pel.getId());
        devPelBeanDto.setCamp(pel.getCamp());
        devPelBeanDto.setCode(pel.getCode());
        devPelBeanDto.setDamage(pel.getDamage());
  
        devPelBeanDto.setLocation(pel.getLocation());
        devPelBeanDto.setY(pel.getY());
        devPelBeanDto.setX(pel.getX());
        devPelBeanDto.setState(pel.getState());
        devPelBeanDto.setPelName(pel.getPelname());
        devPelBeanDto.setShape(pel.getShape());
        devPelBeanDto.setType(pel.getType().toString());
        devPelBeanDtos.add(devPelBeanDto);
       
        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
        responseDto.setResponseStatus(state);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(devPelBeanDtos);
        String str = JSON.toJSONString(responseDto);
        String respon = JSON.toJSONString(responseDto);
        
        try {
        	IMPacket imPacket = new IMPacket();
            byte[] doZipMsg = GZipUtil.doZip(respon);
            imPacket.setBody(doZipMsg);
            String camp = pel.getCamp();
    		if(StringUtils.isNotEmpty(camp)){
    			List<JMediator> medList = MediatorQuery.me().getMediatorByGroupId(GroupQuery.me().getGroupByCamp(Integer.valueOf(camp)).getId());
    			for (JMediator jMediator : medList) {    				
    				if(jMediator!=null){
    					JTerminal terminal = TerminalQuery.me().getByMediator(jMediator.getId());
    					if(terminal!=null){
    						ChannelContext ChannelContext = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
            				if(null!=ChannelContext){
            					Aio.send(ChannelContext, imPacket);
            					/*if(Aio.getChannelContextsByGroup(ChannelContext.getGroupContext(), camp)!=null){
            						Aio.sendToGroup(ChannelContext.getGroupContext(), GroupQuery.me().getGroupByCamp(Integer.valueOf(camp)).getId().toString(), imPacket);
            																			//groupId
            						break;*/
            					}	
            				}
    					}	
    				}    				
    			}

            
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("新增图元终端下发出错了!!!");
			e.printStackTrace();
		}
	}
	
	
	private static IMPacket heartbeatPacket = new IMPacket();

	/** 
	 * 此方法如果返回null，框架层面则不会发心跳；如果返回非null，框架层面会定时发本方法返回的消息包
	 */
	@Override
	public IMPacket heartbeatPacket()
	{
		return heartbeatPacket;
		//return null;
	}
}
