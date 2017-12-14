package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JTerminal;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DevInformationUpload
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 上传数据
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:53
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.DEV_INFORMATION_UPLOAD_CODE)
public class DevInformationUpload extends BaseHandleImpl {
    /**
     * 上传数据，x，y，power
     * @param data
     * @return
     */
    @Override
    @Before(Tx.class)
    public String init(RequestDto data) {
        JSONObject jsonObject= JSON.parseObject(data.getData().toString());
        String devPower=jsonObject.getString("devPower");
        String x=jsonObject.getString("x");
        String y=jsonObject.getString("y");
        JTerminal Qter=new JTerminal();
        JTerminal terminal=null;
        terminal=Qter.findFirst("select * from j_terminal where terminal_num=? and sdnum=?",data.getDevMac(),data.getSdMac());
        int isupdate=0;
        if(terminal!=null){
            terminal.setTerminalPower(devPower);
            boolean isup=terminal.update();
            if(!isup){
                return null;
            }
            isupdate=Db.update("update j_mediator jm set jm.x=? ,jm.y=? where jm.id=(select mediator_id from j_mediatorterminal where terminal_id=?)",x,y,terminal.getId());
        }else {
            return null;
        }
        if(isupdate!=0){
            ResponseDto responseDto=new ResponseDto();
            responseDto.setResponseStatus(1);
            responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_INFORMATION_UPLOAD_CODE));
            responseDto.setHandle(2);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            responseDto.setTime(df.format(new Date()));
            String str = JSON.toJSONString(responseDto);
            return JSON.toJSONString(responseDto);
        }
        return null;
    }
}
