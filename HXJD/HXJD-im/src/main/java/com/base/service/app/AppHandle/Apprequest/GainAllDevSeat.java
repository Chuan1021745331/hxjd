package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.model.JSeat;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.SeatQuery;
import com.base.service.app.AppHandle.AppBean.DevSeatBean;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.jfinal.plugin.ehcache.CacheKit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainAllDevSeat
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得全部席位
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.DEV_ALL_SEAT_CODE)
public class GainAllDevSeat extends BaseHandleImpl {
    /**
     * 获得全部席位
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        List<DevSeatBean>dseats=CacheKit.get("seat","seats");

        if(dseats==null){
            dseats=new ArrayList<>();
            List<JSeat> seats= SeatQuery.me().getAll();
            if(seats==null){
                return null;
            }
            for(JSeat seat:seats){
                DevSeatBean devSeatBean=new DevSeatBean();
                devSeatBean.setCamp(seat.getCamp());
                devSeatBean.setDirecting(seat.getDirecting());
                devSeatBean.setSeatName(seat.getSeatname());
                dseats.add(devSeatBean);
            }
            CacheKit.put("seat","seats",dseats);
        }
        ResponseDto responseDto=new ResponseDto();
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_ALL_SEAT_CODE));
        responseDto.setResponseStatus(1);
        responseDto.setHandle(2);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(dseats);
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
