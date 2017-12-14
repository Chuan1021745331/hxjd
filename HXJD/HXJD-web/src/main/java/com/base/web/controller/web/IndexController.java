package com.base.web.controller.web;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DrillcodeQuery;
import com.base.service.GroupQuery;
import com.base.service.MarkQuery;
import com.base.service.MediatorQuery;
import com.base.service.MesssageAllQuery;
import com.base.service.OffUserQuery;
import com.base.service.OptionQuery;
import com.base.service.PelGroupQuery;
import com.base.service.PelQuery;
import com.base.service.RecInstructionQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.DevInstructionBeanDto;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.utils.AttachmentUtils;
import com.base.utils.GZipUtil;
import com.base.utils.StringUtils;
import com.base.web.controller.admin.DrillcodeController;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.joda.time.DateTime;
import org.tio.client.ClientChannelContext;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.model.JDrillcode;
import com.base.model.JInstruction;
import com.base.model.JMark;
import com.base.model.JMediator;
import com.base.model.JMessageall;
import com.base.model.JMsg;
import com.base.model.JOffuser;
import com.base.model.JOption;
import com.base.model.JPel;
import com.base.model.JPelgroup;
import com.base.model.JPelgrouppel;
import com.base.model.JRecinstruction;
import com.base.model.JTerminal;
import com.base.model.dto.ResponseDto;
/**
 * 首页
 * @author z
 *
 */
@RouterMapping(url = "/", viewPath = "/view/web")
@RouterNotAllowConvert
public class IndexController extends BaseController {

	public void index() {
		List<JOption> op = OptionQuery.me().getAll();
		for (JOption j:op) {
			if(j.getOptionKey().equals("map_center")){
				String[] ov = j.getOptionValue().split("\\|");
				setAttr("coordsX", ov[0]);
				setAttr("coordsY", ov[1]);
			}
			if(j.getOptionKey().equals("fightTime")){
				setAttr("fightTime", j.getOptionValue());
			}
		}
		
		List<JPelgroup> pelGroupList =  PelGroupQuery.me().getAll();
		setAttr("pelGroupList", pelGroupList);
		
		JDrillcode drill =  DrillcodeQuery.me().getActiveDrill();
		setAttr("drill", drill);
		
		render("index.html");
	}
	
	public void getMapAll(){
		List<JPel> pelList = PelQuery.me().getByCurrentDrill();
		List<JMark> makList = MarkQuery.me().getMarkAll();
		List<JMediator> medList = MediatorQuery.me().getByCurrentDrill();
		
		List<JMediator> onlineMedList = new ArrayList<JMediator>();
		for (JMediator jMediator : medList) {
			JTerminal terminal = TerminalQuery.me().getByMediator(jMediator.getId());
			if(terminal!=null){
				ChannelContext ChannelContext = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
				if(ChannelContext!=null){//在线的
					onlineMedList.add(jMediator);
				}
			}
		}
		
		
		PelQuery.me().updataPelState();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pel", pelList);
		map.put("mak", makList);
		map.put("med", medList);
		map.put("onlineMedList", onlineMedList);
		
		renderJson(map);
	}
	
