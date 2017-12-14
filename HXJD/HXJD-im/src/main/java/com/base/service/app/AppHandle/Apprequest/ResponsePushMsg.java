package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JMsg;
import com.base.model.JOffuser;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.OffUserQuery;
import com.base.service.app.MsgQuery;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.OffMsgType;
import com.base.service.app.AppInterface.RequestCodeMaping;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ResponsePushMsg
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 响应推送消息是否成功
 * @所属: 华夏九鼎
 * @日期: 2017/6/9 14:41
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.PUSH_SUCCESS)
public class ResponsePushMsg extends BaseHandleImpl {
    @Override
    /**
     * 响应推送消息是否成功,需要的参数：mediatorId，groupId
     */
    public String init(RequestDto data) {
        JSONObject object= JSON.parseObject(data.getData().toString());
        int mediatorId=object.getInteger("mediatorId");
        int groupId=object.getInteger("groupId");
        List<JOffuser> offusers = OffUserQuery.me().GainOffUserByMedatorId(mediatorId);
        if (offusers != null) {
            for (JOffuser offuser : offusers) {
                String[] imgIds = offuser.getMsgId().split("\\|");
                if (offusers != null) {
                    for (String imgId : imgIds) {
                        JMsg jMsg = MsgQuery.me().findById(Integer.parseInt(imgId));
                        if(jMsg != null){
                            jMsg.delete();
                        }
                    }
                    offuser.delete();
                }
            }
        }


        //获得与mediatorId相匹配的,mediatorId是需要从终端传回来//
//        List<JOffuser> offusers=OffUserQuery.me().GainOffUserByMedatorId(Integer.parseInt(mediatorId));
//        for(JOffuser offuser:offusers){
//            //todo 因为推送离线消息是一条一条发送的，并且每发送一条，终端都会给服务器响应一遍。那要是服务器还没发完，而终端就将第一条的响应发回来了，那这就只能删这一条数据。
//            //解决方法一：发送离线消息，给这个人的离线消息发送完后做个标记，发送给终端，当终端接受到这个标记，才响应。但是这意味着发送消息这块就需要加一个字段。
//
//        }
        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.PUSH_SUCCESS));
        responseDto.setResponseStatus(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
