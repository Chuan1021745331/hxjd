package com.base.web.controller.admin;

import com.base.constants.MessageConstants;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.MsgUtil;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.service.DrillcodeQuery;
import com.base.service.GroupDrillQuery;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.DevGroupBeanDto;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GroupController
 * @包名: com.base.web.controller.admin
 * @描述: 群组管理
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 11:06
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/group", viewPath = "/view/admin/group/")
public class GroupController extends BaseController {
    @Before(ButtonInterceptor.class)
    public void index() {
        render("group.html");
    }

    public void groupOption() {
        //System.out.println("/admin/group-----1----->groupOption");
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
       // System.out.println("/admin/group-----2----->groupOption");
        List<Record> list = GroupQuery.me().findList(start, length, column, order, search);
        long count = GroupQuery.me().findConunt(search);
        renderPageResult(draw, (int) count, (int) count, list);
    }

    public void add() {
//        List<JMediator>mediators=GroupQuery.me().getAllMediator();
//        if(mediators!=null){
//            setAttr("mediators",mediators);
//        }
        render("add.html");
    }

    public void addSave() {
        String groupName = getPara("groupName");
        JGroup group = new JGroup();
        group.setGroupName(groupName);
        group.saveOrUpdate();
        
        JDrillcode drill = DrillcodeQuery.me().getActiveDrill();
        if(drill!=null){//与训练关联
        	JGroupdrill groupDrill = new JGroupdrill();
        	groupDrill.setGroupId(group.getId());
        	groupDrill.setDrillId(drill.getId());
        	groupDrill.save();
        }
        
        
        renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
    }
    
    public void addCamp(){
    	render("addCamp.html");
    }
    
    
    @Before(Tx.class)
    public void addCampSave(){
    	
    	
    	JGroup group =  getModel(JGroup.class);
    	//group.setCamp(0);
    	group.saveOrUpdate();
    	renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
    }
    
    
    public void edit() {
        JGroup group = GroupQuery.me().findById(getParaToInt("id"));
        List<JMediator> mediatorsG = GroupQuery.me().getGroupMediator(getParaToInt("id"));
        List<JMediator> mediators = new ArrayList<JMediator>();
        
        JDrillcode drill = DrillcodeQuery.me().getActiveDrill();
        if(drill != null){
        	mediators = GroupQuery.me().getAllMediator(getParaToInt("id"), drill.getId());//未绑定该在的所有调理员
        }
        
        
        //List<JMediator> mediators = MediatorQuery.me().getAll();

        setAttr("mediatorsG", mediatorsG);
        setAttr("mediators", mediators);
        setAttr("group", group);
        render("edit.html");
    }

    @Before(Tx.class)
    public void editSave() {
        JGroup group = getModel(JGroup.class);
        Integer[] strs = getParaValuesToInt("mediatorId");
        JGroupmediator DAO = new JGroupmediator();
        //在删除组员之前查询出组员信息
        List<Record> records = TerminalQuery.me().GainTerminaByGroupId(group.getId());

        int ii = DAO.doDelete("groupId=?", group.getId());
        group.update();
        if (strs != null) {
            //更改了成员以及更改了群组信息
            for (int i : strs) {
                JGroupmediator dao = new JGroupmediator();
                dao.setMediatorId(i);
                dao.setGroupId(group.getId());
                dao.save();
            }

        }
        //推送改变的组信息
        SendGroup(strs, records);
        renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
    }

    @Before(Tx.class)
    public void del() {
        int groupId = getParaToInt("id");
        JGroupmediator groupmediator = new JGroupmediator();
        
        //删除群组与训练的关联
        JDrillcode drill = DrillcodeQuery.me().getActiveDrill();
        if(drill != null){
        	GroupDrillQuery.me().deleteByGroupId(groupId, drill.getId());
        }
        
        
        //在删除组员之前查询出组员信息
        List<Record> records = TerminalQuery.me().GainTerminaByGroupId(groupId);

        groupmediator.doDelete("groupId=?", groupId);
        JGroup group = new JGroup();
        group.doDelete("id=?", groupId);

        SendGroup(null, records);
        renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
    }

