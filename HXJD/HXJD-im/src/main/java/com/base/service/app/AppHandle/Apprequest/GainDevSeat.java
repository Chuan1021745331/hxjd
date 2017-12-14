package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.model.JMediator;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.MediatorQuery;
import com.base.service.app.AppHandle.AppBean.DevSeatBean;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainDevSeat
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获取当前用户席位信息
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.DEV_SEAT_CODE)
public class GainDevSeat extends BaseHandleImpl {
    /**
     * 获取当前用户席位信息
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        //Record record= Db.findFirst("SELECT js.id,js.seatname,js.directing,js.camp FROM j_terminal jt JOIN j_mediatorterminal jmt on jt.id=jmt.terminal_id JOIN j_mediator jm on jmt.mediator_id=jm.id JOIN j_medseat jms ON jm.id=jms.mediatorId JOIN j_seat js ON jms.seatId=js.id WHERE jt.terminal_num=? and jt.sdnum=?",data.getDevMac(),data.getSdMac());
        JMediator mediator = MediatorQuery.me().getByTerminalMac(data.getDevMac(),data.getSdMac());
        Record record = new Record();
        if(mediator != null){
        	record= Db.findFirst("SELECT js.id,js.seatname,js.directing,js.camp FROM j_mediator jm JOIN j_medseat jms ON jm.id=jms.mediatorId JOIN j_seat js ON jms.seatId=js.id JOIN j_drillcode dc ON dc.id = jm.drillId WHERE dc.state = 0 AND jm.id = ? ", mediator.getId());            
        }
                
    	if(record==null){
            return null;
        }
        DevSeatBean devSeatBean=new DevSeatBean();
        devSeatBean.setSeatName(record.getStr("seatname"));
        devSeatBean.setDirecting(record.getStr("directing"));
        devSeatBean.setCamp(record.getStr("camp"));
        devSeatBean.setId(record.getInt("id").toString());

        ResponseDto responseDto=new ResponseDto();
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_SEAT_CODE));
        responseDto.setHandle(2);
        responseDto.setResponseStatus(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(devSeatBean);
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
