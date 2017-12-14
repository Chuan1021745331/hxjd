package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JPel;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.PelQuery;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainSelectDevPel
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得所选图元
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.PEL_INFORMATION_CODE)
public class GainSelectDevPel extends BaseHandleImpl {
    /**
     * 获得所选图元
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        JSONObject object=JSONObject.parseObject(data.getData().toString());
        JPel pel= PelQuery.me().findById(object.getInteger("pelId"));
        if(pel==null){
            return null;
        }
        DevPelBeanDto devPelBeanDto=new DevPelBeanDto();
        devPelBeanDto.setCamp(pel.getCamp());
        devPelBeanDto.setCode(pel.getCode());
        devPelBeanDto.setDamage(pel.getDamage());
        devPelBeanDto.setLocation(pel.getLocation());
        devPelBeanDto.setY(pel.getY());
        devPelBeanDto.setX(pel.getX());
        devPelBeanDto.setState(pel.getState());
        devPelBeanDto.setPelName(pel.getPelname());

        ResponseDto responseDto=new ResponseDto();
        responseDto.setResponseStatus(1);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.PEL_INFORMATION_CODE));
        responseDto.setHandle(2);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(devPelBeanDto);
//        context=Aio.getChannelContextByUserid(IMServerStarter.serverGroupContext,isInitialize.toString());
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
