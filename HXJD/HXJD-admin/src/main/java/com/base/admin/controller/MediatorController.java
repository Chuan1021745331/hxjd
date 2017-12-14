package com.base.admin.controller;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.router.RouterMapping;
import com.base.service.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: MediatorController
 * @包名: com.base.web.controller.admin
 * @描述: 调理员管理
 * @所属: 华夏九鼎
 * @日期: 2017/6/2 11.15
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/mediator", viewPath = "/view/admin/facility/")
public class MediatorController extends BaseController {
    @Before(ButtonInterceptor.class)
    public void index(){
        render("mediatoroption.html");
    }

    /**
     * 分页查询
     */
    public void optionData(){
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        List<Record> list = MediatorQuery.me().findList(start, length,column,order,search);
        long count = MediatorQuery.me().findConunt(search);
        renderPageResult(draw, (int)count, (int)count, list);
    }
    /**
     * 进入添加界面
     */
    public void add() {
        List<JTerminal>jTerminals=TerminalQuery.me().getAllTerminal();
        List<JMediatorterminal> jMediatorTerminals= MediatorTerminalQuery.me().getAll();
        List<JTerminal>jTerminalList=new ArrayList<>();
        List<JSeat> jeat =new ArrayList<>();
        List<JSeat> seats = SeatQuery.me().getAll();
        List<JMedseat> medseat = MeaSeatQuery.me().getAll();
        //遍历出已选的席位
        boolean tags = false;
        for (JSeat jSeat: seats){
            for (JMedseat jMedseat: medseat){
                if (jMedseat.getSeatId()==jSeat.getId()){
                    jeat.add(jSeat);
                    tags = true;
                }
            }
        }
        if(tags){
            seats.removeAll(jeat);
        }

        boolean tag=false;
        //遍历所有的终端以及所有已经配备的终端，从所有终端中将已经配备的分离出来，装进jTerminalList
        if(jMediatorTerminals!=null&&jTerminals!=null){
            for(JMediatorterminal jMediatorTerminal:jMediatorTerminals){
                for(JTerminal jTerminal:jTerminals){
                    if(jMediatorTerminal.get("terminal_id")==jTerminal.getId()){
                        jTerminalList.add(jTerminal);
                        tag=true;
                    }
                }
            }
        }else {
            //没有值
        }
        //如果有已经配备的终端就将配备的remove
        if(tag){
            jTerminals.removeAll(jTerminalList);
        }

        //群组
        List<JGroup>groups=GroupQuery.me().getAllGroup();
        setAttr("groups",groups);
        setAttr("seats",seats);
        setAttr("jTerminals",jTerminals);
        render("mediatoradd.html");
    }

