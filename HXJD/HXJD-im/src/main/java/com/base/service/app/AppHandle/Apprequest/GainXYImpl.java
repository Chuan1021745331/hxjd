package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainXYImpl
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得经纬度
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:06
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.MAP_CENTER_POINT_CODE)
public class GainXYImpl extends BaseHandleImpl{
    @Override
    public String init(RequestDto data) {
        Record record= Db.findFirst("select jm.x,jm.y FROM j_terminal jt INNER JOIN j_mediatorterminal jmt ON jt.id=jmt.terminal_id INNER JOIN j_mediator jm ON jmt.mediator_id=jm.id where jt.terminal_num=? and jt.sdnum=?",data.getDevMac(),data.getSdMac());
        String str=null;
        if(record!=null){
            str="{x:"+record.getStr("x")+",y:"+record.getStr("y")+"}";
        }else {
            return null;
        }

        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.MAP_CENTER_POINT_CODE));
        responseDto.setResponseStatus(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(str);
        String str_ = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
