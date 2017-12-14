package com.base.web.controller.admin;

import com.base.constants.MessageConstants;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.MsgUtil;
import com.base.im.common.PushUtil;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.base.service.app.AppHandle.AppBean.DevPelBeanDto;
import com.base.service.app.AppHandle.AppBean.DevSeatBean;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: pelController.java
 * @包名: com.base.web.controller.admin
 * @描述: 用户配置参数的相关操作
 * @所属: 华夏九鼎
 * @日期: 2017年5月15日 上午9:36:05
 * @版本: V1.0
 * @创建人：z
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/seat", viewPath = "/view/admin/seat")
@RouterNotAllowConvert
public class SeatController extends BaseController {

    @Before(ButtonInterceptor.class)
    public void index() {
        render("seat.html");
    }

    public void seatData() {
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        if(search.equals("红方")){
            search = "0";
        }
        else if (search.equals("蓝方")){
            search = "1";
        }
        else if (search.equals("随队")){
            search = "3";
        }
        else if (search.equals("区域")){
            search = "2";
        }
        List<Record> list = SeatQuery.me().findList(start, length,column,order,search);
        long count = SeatQuery.me().findConunt(search);
        renderPageResult(draw, (int)count, (int)count, list);
    }
    public void details() {
        Integer id = getParaToInt("id");
        JSeat seat = SeatQuery.me().findById(id);
        List<JPelgroup> JSeatpelgroup = PelGroupQuery.me().getGroupAll(id);
        //或者绑定的调理员
        //String seatName;
        List<JMediator> seatNameList = new ArrayList<JMediator>();
        List<JMediator> jm = MediatorQuery.me().getSeatMediator(id);
        if (!jm.isEmpty() && jm.size()>0){
            for (JMediator jMediator : jm) {
            	seatNameList.add(jMediator);
			}
        	
        	//seatName = jm.get(0).getMediatorName();
            //seatNameList
        }/*else {
            seatName = "暂未绑定";
        }*/
        
        JDrillcode drill = DrillcodeQuery.me().getDrillBySeatId(id);
        
        setAttr("seatNameList", seatNameList);
        setAttr("plegroupname", JSeatpelgroup);
        setAttr("seat", seat);
        setAttr("drill", drill);
        render("details.html");
    }
    public void add() {
        List<JPelgroup> pelgroups = PelGroupQuery.me().getByCurrentDrill();        
        List<JMediator> mediators = MediatorQuery.me().getByCurrentDrill();
        List<JMedseat> medseat = MeaSeatQuery.me().getAll();
        
        //遍历出已选的调理员
        boolean tags = false;
        List<JMediator> jeat =new ArrayList<>();
        for (JMediator mediator: mediators){
            for (JMedseat jMedseat: medseat){
                if (jMedseat.getMediatorId()==mediator.getId()){
                    jeat.add(mediator);
                    tags = true;
                }
            }
        }
        if(tags){
        	mediators.removeAll(jeat);
        }
        setAttr("mediators",mediators);
        setAttr("pelgroups", pelgroups);
        render("add.html");
    }
    @Before(Tx.class)
    public void addSave() {
        JSeat jo = getModel(JSeat.class);
        String[] groupId =getParaValues("groupId");
        Integer medId = getParaToInt("medId");
        
        JDrillcode drill = DrillcodeQuery.me().getActiveDrill();
        if(drill != null){
        	jo.setDrill(drill.getId());
        }
        //保存席位
        boolean a = jo.save();
        int seatId = jo.getId();
        //保存席位调理员关联
        JMedseat jm = getModel(JMedseat.class);
        if (medId != null && medId != 0){
        	jm.setSeatId(seatId);
            jm.setMediatorId(medId);
            jm.save();
        }
               
        //保存图元组-席位关联
        if (groupId != null && groupId.length > 0 ){
            for (int i = 0;i < groupId.length; i++){
                if (Integer.valueOf(groupId[i]) != 0){
                    JSeatpelgroup jpl = getModel(JSeatpelgroup.class);
                    jpl.setPelgroupId(Integer.valueOf(groupId[i]));
                    jpl.setSeatId(seatId);
                    boolean b = jpl.save();
                    if(b==false){
                        renderAjaxResultForSuccess(MessageConstants.ADD_DEFEAT);
                        return ;
                    }
                }
            }
        }
        if(a){
            PushSeat(jo,medId,medId,false);//推送
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }
    public void edit() {
        Integer id = getParaToInt("id");
        JSeat seat  = SeatQuery.me().findById(id);
        List<JPelgroup> pelgroups = PelGroupQuery.me().getByCurrentDrill();
        List<JPelgroup> pelgroupPelId = PelGroupQuery.me().getGroupAll(id);//对应的图元组
        //获取调理员
        List<JMediator> jm = MediatorQuery.me().getSeatMediator(id);//该席位对应的调理员    √席位已有训练判定   
        List<JMediator> mediators = MediatorQuery.me().getByCurrentDrill();//所有调理员
        List<JMedseat> medseat = MeaSeatQuery.me().getAll();//所有席位-调理员关联记录    
        //遍历出已选的调理员
        List<JMediator> jeat =new ArrayList<>();
        for (JMediator mediator: mediators){
            for (JMedseat jMedseat: medseat){
                if (jMedseat.getMediatorId()==mediator.getId()){
                    if (!jm.isEmpty()){
                        if (jMedseat.getMediatorId() == jm.get(0).getId()){}
                        else {
                            jeat.add(mediator);
                        }
                    }
                }
            }
        }
        mediators.removeAll(jeat);
        //选定未绑定的调理员
        
        setAttr("mediators",mediators);
        setAttr("jm",jm);//判断好像重复了???
        setAttr("pelgroupPelId",pelgroupPelId);
        setAttr("pelgroups", pelgroups);
        setAttr("seat", seat);
        
        render("edit.html");
    }
    @Before(Tx.class)
    public void editSave() {
        JSeat jo = getModel(JSeat.class);
//        Boolean c = SeatgroupQuery.me().delectByPelgroupId(jo.getId());
        String[] pelid =getParaValues("groupid");
        Integer medId = getParaToInt("medId");
        JMedseat medseat=MeaSeatQuery.me().GainMediatorId(jo.getId());
        int befor=-1;
        if(null != medseat){
        	 befor=medseat.getMediatorId();
        }        
        
        //修改绑定调理员
        MeaSeatQuery.me().delectByPelgroupId(jo.getId());
        if(medId != 0){
            JMedseat jm = getModel(JMedseat.class);
            jm.setSeatId(jo.getId());
            jm.setMediatorId(medId);
            jm.save();
        }
        //修改绑定图元组
        //若不变 则不修改
        if (pelid != null){
        	SeatgroupQuery.me().deleteBySeatId(jo.getId());//删除原来的
            for (int i = 0;i < pelid.length; i++){
                JSeatpelgroup jpl = getModel(JSeatpelgroup.class);
                jpl.setPelgroupId(Integer.valueOf(pelid[i]));
                jpl.setSeatId(jo.getId());
                boolean b = jpl.save();
                if(b==false){
                    renderAjaxResultForSuccess(MessageConstants.EDIT_DEFEAT);
                    return ;
                }
            }
        }
        boolean a = jo.update();
        if(a){
        	if(befor!=-1){
        		PushSeat(jo,befor,medId,false);//推送
        	}
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);

    }
    @Before(Tx.class)
    public void del() {
        Integer id = getParaToInt("id");
        List<JMedseat> medseats = MeaSeatQuery.me().seatIsExist(id);
        JMedseat medseat=MeaSeatQuery.me().GainMediatorId(id);
        int befor=medseat.getMediatorId();
        
        JSeatdrill seatDrill = SeatDrillQuery.me().getBySeatId(id);                  
        
        if(medseats.isEmpty()){
            SeatgroupQuery.me().delectByPelgroupId(id);
            JSeat jseat = SeatQuery.me().findById(id);
            boolean a = jseat.delete();
            if(a){
                PushSeat(null,befor,befor,true);//推送
                renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
                return;
            }
            renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
        }else {
            renderAjaxResultForError(MessageConstants.DEL_RELIEVE_MEA);
        }


    }

    private void PushSeat(JSeat seat,int befor,int after,boolean isdel) {
        //调理员没改变
        if (befor == after) {
            //推送图元
            PushUtil.pushPel(PushRespons(seat,isdel,befor));
        } else {
            PushUtil.pushPel(PushRespons(seat,true,befor));//推送图元
            //推送图元
            PushUtil.pushPel(PushRespons(seat,false,after));
        }
    }
    //推送席位
    private JTerminal PushRespons(JSeat seat,boolean isNull,int num){
        JTerminal terminal = TerminalQuery.me().getByMediator(num);
        if(null !=terminal){
        	ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
        	 if (context != null) {
                 ResponseDto responseDto = new ResponseDto();
                 if(!isNull){
                     DevSeatBean devSeatBean = new DevSeatBean();
                     devSeatBean.setSeatName(seat.getSeatname());
                     devSeatBean.setDirecting(seat.getDirecting());
                     devSeatBean.setCamp(seat.getCamp());
                     devSeatBean.setId(seat.getId().toString());
                     responseDto.setData(devSeatBean);
                 }else {
                     responseDto.setData(null);
                 }
                 responseDto.setCode(RequestCodeConstants.PUSH_SEAT_CODE);
                 responseDto.setHandle(2);
                 responseDto.setResponseStatus(1);
                 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                 responseDto.setTime(df.format(new Date()));

                 MsgUtil.Send(context, responseDto);
        	 }
        }
        return terminal;
    }
}
