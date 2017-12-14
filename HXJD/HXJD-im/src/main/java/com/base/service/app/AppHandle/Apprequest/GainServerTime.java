package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainServerTime
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获取服务器时间
 * @所属: 华夏九鼎
 * @日期: 2017/5/31 17:08
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.SERVER_TIME_CODE)
public class GainServerTime extends BaseHandleImpl {
    //获取服务器时间
    @Override
    public String init(RequestDto data) {
        ResponseDto responseDto=new ResponseDto();
        responseDto.setResponseStatus(1);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.SERVER_TIME_CODE));
        responseDto.setHandle(2);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
//        context=Aio.getChannelContextByUserid(IMServerStarter.serverGroupContext,isInitialize.toString());
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
