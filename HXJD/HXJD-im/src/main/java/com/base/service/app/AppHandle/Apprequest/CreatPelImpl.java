package com.base.service.app.AppHandle.Apprequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.model.JDrillcode;
import com.base.model.JMediator;
import com.base.model.JPel;
import com.base.model.JTerminal;
import com.base.model.dto.RequestDto;
import com.base.model.dto.ResponseDto;
import com.base.service.DrillcodeQuery;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppInterface.BaseHandleImpl;
import com.base.service.app.AppInterface.RequestCodeMaping;
import com.base.utils.GZipUtil;
import com.base.utils.StringUtils;
import com.jfinal.kit.PathKit;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CreatPelImpl}
 * @包名: com.base.service.app.AppHandle.Apprequest
 * @描述: 创建新的图元
 * @所属: 华夏九鼎
 * @日期: 2017/5/31 17:14
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RequestCodeMaping(requestCode = RequestCodeConstants.NEW_PEL_CODE)
public class CreatPelImpl extends BaseHandleImpl {
    /**
     * 创建新的图元
     * @param data
     * @return
     */
    @Override
    public String init(RequestDto data) {
        JSONObject object=JSONObject.parseObject(data.getData().toString());
        JPel pel=new JPel();
        pel.setCamp(object.getString("camp"));
        pel.setCode(object.getString("code"));
        pel.setDamage(object.getInteger("damage"));
        pel.setLocation(object.getString("direction"));
        pel.setPelname(object.getString("pelName"));
        pel.setX(object.getString("axesX"));
        pel.setY(object.getString("axesY"));
        pel.setState(1);
        pel.setShape(object.getString("shape"));
        pel.setOid(object.getString("oid"));
        pel.setCode(DateTime.now().toString("yyyyMMddhhmmss"+new Random().nextInt(9999)));
        pel.setType(new Integer(1));
        if(object.getString("oid")==null){
        	pel.setOid("");
        } else {
        	pel.setOid(object.getString("oid"));
        }
        
        JDrillcode drill = DrillcodeQuery.me().getActiveDrill();
        if(drill != null){
        	pel.setDrill(drill.getId());
        }
        
        boolean issave=pel.save();

        //下发
        List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
		
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
       
        ResponseDto responseDto_=new ResponseDto();
        responseDto_.setHandle(2);
        responseDto_.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
        responseDto_.setResponseStatus(2);//图元新增
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto_.setTime(df.format(new Date()));
        responseDto_.setData(devPelBeanDtos);
        String str = JSON.toJSONString(responseDto_);
        String respon = JSON.toJSONString(responseDto_);
        
        try {
        	IMPacket imPacket = new IMPacket();
            byte[] doZipMsg = GZipUtil.doZip(respon);
            imPacket.setBody(doZipMsg);
             
            String camp = pel.getCamp();
    		if(StringUtils.isNotEmpty(camp)){
    			List<JMediator> medList = MediatorQuery.me().getMediatorByGroupId(GroupQuery.me().getGroupByCamp(Integer.valueOf(camp)).getId());
    			for (JMediator jMediator : medList) {
    				JTerminal terminal = TerminalQuery.me().getByMediator(jMediator.getId());
    				if(terminal!=null){
    					ChannelContext ChannelContext =  (ChannelContext)IMcacheMap.cacheMap.get(TerminalQuery.me().getByMediator(jMediator.getId()).getSdnum());
        				if(null!=ChannelContext){
        					Aio.sendToGroup(ChannelContext.getGroupContext(), camp, imPacket);
        					break;
        				}
    				}
    				
    			}
    		}
            
            
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("新增图元终端下发出错了!!!");
			e.printStackTrace();
		}
        
        
        
        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.NEW_PEL_CODE));
        responseDto.setResponseStatus(0);
        if(issave){
            responseDto.setResponseStatus(2);
        }else {
            return null;
        }

        responseDto.setTime(df.format(new Date()));
        String str_ = JSON.toJSONString(responseDto);
        return JSON.toJSONString(responseDto);
    }
}
