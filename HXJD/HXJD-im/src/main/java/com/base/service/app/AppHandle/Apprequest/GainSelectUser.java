package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JMediator;
import com.base.model.JTerminal;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.GroupMediatorQuery;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.app.AppHandle.AppBean.MediatorBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GainSelectUser
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得所选用户信息
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.USER_INFORMATION_CODE)
public class GainSelectUser extends BaseHandleImpl {
    /**
     * 获得所选用户信息
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        //JSONObject object=JSONObject.parseObject(data.getData().toString());
        String DevMac = data.getDevMac();
        String SdMac = data.getSdMac();
        
        int groupId = GroupMediatorQuery.me().findByTerminal(SdMac).getGroupId();
        
        List<Record> recordList = Db.find("SELECT * FROM j_mediator jm INNER JOIN j_mediatorterminal jmt ON jm.id=jmt.mediator_id INNER JOIN j_terminal jt ON jmt.terminal_id=jt.id INNER JOIN j_groupmediator gm on gm.mediatorId = jm.id where gm.groupId = ? AND jt.terminal_num !=? AND jt.sdnum!=? ",groupId, DevMac, SdMac);
        List<ResponseDto> resList = new ArrayList<ResponseDto>();
        for (Record record : recordList) {
        	MediatorBeanDto mediato=new MediatorBeanDto();
            mediato.setCamp(record.getStr("camp"));
            mediato.setMediatorGrade(record.getStr("mediator_grade"));
            mediato.setMediatorName(record.getStr("mediator_name"));
            mediato.setSdNum(record.getStr("sdnum"));
            mediato.setTerminalName(record.getStr("terminal_name"));
            mediato.setTerminalNum(record.getStr("terminal_num"));
            mediato.setTerminalStauts(record.getStr("terminal_stauts"));
            mediato.setX(record.getStr("x"));
            mediato.setY(record.getStr("y"));

            ResponseDto responseDto=new ResponseDto();
            responseDto.setHandle(2);
            responseDto.setCode(Integer.parseInt(RequestCodeConstants.USER_INFORMATION_CODE));
            responseDto.setResponseStatus(1);
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            responseDto.setTime(df.format(new Date()));
            responseDto.setData(mediato);
            resList.add(responseDto);
		}
        		
        		
//        Record record= Db.findFirst("SELECT * FROM j_mediator jm INNER JOIN j_mediatorterminal jmt ON jm.id=jmt.mediator_id INNER JOIN j_terminal jt ON jmt.terminal_id=jt.id where jt.terminal_num =? AND jt.sdnum=? ", DevMac, SdMac);
//        if(record==null){
//            return null;
//        }
//        MediatorBeanDto mediato=new MediatorBeanDto();
//        mediato.setCamp(record.getStr("camp"));
//        mediato.setMediatorGrade(record.getStr("mediator_grade"));
//        mediato.setMediatorName(record.getStr("mediator_name"));
//        mediato.setSdNum(record.getStr("sdnum"));
//        mediato.setTerminalName(record.getStr("terminal_name"));
//        mediato.setTerminalNum(record.getStr("terminal_num"));
//        mediato.setTerminalStauts(record.getStr("terminal_stauts"));
//        mediato.setX(record.getStr("x"));
//        mediato.setY(record.getStr("y"));
//
        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.USER_INFORMATION_CODE));
        responseDto.setResponseStatus(1);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(resList);
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