    /**
     * 推送组信息
     */
    @SuppressWarnings("rawtypes")
    private void SendGroup(Integer[] strs, List<Record> records) {
        List<Record> records1 = new ArrayList<>();
        if (strs != null)
            for (Record record : records) {
                for (int st : strs) {
//                JTerminal terminal1=TerminalQuery.me().getByMediator(Integer.parseInt(st));
                    if (record.getInt("mediator_id") == st) {
                        records1.add(record);
                        //records中与strs相同id数据的
                        ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(record.getStr("sdnum"));
                        List<JGroup> groups = GroupQuery.me().GainGroupByMediatorId(record.getInt("mediator_id"));
                        //发送消息
                        if (null != context) {
                            MsgUtil.Send(context, GainResponse(groups));
                        }

                    }
                }
            }
        //遍历records
        for (Record record : records) {
            if (records1.size() > 0) {
                for (Record record1 : records1) {
                    if (record.getInt("mediator_id") != record1.getInt("mediator_id")) {
                        //不是公共的
                        ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(record.getStr("sdnum"));
                        List<JGroup> groups = GroupQuery.me().GainGroupByMediatorId(record.getInt("mediator_id"));
                        //发送消息
                        if (null != context) {
                            MsgUtil.Send(context, GainResponse(groups));
                        }
                    }

                }
            } else {
                //如果没有公共的
                ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(record.getStr("sdnum"));
                List<JGroup> groups = GroupQuery.me().GainGroupByMediatorId(record.getInt("mediator_id"));
                //发送消息
                if (null != context) {
                    MsgUtil.Send(context, GainResponse(groups));
                }
            }
        }
        //遍历strs
        if (strs != null)
            for (int st : strs) {
                if (records1.size() > 0) {
                    for (Record record1 : records1) {
                        if (record1.getInt("mediator_id") != st) {
                            //不是公共的
                            List<JGroup> groups = GroupQuery.me().GainGroupByMediatorId(st);
                            JTerminal terminal1 = TerminalQuery.me().getByMediator(st);
                            if (terminal1 != null) {
                                ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(terminal1.getSdnum());
                                //发送消息
                                if (null != context) {
                                    MsgUtil.Send(context, GainResponse(groups));
                                }
                            }
                        }

                    }
                } else {
                    //如果没有公共的
                    List<JGroup> groups = GroupQuery.me().GainGroupByMediatorId(st);
                    JTerminal terminal1 = TerminalQuery.me().getByMediator(st);
                    if (terminal1 != null) {
                        ChannelContext context = (ChannelContext) IMcacheMap.cacheMap.get(terminal1.getSdnum());
                        //发送消息
                        if (null != context) {
                            MsgUtil.Send(context, GainResponse(groups));
                        }
                    }
                }
            }
    }

    //要推送的数据
    private ResponseDto GainResponse(List<JGroup> groups) {
        List<DevGroupBeanDto> devGroupBeanDtos = new ArrayList<>();
        for (JGroup group : groups) {
            DevGroupBeanDto devGroupBeanDto = new DevGroupBeanDto();
            devGroupBeanDto.setGroupId(group.getId());
            devGroupBeanDto.setGroupName(group.getGroupName());
            devGroupBeanDtos.add(devGroupBeanDto);
        }


        ResponseDto responseDto = new ResponseDto();
        responseDto.setHandle(3);
        responseDto.setCode(RequestCodeConstants.PUSH_GROUP_CODE);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        responseDto.setTime(df.format(new Date()));
        responseDto.setResponseStatus(1);
        responseDto.setData(devGroupBeanDtos);
        return responseDto;
    }
}
