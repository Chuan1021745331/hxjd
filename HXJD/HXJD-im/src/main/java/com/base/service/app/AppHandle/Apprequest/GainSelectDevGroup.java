package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JMediator;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.MediatorQuery;
import com.base.service.app.AppHandle.AppBean.DevGroupUserBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainSelectDevPel
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获取当前用户群组用户
 * @所属: 华夏九鼎
 * @日期: 2017/6/7 13:23
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
   @RequestCodeMaping(requestCode = RequestCodeConstants.GROUP_GET_CODE)
   public class GainSelectDevGroup extends BaseHandleImpl{


    @Override
    public String init(RequestDto data) {
        JSONObject object=JSONObject.parseObject(data.getData().toString());
        List<JMediator> jMediators = MediatorQuery.me().getMediatorByGroupId(object.getInteger("groupId"));
        if(jMediators == null){
            return null;
        }
        List<DevGroupUserBeanDto> devGroupUserBeanDtos = new ArrayList<>();
         for(JMediator jMediator :jMediators){
             DevGroupUserBeanDto devGroupUserBeanDto = new DevGroupUserBeanDto();
             devGroupUserBeanDto.setUserId(jMediator.getId());
             devGroupUserBeanDto.setUserName(jMediator.getMediatorName());
             devGroupUserBeanDto.setCamp(Integer.parseInt(jMediator.getCamp()));
             devGroupUserBeanDtos.add(devGroupUserBeanDto);

         }
           ResponseDto responseDto=new ResponseDto();
           responseDto.setResponseStatus(1);
           responseDto.setCode(Integer.parseInt(RequestCodeConstants.GROUP_GET_CODE));
           responseDto.setHandle(2);
           SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
           responseDto.setTime(df.format(new Date()));
           responseDto.setData(devGroupUserBeanDtos);
           System.out.println(devGroupUserBeanDtos.get(0).getUserId());
           System.out.println(devGroupUserBeanDtos.get(0).getUserName());
           System.out.println(devGroupUserBeanDtos.get(0).getCamp());
           String str = JSON.toJSONString(responseDto);
           return JSON.toJSONString(responseDto);

    }
}
