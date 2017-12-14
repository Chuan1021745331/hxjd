package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JGroup;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.GroupQuery;
import com.base.service.app.AppHandle.AppBean.DevGroupBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;

import java.security.acl.Group;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainDevGroup
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得所属群组
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.OWN_GROUP_CODE)
public class GainDevGroup extends BaseHandleImpl {
    /**
     * 获得所属群组
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data){

        List<JGroup> groups = GroupQuery.me().getGroupByTerminal(data.getDevMac(),data.getSdMac());

        if(groups == null){
            return null;
        }
        List<DevGroupBeanDto> devGroupBeanDtos = new ArrayList<>();
        for(JGroup group : groups){
            DevGroupBeanDto devGroupBeanDto = new DevGroupBeanDto();
            
            //用GroupId
            devGroupBeanDto.setGroupId(group.getId());
            devGroupBeanDto.setGroupName(group.getGroupName());
            devGroupBeanDtos.add(devGroupBeanDto);

        }
          ResponseDto responseDto=new ResponseDto();
          responseDto.setHandle(2);
          responseDto.setCode(Integer.parseInt(RequestCodeConstants.OWN_GROUP_CODE));
          responseDto.setResponseStatus(1);
          SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
          responseDto.setTime(df.format(new Date()));
          responseDto.setData(devGroupBeanDtos);
          String str = JSON.toJSONString(responseDto);
          return JSON.toJSONString(responseDto);

    }
}