	public void findChange(){
//		List<JPel> list = PelQuery.jPelList;
		Long pNum = PelQuery.me().findCountAll();
		List<JPel> l =PelQuery.me().getAllState();
		for (JPel l_:l) {
			l_.setState(0);
			l_.update();
		}
		
//		List<JPel> l = new ArrayList<JPel>();
//		l.addAll(list);
//		PelQuery.jPelList.clear();
		List<JMediator> med = MediatorQuery.me().getByCurrentDrill();
		
		List<JMediator> onlineMedList = new ArrayList<JMediator>();
		for (JMediator jMediator : med) {
			JTerminal terminal = TerminalQuery.me().getByMediator(jMediator.getId());
			if(terminal!=null){
				ChannelContext ChannelContext = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
				if(ChannelContext!=null){//在线的
					onlineMedList.add(jMediator);
				}
			}
		}

		Long mNum = MediatorQuery.me().findConunt(null);
		
		
		
		Map<String, Object> map = new HashMap<>();
		map.put("pel", l);
		map.put("med", med);
		map.put("pNum", pNum);
		map.put("mNum", mNum);
		map.put("oNum", onlineMedList.size());
		map.put("onlineMedList", onlineMedList);
		
		renderJson(map);
	}
	public void findChangeMed(){
		List<JMediator> med = MediatorQuery.me().getByCurrentDrill();
//		List<JMediator> cMed = CacheKit.get("mediator", "cacheJMediator");
//		List<JMediator> newMed = new ArrayList<JMediator>();
//		if(cMed.size()>0){
//			
//		}else{
//			newMed.addAll(med);
//		}
//		CacheKit.put("mediator", "cacheJMediator", newMed);
		
		renderJson(med);
	}
	@Before(Tx.class)
	public void addPel(){
		String temp = getPara("data");
		JPel newPel = JSON.parseObject(temp, JPel.class);
		
		JDrillcode drill = DrillcodeQuery.me().getActiveDrill();
		if(drill != null){
			newPel.setDrill(drill.getId());
		} else {
			renderJson(1);//无法添加！！当前无进行中的训练！！
			return;
		}
		
		newPel.setCode(DateTime.now().toString("yyyyMMddhhmmss"+new Random().nextInt(9999)));
		newPel.setLocation("0.000000");
		if(StringUtils.isBlank(newPel.getShape())){
			newPel.setShape("0");
		}
		
	
		newPel.setType(new Integer(1));
		newPel.setState(1);
		newPel.saveOrUpdate();
		
		Integer pelGroup = getParaToInt("pelGroup");
		if(pelGroup != null){//图元-图元组 中间表			
			JPelgrouppel pelgrouppel = new JPelgrouppel();
			pelgrouppel.setPelId(newPel.getId());
			pelgrouppel.setPelgroupId(pelGroup);
			pelgrouppel.saveOrUpdate();
		}
		
		//新增图元下发终端
		List<DevPelBeanDto>devPelBeanDtos=new ArrayList<>();
		
		DevPelBeanDto devPelBeanDto=new DevPelBeanDto();
        devPelBeanDto.setId(newPel.getId());
        devPelBeanDto.setCamp(newPel.getCamp());
        devPelBeanDto.setCode(newPel.getCode());
        devPelBeanDto.setDamage(newPel.getDamage());
  
        
        devPelBeanDto.setOid(newPel.getOid());
        devPelBeanDto.setLocation(newPel.getLocation());
        devPelBeanDto.setY(newPel.getY());
        devPelBeanDto.setX(newPel.getX());
        devPelBeanDto.setState(newPel.getState());
        devPelBeanDto.setPelName(newPel.getPelname());
        devPelBeanDto.setShape(newPel.getShape());
        devPelBeanDto.setType(newPel.getType().toString());
        devPelBeanDtos.add(devPelBeanDto);
       
        ResponseDto responseDto=new ResponseDto();
        responseDto.setHandle(2);
        responseDto.setCode(Integer.parseInt(RequestCodeConstants.DEV_PEL_CODE));
        responseDto.setResponseStatus(2);//图元新增
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        responseDto.setTime(df.format(new Date()));
        responseDto.setData(devPelBeanDtos);
        String str = JSON.toJSONString(responseDto);
        String respon = JSON.toJSONString(responseDto);
        
        try {
        	IMPacket imPacket = new IMPacket();
            byte[] doZipMsg = GZipUtil.doZip(respon);
            imPacket.setBody(doZipMsg);
            String camp = newPel.getCamp();
    		if(StringUtils.isNotEmpty(camp)){
    			List<JMediator> medList = MediatorQuery.me().getMediatorByGroupId(GroupQuery.me().getGroupByCamp(Integer.valueOf(camp)).getId());
    			for (JMediator jMediator : medList) {    				
    				if(jMediator!=null){
    					JTerminal terminal = TerminalQuery.me().getByMediator(jMediator.getId());
    					if(terminal!=null){
    						ChannelContext ChannelContext = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
            				if(null!=ChannelContext){
            					if(Aio.getChannelContextsByGroup(ChannelContext.getGroupContext(), camp)!=null){
            						Aio.sendToGroup(ChannelContext.getGroupContext(), camp, imPacket);
            						break;
            					}	
            				}
    					}	
    				}
    			}
    		}
            
            
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("新增图元终端下发出错了!!!");
			e.printStackTrace();
		}
        
		
		renderJson("0");
	}
	public void findMessage(){
		//JMessageall message = new JMessageall();
		List<JMessageall> redList = MesssageAllQuery.me().findByCamp("0");
		List<JMessageall> blueList = MesssageAllQuery.me().findByCamp("1");
		if(redList!=null && redList.size()>0){
			int i=0;
		}
		if(blueList!=null && blueList.size()>0){
			int i=0;
		}
		Map<String, List<JMessageall>> resultMap = new HashMap<>();
		resultMap.put("red", redList);
		resultMap.put("blue", blueList);
		
		for (JMessageall jMessageall : redList) {
			jMessageall.setState(0);
			jMessageall.update();
		}
		
		for (JMessageall jMessageall : blueList) {
			jMessageall.setState(0);
			jMessageall.update();
		}
		renderJson(resultMap);
	}
	public void findRaadMessage(){
		List<JMessageall> redHisList = MesssageAllQuery.me().findReadByCamp("0");
		List<JMessageall> blueHisList = MesssageAllQuery.me().findReadByCamp("1");
		Map<String, List<JMessageall>> resultMap = new HashMap<>();
		resultMap.put("redHis", redHisList);
		resultMap.put("blueHis", blueHisList);
		renderJson(resultMap);
	}
	
