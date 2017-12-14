package com.base.im.common;

import com.base.constants.RequestCodeConstants;
import com.base.im.IMcacheMap;
import com.base.model.JPel;
import com.base.model.JTerminal;
import com.base.model.dto.ResponseDto;
import com.base.service.PelQuery;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: PushUtil
 * @包名: com.base.im.common
 * @描述: 推送工具类
 * @所属: 华夏九鼎
 * @日期: 2017/6/15 16:30
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class PushUtil {
    /**
     * 推送图元
     * @param terminal
     */
    public static void pushPel(JTerminal terminal){
       if(terminal!=null){
    	   ChannelContext context= (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
           if(context!=null){
               List<JPel> pels= PelQuery.me().GainPelByJTera(terminal.getId());

               List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
               if(pels!=null){
	               for(JPel pel:pels){
	                   DevPelBeanDto devPelBeanDto=new DevPelBeanDto();
	                   devPelBeanDto.setId(pel.getId());
	                   devPelBeanDto.setCamp(pel.getCamp());
	                   devPelBeanDto.setCode(pel.getCode());
	                   devPelBeanDto.setDamage(pel.getDamage());
	                   devPelBeanDto.setLocation(pel.getLocation());
	                   devPelBeanDto.setY(pel.getY());
	                   devPelBeanDto.setX(pel.getX());
	                   devPelBeanDto.setState(pel.getState());
	                   devPelBeanDto.setPelName(pel.getPelname());
	                   devPelBeanDto.setShape(pel.getShape());
	                   devPelBeanDtos.add(devPelBeanDto);
	               }
               }
               ResponseDto responseDto=new ResponseDto();
               responseDto.setHandle(2);
               responseDto.setCode(RequestCodeConstants.PUSH_PEL_CODE);
               responseDto.setResponseStatus(1);
               SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
               responseDto.setTime(df.format(new Date()));
               if(pels==null){
                   responseDto.setData(null);
               }else {
                   responseDto.setData(devPelBeanDtos);
               }


               MsgUtil.Send(context,responseDto);
           }
    	   //System.out.println();
       }
    	
    }
}
