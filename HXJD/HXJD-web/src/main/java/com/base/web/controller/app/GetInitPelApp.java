package com.base.web.controller.app;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.model.JGroupmediator;
import com.base.model.JPel;
import com.base.model.JTerminal;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.GroupMediatorQuery;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.PelQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.utils.GZipUtil;


/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: GetInitPelApp.java   
 * @包名: com.base.web.controller.app   
 * @描述: TODO(用一句话描述该文件做什么)   
 * @所属: 华夏九鼎     
 * @日期: 2017年10月17日 上午11:12:21   
 * @版本: V1.0 
 * @创建人：lgy 
 * @修改人：kevin
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/GetInitPelApp", viewPath = "")
@RouterNotAllowConvert
public class GetInitPelApp extends BaseController{
	
	public void getPel(){
	    String SdMac = getPara("SdMac");
	    
	    JTerminal terminal =  TerminalQuery.me().initializeTerminal(SdMac);
	    if(terminal==null){
	    	List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
	    	ResponseDto responseDto=new ResponseDto();
		    responseDto.setHandle(2);
		    responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
		    responseDto.setResponseStatus(1);//2-新增 //3-更新
		    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		    responseDto.setTime(df.format(new Date()));
		    responseDto.setData(devPelBeanDtos);
		    String str = JSON.toJSONString(responseDto);

		    renderText(str);
	    	return;
	    }
	    	    
	    JGroupmediator groupMediator = GroupMediatorQuery.me().findByTerminal(SdMac);

	    int groupId = groupMediator.getGroupId();
		       
	    //List<JPel> pels = PelQuery.me().getPelByTerminal(data.getDevMac(), data.getSdMac());
	      
	    List<JPel> pels = PelQuery.me().getPelByCamp(GroupQuery.me().findById(groupId).getCamp().toString());
	    if(pels==null){

	        return;
	    }
	    System.out.println("=########################pel-size: " + pels.size());

	    List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
	    for(JPel pel:pels){	    	
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

	    renderText(str);

			//return doZipMsg;

	    //return null;
	}
	
	
}