	//文件类型
	private static String[] video = {"mp4","rmvb","mkv","mov","avi","3gp","wmv","flv","f4v","ts","vob","m2ts"};
	private static String[] audio = {"mp3","amr","wav","flac","ape","aac"};
	private static String[] picture = {"jpg","png","bmp","gif","tif","jpeg","ico"};
	
	public void webChat(){	
		String msg;
		JMessageall message = new JMessageall();
		String ss = getPara("message");
		if(StringUtils.isNotEmpty(getPara("message"))){
			msg = getPara("message");
        	message.setMsgType("0");
        } else {
    		UploadFile upfile=this.getFile();
                String fileName = upfile.getOriginalFileName();
                String fileType = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
                String typePath = "";
    			for (String str : video) {
    				if(str.equalsIgnoreCase(fileType)){
    					message.setMsgType("2");
    					typePath="video";
    				}
    			}
    			for (String str : audio) {
    				if(str.equalsIgnoreCase(fileType)){
    					message.setMsgType("3");
    					typePath="audio";
    				}
    			}
    			for (String str : picture) {
    				if(str.equalsIgnoreCase(fileType)){
    					message.setMsgType("1");
    					typePath="image";
    				}
    			}
    			if(!StringUtils.isNotEmpty(message.getMsgType())){
    				message.setMsgType("4");
    				typePath="document";
    			}
    			String prefix = JFinal.me().getServletContext().getRealPath("/attachment/")+"/"+typePath;
    			
                //String str3 = upfile.getParameterName();//file
                //String str4 = upfile.getUploadPath();//D:\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp2\wtpwebapps\HHT-web\attachment
    			String whatPath=AttachmentUtils.moveFile(upfile,typePath);
                msg = whatPath;
            }
		String camp = getPara("camp");
		
		int groupId = GroupQuery.me().getGroupByCamp(Integer.parseInt(camp)).getId();
		
		//String temp = getPara("data");
		//JSONObject object = JSONObject.parseObject(temp);
		//String msg = object.getString("messsage");
		//String camp = object.getString("camp");
		
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//保存到所有信息中

		message.setSenderId(new Integer(-1));
		message.setCamp(camp);
		message.setMessage(msg);		
		message.setSendName("导演部");
		message.setSendTime(df.format(new Date()));
		message.setState(1);
		message.saveOrUpdate();
		
        DevInstructionBeanDto devInstructionBeanDto = new DevInstructionBeanDto();
        devInstructionBeanDto.setInstructionType(message.getMsgType());
        devInstructionBeanDto.setSenderId(message.getSenderId());
        devInstructionBeanDto.setSenderName(message.getSendName());
        devInstructionBeanDto.setCamp(2);//全部
        devInstructionBeanDto.setMsg(message.getMessage());
        devInstructionBeanDto.setSendTime(message.getSendTime());
		
        
        List<JMediator> mediators = MediatorQuery.me().getMediatorByGroupId(GroupQuery.me().getGroupByCamp(Integer.parseInt(camp)).getId());
		
        boolean isoff = false;
        boolean isffo = false;        
		
        JMsg jMsg = new JMsg();
        JInstruction jInstruction = new JInstruction();
        jMsg.setSendName(devInstructionBeanDto.getSenderName());
        jMsg.setMessage(devInstructionBeanDto.getMsg());
        jMsg.setSenderId(devInstructionBeanDto.getSenderId());
        jMsg.setMsgType(devInstructionBeanDto.getInstructionType());
        jMsg.setSendTime(devInstructionBeanDto.getSendTime());
               
        if (mediators != null){
            for (JMediator mediator : mediators) {                
                JTerminal terminal = TerminalQuery.me().getByMediator(mediator.getId());
                ChannelContext context = null;
                if (terminal != null) {
                    context = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
                }

                jInstruction.setSenderId(devInstructionBeanDto.getSenderId());
                jInstruction.setSenderName(devInstructionBeanDto.getSenderName());
                jInstruction.setMessage(devInstructionBeanDto.getMsg());
                jInstruction.setInstructionType(devInstructionBeanDto.getInstructionType());
                jInstruction.setSendTime(devInstructionBeanDto.getSendTime());
                if (!isffo) {
                    jInstruction.save();
                    isffo = true;
                }

                     JRecinstruction jRecinstruction = new JRecinstruction();
                     jRecinstruction.setMediatorId(mediator.getId());
                     jRecinstruction.setMediatorName(mediator.getMediatorName());
                     jRecinstruction.setMsgId(jInstruction.getId().toString());
                     RecInstructionQuery.me().ReciviceSave(jRecinstruction);

            //如果群友在线，将文件地址变为responseDto发给他
            if(context!=null){
            	MessageBean messageBean = new MessageBean();
        		messageBean.setGroupId(1);//Integer.parseInt(groupId)
        		messageBean.setMsg(message.getMessage());
        		messageBean.setSendTime(message.getSendTime());
        		messageBean.setMsgType(message.getMsgType());
        		messageBean.setSendName(message.getSendName());
        		messageBean.setSender(message.getSenderId().toString());
            	
            	ResponseDto responseDto = new ResponseDto();
                responseDto.setData(messageBean);
        		responseDto.setCode(13);
        		responseDto.setTime(df.format(new Date()));
                com.base.im.common.IMPacket packet = new com.base.im.common.IMPacket();
                responseDto.setData(messageBean);
                String respo = JSON.toJSONString(responseDto);
                byte[] doZipMsg = new byte[0];
                try {
                    doZipMsg = GZipUtil.doZip(respo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                packet.setBody(doZipMsg);
                Aio.send(context,packet);
            } else {
                //离线消息             

                if(!isoff){
                    jMsg.save();
                    isoff = true;
                }
                JOffuser offuser=new JOffuser();
                offuser.setGroupId(1);//用groupId，不用camp
                offuser.setMsgId(jMsg.getId().toString());
                offuser.setMediatorId(mediator.getId());
                OffUserQuery.me().updateAndSave(offuser);
            }
        }
	}
    	renderJson("0");
	}
	
	
	public void getOnlineMediator(){
		List<JMediator> med = MediatorQuery.me().getByCurrentDrill();
		List<Map> onlineMedList = new ArrayList<Map>();
		
		for (JMediator jMediator : med) {
			JTerminal terminal = TerminalQuery.me().getByMediator(jMediator.getId());
			if(terminal!=null){
				ChannelContext ChannelContext = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
				if(ChannelContext!=null){//在线的
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("mediator", jMediator);
					map.put("terminal", terminal);
					onlineMedList.add(map);
				}
			}
		}
		renderJson(onlineMedList);
	}
	
	public void getSelectedDevInfo(){
		String mediatorId = getPara("mediatorId");
		JMediator mediator = MediatorQuery.me().findById(new Integer(mediatorId));
		JTerminal terminal = TerminalQuery.me().getByMediator(Integer.parseInt(mediatorId));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("mediator", mediator);
		map.put("terminal", terminal);
		renderJson(map);
		
	}

/*	*//**
	 * 
	 * beforeAdd  
	 * (实验性质)add前置条件   
	 *void  
	 * @exception   
	 * @since  1.0.0
	 *//*
	
	public int beforeAdd(){
		JDrillcode drill =  DrillcodeQuery.me().getActiveDrill();
		if(drill!=null){
			return 0;
		}else {
			return 1;
		}
		
	}*/
	
}
