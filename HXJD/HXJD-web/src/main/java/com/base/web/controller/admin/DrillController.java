package com.base.web.controller.admin;

import java.util.List;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.client.IMClientStarter2;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JDrillcode;
import com.base.model.JGroupdrill;
import com.base.model.JSeat;
import com.base.model.JSeatdrill;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DrillQuery;
import com.base.service.DrillcodeQuery;
import com.base.service.GroupDrillQuery;
import com.base.service.MediatorQuery;
import com.base.service.PelGroupDrillQuery;
import com.base.service.PelQuery;
import com.base.service.SeatDrillQuery;
import com.base.service.SeatQuery;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: DrillController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 训练/演习Controller  
 * @所属: 华夏九鼎     
 * @日期: 2017年11月13日 上午10:30:54   
 * @版本: V1.0 
 * @创建人：lgy 
 * @修改人：kevin
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/drill", viewPath = "/view/admin/drill")
@RouterNotAllowConvert
public class DrillController extends BaseController {
	@Before(ButtonInterceptor.class)
	public void index(){
        render("drill.html");
    }
	public void drillData(){
		Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");

        List<JDrillcode> list = DrillcodeQuery.me().findList(start, length,column,order,search);
        long count = DrillcodeQuery.me().findConunt(search);
        renderPageResult(draw, (int)count, (int)count, list);
	}
	public void details() {
        Integer id = getParaToInt("id");
        JDrillcode drillcode = DrillcodeQuery.me().findById(id);
        setAttr("drillcode", drillcode);
        render("details.html");
    }
    public void add() {
    	List<JSeat> seatList = SeatQuery.me().getUnselectedSeat();
    	
    	setAttr("seatList", seatList);
    	render("add.html");
    }
    @Before(Tx.class)
    public void addSave() {
        JDrillcode jo = getModel(JDrillcode.class);
        if(jo.getState().intValue() == 0){//设定为开启
        	JDrillcode drill =  DrillcodeQuery.me().getActiveDrill();
            if(drill != null ){
            	renderAjaxResultForError("现有其他训练进行中！！！");
            	return;
            } else {
            	if(StringUtils.areNotEmpty(jo.getIp(),jo.getPort())){
            		//IMClientStart
            	}            	
            }
        }
        boolean a = jo.save();
        if(a){
            String[] seats = getParaValues("seat");
            if(seats!=null && seats.length > 0){
            	for (String seat : seats) {//
                	JSeatdrill seatDrill = new JSeatdrill();
                	seatDrill.setDrillId(jo.getId());
                	seatDrill.setSeatId(new Integer(seat));
                	seatDrill.saveOrUpdate();
    			}
            }
            
            for (int i = 1; i < 3; i++) {//添加训练时，自动添加红、蓝、导演部 基础阵营
				JGroupdrill gd = new JGroupdrill();
				gd.setDrillId(jo.getId());
				gd.setGroupId(i);
				gd.save();
			}
            
            
        	renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }
    public void edit() {
        Integer id = getParaToInt("id");
        JDrillcode drillcode = DrillcodeQuery.me().findById(id);
        setAttr("drillcode", drillcode);
        
        List<JSeat> seatList = SeatQuery.me().getAll();
        List<JSeat> selectedSeat = SeatQuery.me().getSelectedSeat(id);
        setAttr("seatList", seatList);
        setAttr("selectedSeat", selectedSeat);
        
        render("edit.html");
    }
    public void editSave() {
        JDrillcode jo = getModel(JDrillcode.class);
        
        if(StringUtils.isNotEmpty(jo.getIp())){
        	String ip = jo.getIp();
            String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
            		+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            		+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
            		+"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        	if(!ip.matches(regex)){//判断IP格式是否合法
            	renderAjaxResultForError("IP格式不正确！！！");
            	return;
            }
        }
        
        
        String[] seatIds = getParaValues("seatid");
        
        JDrillcode oldJo = DrillcodeQuery.me().findById(jo.getId());
        
        if(jo.getState() != null){
            if(jo.getState().intValue() != oldJo.getState().intValue()){//修改了状态 开启/关闭/重启IMClient
            	if(jo.getState().intValue()==0){//设定为开启
            		JDrillcode drill=  DrillcodeQuery.me().getActiveDrill();
                    if(drill != null ){
        				if(drill.getId() != oldJo.getId() || drill.getId().intValue() == 0){//不是对进行中的训练本身进行修改
        					renderAjaxResultForError("现有其他训练进行中！！！");
        		            return;
        				}        	
                    } 
            		
            		if(StringUtils.areNotEmpty(jo.getIp(),jo.getPort())){
            			IMClientStarter2 start = new IMClientStarter2();
            			//start.start();
            		}
            		
            	} else if(jo.getState().intValue()==1){//设定为关闭
            		//if(StringUtils.areNotEmpty(jo.getIp(),jo.getPort())){
            			//IMClientStop
            		//} 
            		IMClientStarter2 start = new IMClientStarter2();
            		//start.stop();
            	}
            }
        }
        
        
        boolean a = jo.update();
        if(a){
        	if(seatIds!=null && seatIds.length>0){
        		SeatDrillQuery.me().deleteByDrill(jo.getId());//删除旧的记录
        		for (String seatId : seatIds) {
        			JSeatdrill seatDrill = new JSeatdrill();
                	seatDrill.setDrillId(jo.getId());
                	seatDrill.setSeatId(new Integer(seatId));
                	seatDrill.saveOrUpdate();
				}
        	}
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    public void del() {
        //后续修改要删除中间表!!!!!!!!!!!!!!!!!!
    	Integer id = getParaToInt("id");
        JDrillcode drillcode = DrillcodeQuery.me().findById(id);
        boolean a = drillcode.delete(); 
        if(a){
        	SeatDrillQuery.me().deleteByDrill(id);//席位关联
        	PelGroupDrillQuery.me().deleteByDrillId(id);//图元组关联
        	
        	PelQuery.me().deleteByDrill(id);//关联图元
        	MediatorQuery.me().deleteByDrill(id);//关联调理员
        	
        	GroupDrillQuery.me().deleteByDrillId(id);//关联群组
        	
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
    }

}
