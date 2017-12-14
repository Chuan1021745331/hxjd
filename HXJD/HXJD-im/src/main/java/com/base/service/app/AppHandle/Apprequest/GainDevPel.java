package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.model.JGroupmediator;
import com.base.model.JMediator;
import com.base.model.JPel;
import com.base.model.JSeatpelgroup;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.GroupMediatorQuery;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.PelQuery;
import com.base.service.SeatgroupQuery;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
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
 * @类名: GainDevPel
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 获得所属图元
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 9:54
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.DEV_PEL_CODE)
public class GainDevPel extends BaseHandleImpl {
    /**
     * 获得所属图元
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        String DevMac = data.getDevMac();
        String SdMac = data.getSdMac();
        
        JMediator meditoer =  MediatorQuery.me().getByTerminalMac(DevMac, SdMac);
        
        //JGroupmediator groupMediator =  GroupMediatorQuery.me().findByTerminal(SdMac);

        //int groupId = groupMediator.getGroupId();
		       
        //List<JPel> pels = PelQuery.me().getPelByTerminal(data.getDevMac(), data.getSdMac());//条件包含图元组
        
        //要把没有图元组的图元添加进去
        List<JPel> pels = new ArrayList<JPel>();
        List<JPel> pel1 = PelQuery.me().getMediatorGroupPel(meditoer.getId());
        List<JPel> pel2 = PelQuery.me().getUngroupPel(meditoer.getCamp());
        pels.addAll(pel1);
        pels.addAll(pel2);
          
        //List<JPel> pels = PelQuery.me().getPelByCamp(GroupQuery.me().findById(groupId).getCamp().toString());
        
/*        if(pels==null){

            return null;
        }*/
        System.out.println("=########################pel-size: " + pels.size());

        List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
        for(JPel pel:pels){
            Integer damage =  pel.getDamage();
        	
        	DevPelBeanDto devPelBeanDto=new DevPelBeanDto();
            devPelBeanDto.setId(pel.getId());
            devPelBeanDto.setCamp(pel.getCamp());
            devPelBeanDto.setCode(pel.getCode());
            devPelBeanDto.setDamage(pel.getDamage());
            devPelBeanDto.setOid(pel.getOid());
      
            devPelBeanDto.setLocation(pel.getLocation());
            devPelBeanDto.setY(pel.getY());
            devPelBeanDto.setX(pel.getX());
            devPelBeanDto.setState(pel.getState());
            devPelBeanDto.setPelName(pel.getPelname());
            devPelBeanDto.setShape(pel.getShape());
            devPelBeanDto.setType(pel.getType().toString());
            devPelBeanDtos.add(devPelBeanDto);
        }

        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
        responseDto.setResponseStatus(1);//2-新增 //3-更新
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(devPelBeanDtos);
        String str = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