    /**
     * 添加数据到数据库
     */
    @Before(Tx.class)
    public void addSave() {
        JMediator jm = getModel(JMediator.class);
        int terminalId=getParaToInt("terminalId");
        //获取席位ID
        Integer seatId = getParaToInt("seatId");
//        System.out.println("/admin/mediator-----2----->addSave=");
        boolean ajm=false;
        boolean ame=false;
        boolean ab = false;
        if(jm!=null){
            ajm= jm.save();
            JGroupmediator groupmediator=new JGroupmediator();
            if("1".equals(jm.getCamp())){
                groupmediator.setGroupId(3);
            }else {
                groupmediator.setGroupId(2);
            }
            groupmediator.setMediatorId(jm.getId());
            groupmediator.save();//保存到基本群组
            groupmediator=new JGroupmediator();
            groupmediator.setMediatorId(jm.getId());
            groupmediator.setGroupId(1);
            groupmediator.save();//保存到所有基础群组
            //保存到所选群组
            Integer[]ids=getParaValuesToInt("groupId");
            if(ids!=null){
	            for(int i:ids){
	                groupmediator=new JGroupmediator();
	                groupmediator.setMediatorId(jm.getId());
	                groupmediator.setGroupId(i);
	                groupmediator.save();
	            }
            }
            if(terminalId!=0){
                JMediatorterminal mediatorTerminal=new JMediatorterminal();
                mediatorTerminal.setMediatorId(jm.getId());
                mediatorTerminal.setTerminalId(terminalId);
                ame=mediatorTerminal.save();
                CacheKit.put("mediatorterminal",mediatorTerminal.getId(),mediatorTerminal);
            }
        }
        if (seatId!=0){
            //获取调理员管理ID
            int medId = jm.getId();
            JMedseat jMedseat = getModel(JMedseat.class);
            jMedseat.setSeatId(seatId);
            jMedseat.setMediatorId(medId);
            ab = jMedseat.save();
        }

        if(ajm){
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }
    /**
     * 进入详情界面
     */
    public void details() {
        Integer id = getParaToInt("id");
        String seatname;
        if (!SeatQuery.me().getSeatAll(id).isEmpty()){
            seatname = SeatQuery.me().getSeatAll(id).get(0).getSeatname();
        }else {
            seatname = "未选择席位";
        }
        JMediator mediator = MediatorQuery.me().findById(id);
        JMediatorterminal mediatorterminal=MediatorTerminalQuery.me().findByMediatorId(id);
        JTerminal terminal=null;

        if(mediatorterminal!=null){
            terminal=TerminalQuery.me().findById(mediatorterminal.getTerminalId());

        }
        List<JGroup>groups=GroupQuery.me().getGroupByMedorId(id);
        setAttr("groups",groups);//匹配的群组
        setAttr("seatname",seatname);
        setAttr("terminal",terminal);
        setAttr("mediator", mediator);
        render("mediatordetail.html");
    }
    /**
     * 进入到更新界面
     */
    public void edit() {
        Integer id = getParaToInt("id");
        JMediator jMediator = MediatorQuery.me().findById(id);
        List<JTerminal>terminals=TerminalQuery.me().getNotInTM();
        JTerminal terminal=TerminalQuery.me().getByMediator(id);
        //获取全部席位
        List<JSeat> seats = SeatQuery.me().getAll();
        //获取绑定的席位
        List<JSeat> existSeat = SeatQuery.me().getSeatAll(id);
        List<JSeat> jeatt =new ArrayList<>();
        List<JMedseat> medseat = MeaSeatQuery.me().getAll();
        //遍历出已选的席位
        boolean tags = false;
        for (JSeat jSeat: seats){
            for (JMedseat jMedseat: medseat){
                if (jMedseat.getSeatId()==jSeat.getId()){
                    if (!existSeat.isEmpty()){
                        if (jMedseat.getSeatId()==existSeat.get(0).getId()){
                            tags = false;
                        }else {
                            jeatt.add(jSeat);
                            tags = true;
                        }
                    }else {
                        jeatt.add(jSeat);
                        tags = true;
                    }
                }
            }
        }
            seats.removeAll(jeatt);
//        JMediatorterminal mediatorterminal=MediatorTerminalQuery.me().findByMediatorId(id);
//        if(terminal!=null)
        //群组
        List<JGroup>groups=GroupQuery.me().getGroupByMedorId(id);
        setAttr("groups",groups);//匹配的群组
        List<JGroup>groupsN=GroupQuery.me().getNotIn(id);
        setAttr("groupsN",groupsN);//不匹配的群组
        setAttr("seats",seats);
        setAttr("existSeat",existSeat);
        setAttr("terminal",terminal);
        setAttr("jTerminals", terminals);
        setAttr("mediator", jMediator);
        render("mediatoredit.html");
    }

    /**
     * 更新数据到数据库
     */
    @Before(Tx.class)
    public void editSave() {
        JMediator jm = getModel(JMediator.class);
        int terminalId=getParaToInt("terminalId");
        int seatId=getParaToInt("seatId");
        boolean ajm=false;
        boolean ajt=false;
        //terminalId!=0&& 如果是解绑操作就不需要此判断
        if(jm!=null){
             ajm= jm.update();
            //删除中间表
            MeaSeatQuery.me().delectByMedId(jm.getId());
            if (seatId != 0){
                JMedseat js = getModel(JMedseat.class);
                js.setSeatId(seatId);
                js.setMediatorId(jm.getId());
                js.save();
            }

            //更改终端
             if(terminalId!=0){
                 JMediatorterminal mediatorterminal=MediatorTerminalQuery.me().findByMediatorId(jm.getId());
                 if(mediatorterminal==null){
                     mediatorterminal=new JMediatorterminal();
                     mediatorterminal.setTerminalId(terminalId);
                     mediatorterminal.setMediatorId(jm.getId());
                     mediatorterminal.save();//
                     CacheKit.put("mediatorterminal",mediatorterminal.getId(),mediatorterminal);
                 }else {
                     mediatorterminal.setTerminalId(terminalId);
                     Object ob=CacheKit.get("mediatorterminal",mediatorterminal.getId());
                     if(ob!=null){
                    	 CacheKit.remove("mediatorterminal",mediatorterminal.getId());
                     }
                     ajt=mediatorterminal.update();
                     CacheKit.put("mediatorterminal",mediatorterminal.getId(),mediatorterminal);

                 }

             }

        }


//        jt.removeCache(jt.getId());
//        jt.removeCache(jt.getOptionKey());
//        jt.putCache(jt.getOptionKey(), jo.getOptionValue());
        if(ajm){
            Integer[] ids=getParaValuesToInt("groupId");
            if(ids!=null){
                JGroupmediator groupmediator=new JGroupmediator();
                groupmediator.doDelete("mediatorId=?",jm.getId());
                if("1".equals(jm.getCamp())){
                    groupmediator.setGroupId(3);
                }else {
                    groupmediator.setGroupId(2);
                }
                groupmediator.setMediatorId(jm.getId());
                groupmediator.save();
                JGroupmediator dao=new JGroupmediator();
                dao.setMediatorId(jm.getId());
                dao.setGroupId(1);
                dao.save();

                //增加更改后的群组

                for(int i:ids){
                    JGroupmediator doo=new JGroupmediator();
                    doo.setMediatorId(jm.getId());
                    doo.setGroupId(i);
                    doo.save();
                }
            }

            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }
    /**
     * 删除操作
     */
    @Before(Tx.class)
    public void del() {
        Integer id = getParaToInt("id");
        JMediator mediator = MediatorQuery.me().findById(id);
        JMediatorterminal mediatorterminal=MediatorTerminalQuery.me().findByMediatorId(mediator.getId());
       /* Object ob=CacheKit.get("mediatorterminal",mediatorterminal.getId());*/
      /*  if(ob!=null)
            CacheKit.remove("mediatorterminal",mediatorterminal.getId());*/
        //删除中间表
        MeaSeatQuery.me().delectByMedId(id);
        int ID=mediator.getId();
        boolean am=false;
        if(mediatorterminal!=null){
            if(mediatorterminal.delete()){
                am= mediator.delete();
            }
        }else {
            am= mediator.delete();
        }

//        CacheKit.remove("terminal", id);
        if(am){
            JGroupmediator DAO=new JGroupmediator();
            DAO.doDelete("mediatorId=?",ID);
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
    }

    /**
     * 解除綁定
     */
    public void relieve(){
        int mediatorId=getParaToInt("mediatorId");
        int terminalId=getParaToInt("terminalId");
        boolean jb=false;
        if(mediatorId!=0&&terminalId!=0){
            JMediatorterminal mediatorterminal=MediatorTerminalQuery.me().findByMediatorId(mediatorId);
            if(mediatorterminal!=null){
                Object ob=CacheKit.get("mediatorterminal",mediatorterminal.getId());
                if(ob!=null){
                    CacheKit.remove("mediatorterminal",mediatorterminal.getId());
                }
            }
            jb=mediatorterminal.delete();

        }else {
            renderAjaxResult(MessageConstants.RELIEVE_DEFEAT,1,null);
            return;
        }
        if(jb){
            List<JTerminal>terminals=TerminalQuery.me().getNotInTM();
            renderAjaxResult(MessageConstants.RELIEVE_SUCCESS,0,terminals);
            return;
        }
            renderAjaxResult(MessageConstants.RELIEVE_DEFEAT,1,null);



    }
}
