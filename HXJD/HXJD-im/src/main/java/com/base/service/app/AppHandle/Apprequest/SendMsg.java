package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.model.JMediator;
import com.base.model.JMessageall;
import com.base.model.JMsg;
import com.base.model.JOffuser;
import com.base.model.JTerminal;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.GroupQuery;
import com.base.service.OffUserQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.base.utils.GZipUtil;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

import org.apache.commons.lang.ArrayUtils;
import org.tio.client.ClientChannelContext;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: SendMsg
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 发送消息
 * @所属: 华夏九鼎
 * @日期: 2017/6/8 9:19
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.SEND_MES_CODE)
public class SendMsg extends BaseHandleImpl {
    @Override
    @Before(Tx.class)
    public String init(RequestDto data) {
        SimpleDateFormat dff = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
    	try {
            JSONObject object=JSONObject.parseObject(data.getData().toString());
            //通过组id查找出群用户
            int groupId = object.getInteger("groupId");
            
            
            List<JMediator> mediators= GroupQuery.me().getGroupMediator(groupId);
            boolean isoff=false;
            boolean isme=false; 

            MessageBean mesg=new MessageBean();
            mesg.setMsgType("0");
            mesg.setSendTime(dff.format(new Date(Long.parseLong(object.getString("sendTimeDB")))));
            mesg.setSender(object.getString("sender"));
            mesg.setGroupId(object.getInteger("groupId"));
            mesg.setMsg(object.getString("msg"));
            mesg.setSendName(object.getString("sendName"));
            
            //保存信息    信息不保存在信息表中 不在网页显示
/*            JMessageall message = new JMessageall();
            
            message.setSenderId(Integer.parseInt(mesg.getSender()));
            message.setMsgType(mesg.getMsgType());
            message.setMessage(mesg.getMsg());
            message.setSendTime(mesg.getSendTime());
            message.setSendName(mesg.getSendName());
            
            //判空
            message.setCamp(object.getInteger("groupId").toString());
            message.setState(1);
            message.save();*/
            
            ResponseDto responseDtoMe = new ResponseDto();
            responseDtoMe.setHandle(2);
            responseDtoMe.setCode(Integer.parseInt(RequestCodeConstants.SEND_SUCCESS_CODE));
            responseDtoMe.setResponseStatus(1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            responseDtoMe.setTime(mesg.getSendTime());
//        responseDto.setData(mediato);
            JMsg msg1=new JMsg();
            if(mediators!=null){
                for(JMediator mediator:mediators){
                    ResponseDto responseDto=new ResponseDto();
                    responseDto.setHandle(2);
                    responseDto.setCode(Integer.parseInt(RequestCodeConstants.SEND_MES_CODE));
                    responseDto.setResponseStatus(1);

                    responseDto.setTime(df.format(new Date()));

                    JTerminal terminal= TerminalQuery.me().getByMediator(mediator.getId());
                    ChannelContext context=null;
                    if(terminal!=null){
                        context = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
                    }
//            mesg.setMsg(whatPath);
                    //如果群友在线，将文件地址变为responseDto发给他
                    if(context!=null){
                        IMPacket packet=new IMPacket();
                        //发送者自己,跳出当前循环
                        if(Integer.parseInt(mesg.getSender())==mediator.getId()){
                            continue;
                        }
                        responseDto.setData(mesg);
                        String respo= JSON.toJSONString(responseDto);
                        byte[] doZipMsg = new byte[0];
                        try {
                            doZipMsg = GZipUtil.doZip(respo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        packet.setBody(doZipMsg);
                        Aio.send(context,packet);
                    }else {
//                //离线消息
                        msg1.setSendName(mesg.getSendName());
                        msg1.setMessage(mesg.getMsg());
                        msg1.setSenderId(Integer.parseInt(mesg.getSender()));
                        msg1.setMsgType(mesg.getMsgType());
                        msg1.setSendTime(mesg.getSendTime());

                        if(!isoff){
//                    msg1.
                            msg1.save();
                            isoff=true;
                        }

                        JOffuser offuser=new JOffuser();
                        offuser.setGroupId(mesg.getGroupId());
                        offuser.setMsgId(msg1.getId().toString());
                        offuser.setMediatorId(mediator.getId());
                        OffUserQuery.me().updateAndSave(offuser);
                    }
                }
    	}
            String str = JSON.toJSONString(responseDtoMe);
            return JSON.toJSONString(responseDtoMe);
        }catch (Exception e){
            e.printStackTrace();
        	System.out.println("出错了。。。。");
        }
        return null;

    }
}
