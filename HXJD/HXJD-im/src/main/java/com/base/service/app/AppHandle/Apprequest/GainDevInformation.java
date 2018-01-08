package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.app.AppHandle.AppBean.DevInformationBean;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainDevInformation
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获取设备信息
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.DEV_CAPS_CODE)
public class GainDevInformation extends BaseHandleImpl {

    private final static Logger logger= LoggerFactory.getLogger(GainDevInformation.class);

    /**
     * 获取设备信息
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        logger.info(""+data.getCode());
        logger.info(""+data.getHandle());
        logger.info(""+data.getDevMac());

        Record record= Db.findFirst("SELECT jt.sdnum,jt.terminal_name,jt.terminal_power,jt.terminal_stauts,jm.mediator_name,jm.mediator_grade,jm.camp,jm.id FROM j_terminal jt JOIN j_mediatorterminal jmt on jt.id=jmt.terminal_id JOIN j_mediator jm on jmt.mediator_id=jm.id JOIN j_drillcode dc ON dc.id = jm.drillId WHERE dc.state = 0 and jt.terminal_num=? and jt.sdnum=?",data.getDevMac(),data.getSdMac());
        if(record==null){
            return null;
        }
        DevInformationBean devInformationBean=new DevInformationBean();

        devInformationBean.setSdNum(record.getStr("sdnum"));
        devInformationBean.setCamp(record.getStr("camp"));
        devInformationBean.setMediatorGrade(record.getStr("mediator_grade"));
        devInformationBean.setMediatorName(record.getStr("mediator_name"));
        devInformationBean.setTerminalName(record.getStr("terminal_name"));
        devInformationBean.setTerminalStauts(record.getStr("terminal_stauts"));
        devInformationBean.setMediatorId(record.getInt("id"));
        devInformationBean.setSdNum(data.getDevMac());

        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_CAPS_CODE));
        responseDto.setResponseStatus(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(devInformationBean);
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
