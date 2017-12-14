package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.client.TestBean;
import com.base.im.common.IMPacket;
import com.base.model.*;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.*;
import com.base.service.app.AppHandle.AppBean.DevInstructionBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.base.utils.GZipUtil;

import org.tio.client.ClientChannelContext;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @类名: GainDevInformation
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 指令上报
 * @所属: 华夏九鼎
 * @日期: 2017/6/8 9:08
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 **/
@RequestCodeMaping(requestCode = RequestCodeConstants.INSTRUCT_UP_CODE)
public class GainDevInstruction extends BaseHandleImpl {

    @Override
    public String init(RequestDto data) {
        JSONObject object = JSONObject.parseObject(data.getData().toString());
        List<JMediator> mediators = new ArrayList<>();

        if (object.getInteger("msgFlag") == 0) {
            //这里处理给全组发指令
            mediators = GroupQuery.me().getGroupMediator(object.getInteger("groupId"));

         } else {
            // 给部分人发送指令
            JSONArray array = object.getJSONArray("mediators");
            for (int i = 0; i < array.size(); i++) {
                JSONObject objectt = array.getJSONObject(i);
                int recviceId = objectt.getInteger("id");
                String reciveName = objectt.getString("mediatorName");
                JMediator mediator=new JMediator();
                mediator.setId(recviceId);
                mediator.setMediatorName(reciveName);
                mediators.add(mediator);
            }
        }

            //mediators=object.getJSONArray("mediators");
            boolean isoff = false;
            boolean isffo = false;

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            //在线
            DevInstructionBeanDto devInstructionBeanDto = new DevInstructionBeanDto();
            devInstructionBeanDto.setInstructionType("0");
            devInstructionBeanDto.setSenderId(object.getInteger("sender"));
            devInstructionBeanDto.setSenderName(object.getString("sendName"));
            
            //String groupId = object.getInteger("groupId").toString();
            GroupQuery.me().findById(object.getInteger("groupId")).getCamp();//camp
            
            //groupId？？camp 待更新、、、==================================================================================================
            devInstructionBeanDto.setCamp(GroupQuery.me().findById(object.getInteger("groupId")).getCamp());
            devInstructionBeanDto.setMsg(object.getString("msg"));
            devInstructionBeanDto.setSendTime(df.format(new Date(Long.parseLong(object.getString("sendTimeDB")) ))  );

            //保存到所有信息表中
            JMessageall message = new JMessageall();
            message.setSenderId(object.getInteger("sender"));
            message.setSendName(object.getString("sendName"));
            message.setMessage(object.getString("msg"));
            message.setSendTime(df.format(new Date(Long.parseLong(object.getString("sendTimeDB")) )));
            message.setState(1);
            message.setCamp(GroupQuery.me().GainCampByMediatorId(object.getInteger("sender")).getCamp().toString());
            /*if("2".equals(object.getInteger("groupId").toString())){//导演部
            	message.setCamp(GroupQuery.me().GainCampByMediatorId(object.getInteger("sender")).getCamp().toString());
            } else {
            	message.setCamp(GroupQuery.me().findById(object.getInteger("groupId")).getCamp().toString());
            	
            }*/
            message.setMsgType("0");
            message.save();
            
            //发送消息给C平台
            ClientChannelContext<Object, IMPacket, Object> clientChannelContext = (ClientChannelContext<Object, IMPacket, Object>) IMcacheMap.cacheMap.get("IM-C");
            //byte[] front = "$C".getBytes("utf-8");
            //isClosed:true, isRemoved:false;
            if(clientChannelContext==null || clientChannelContext.isClosed() || clientChannelContext.isRemoved()){
            	JMsg msg = new JMsg();
            	msg.setMsgType("C-0");
            	msg.setSenderId(message.getSenderId());
            	msg.setSendName(message.getSendName());
            	msg.setSendTime(message.getSendTime());
            	msg.setMessage(message.getMessage());
            	msg.save();
            } else{
            	try {
            		IMPacket packet1 = new IMPacket();
        			//packet1.setBody("$0".getBytes("utf-8"));
        			packet1.setBody("$0".getBytes("GBK"));
        			Aio.send(clientChannelContext, packet1);//"$0"-->文本信息
        			
        			
        			IMPacket packet2 = new IMPacket();
        			packet2.setBody(message.getCamp().getBytes("GBK"));
        			Aio.send(clientChannelContext, packet2);//阵营
        			
        			IMPacket packet4 = new IMPacket();
        			packet4.setBody(object.getString("sender").getBytes("GBK"));
        			Aio.send(clientChannelContext, packet4);//发送人ID
        			
        			IMPacket packet5 = new IMPacket();
        			packet5.setBody(object.getString("sendName").getBytes("GBK"));
        			Aio.send(clientChannelContext, packet5);//发送人姓名
        			
        			IMPacket packet6 = new IMPacket();
        			packet6.setBody(message.getSendTime().getBytes("GBK"));
        			Aio.send(clientChannelContext, packet6);//发送时间
   			
        			IMPacket packet3 = new IMPacket();
        			//packet3.setBody(object.getString("msg").getBytes("utf-8"));
        			packet3.setBody(object.getString("msg").getBytes("GBK"));
        			Aio.send(clientChannelContext, packet3);//发送消息体
				} catch (Exception e) {
					// TODO: handle exception
					System.out.println("=====指令发送C平台错误！！！=====");
					e.printStackTrace();
				}
            	
            }
            
            
            //发送C平台结束
          
            ResponseDto responseDtoMe = new ResponseDto();
            responseDtoMe.setHandle(2);
            responseDtoMe.setCode(Integer.parseInt(RequestCodeConstants.INSTRUCT_UP_CODE));
            responseDtoMe.setResponseStatus(1);
            
            responseDtoMe.setTime(df.format(new Date()));

            JMsg jMsg = new JMsg();
            JInstruction jInstruction = new JInstruction();
            //终端传上来的指令不下发

            String str = JSON.toJSONString(responseDtoMe);
            return JSON.toJSONString(responseDtoMe);


//


    }
}
