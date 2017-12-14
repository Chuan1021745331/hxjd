package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.Contents;
import com.base.model.dto.PushObjectDto;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.service.*;
import com.base.service.app.AppHandle.AppBean.ScoreBean;
import com.base.service.app.AppInterface.OffMsgType;
import com.base.utils.GZipUtil;
import com.base.utils.IsIntUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.Json;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.jsoup.helper.DataUtil;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.*;

import static com.base.utils.DateUtils.PushData;

/**
 * Created by hxjd on 2017/5/22.
 */
@RouterMapping(url = "/admin/objects", viewPath = "/view/admin/object")
public class ObjectController extends BaseController {
    @Before(ButtonInterceptor.class)
    public void index(){
        render("object.html");
    }

    /**
     * 显示和查询
     */
    public void objectData(){
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        List<Record> list;
        long count;
        if (search != ""){
            String[] searchCondition = search.split("@");
            list = ObjectsQuery.me().condition(start, length,column,order,searchCondition[1],searchCondition[0]);
            count = ObjectsQuery.me().findCondition(searchCondition[1],searchCondition[0]);
        }else {
            list = ObjectsQuery.me().findList(start, length,column,order,search);
            count = ObjectsQuery.me().findConunt(search);
        }

        renderPageResult(draw, (int)count, (int)count, list);
    }


    /**
     * 阶段获取
     */
    public void isGrade(){
        List<JDrillcode> jDrillcode = DrillcodeQuery.me().getAll();
        List<JKpi> jKpis = KpiQuery.me().findByParent(0);
        renderJson(JSON.toJSON(jKpis));
    }

    /**
     * 训练代号获取
     */
    public void isDrill(){
        List<JDrillcode> jDrillcode = DrillcodeQuery.me().getAll();
        for(JDrillcode jd: jDrillcode){
            jd.setName(jd.getName()+"（"+jd.getTime()+"）");
        }
        renderJson(JSON.toJSON(jDrillcode));
    }

    /**
     * 获取添加所需的信息
     */
    public void add() {
        //获取阶段
        List<JKpi> jKpis = KpiQuery.me().findByParent(0);
        //获取全部的调理员信息
        List<JMediator>  jMediators = MediatorQuery.me().getAll();
       /* //获取已经选择了的调度员信息
        List<JObjectmed> exist = ObjectMedQuery.me().getAll();
        List<JMediator> jMediator = new ArrayList<>();
        //遍历出已选的调理员
        for (JMediator jM: jMediators){
            for (JObjectmed jo: exist){
                if (jM.getId() == jo.getMediatorId()){
                    jMediator.add(jM);
                }
            }
        }
        jMediators.removeAll(jMediator);*/
        List<JDrillcode> jDrillcodes = DrillcodeQuery.me().getAll();
        setAttr("jDrillcodes",jDrillcodes);
        setAttr("jKpis",jKpis);
        setAttr("jMediators",jMediators);
        render("add.html");
    }

    /**
     * 添加数据到数据库
     */
    @Before(Tx.class)
    public void addSave() {
        JObject jo = getModel(JObject.class);
        String[] medId = getParaValues("medid");
        //获取绑定调理员的ID
        Boolean b = jo.save();
        if (b&&medId != null){
            for (int i=0; i < medId.length ;i++){
                if (Integer.valueOf(medId[i]) != 0){
                    JObjectmed jom = new JObjectmed();
                    jom.setMediatorId(Integer.valueOf(medId[i]));
                    jom.setObjectId(jo.getId());
                    jom.save();
                }
            }
        }
        if(b){
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
        //获取信息
        List<JMediator> jMediators =  MediatorQuery.me().getMyMediator(id);
        JObject jObject = ObjectsQuery.me().findById(id);
        setAttr("jObject",jObject);
        setAttr("jMediators",jMediators);
        render("details.html");
    }

    /**
     * 进入到编辑界面
     */
    public void edit() {
        Integer id = getParaToInt("id");
        List<JMediator> jMediator = new ArrayList<>();
        //获取全部的调理员信息
        List<JMediator>  jMediators = MediatorQuery.me().getAll();
        //获取已经选择了的调度员信息
//        List<JMediator> exist = MediatorQuery.me().getExist();
        //获取自己选中的调度员
        List<JMediator> meOn = MediatorQuery.me().getMediatorId(id);

        JObject jObject = ObjectsQuery.me().findById(id);
        //遍历出已选的调理员
      /*  for (JMediator jM: jMediators){
            if(!exist.contains(jM)){
                jMediator.add(jM);
            }
        }*/
        //遍历自己已选的调理员
        for (JMediator jr : meOn){
            jMediator.add(jr);
        }
        List<JKpi> jKpis = KpiQuery.me().findByParent(0);
        List<JDrillcode> jDrillcodes = DrillcodeQuery.me().getAll();
        for (JDrillcode jd : jDrillcodes){
            jd.setName(jd.getName()+"（"+jd.getTime()+"）");
        }
        setAttr("jDrillcodes",jDrillcodes);
        setAttr("jkpis",jKpis);
        setAttr("meOn",meOn);
        setAttr("jMediator",jMediators);
        setAttr("jObject", jObject);
        render("edit.html");
    }

    /**
     * 更新数据到数据库
     */
    @Before(Tx.class)
    public void editSave() {
        JObject jo = getModel(JObject.class);
        String[] medId = getParaValues("medId");
        boolean c = true;
        if (medId == null){
            c = false;
        }
        //更新对象表
        boolean a = jo.update();
        boolean b = false;
        //更新中间表
        ObjectMedQuery.me().delectByObjectId(jo.getId());
        if (c){
            for (int i = 0;i< medId.length;i++){
                JObjectmed jObjectmed = getModel(JObjectmed.class);
                jObjectmed.setMediatorId(Integer.valueOf(medId[i]));
                jObjectmed.setObjectId(jo.getId());
                if(Integer.valueOf(medId[i]) != 0){
                    b = jObjectmed.save();
                }
                b = true;
            }
        }

        if(a){
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    /**
     * 删除操作
     */
    public void del() {
        Integer id = getParaToInt("id");
        //根据对象id删除中间表
        Boolean a = ObjectMedQuery.me().delectByObjectId(id);
        Boolean b = ObjectsQuery.me().findById(id).delete();
        if(b){
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
    }

    /**
     * 推送页面
     */
    public void push(){
        List<JKpi> jKpis = KpiQuery.me().findByParent(0);
        List<JDrillcode> jDrillcodes = DrillcodeQuery.me().getAll();
        for (JDrillcode jd : jDrillcodes){
            jd.setName(jd.getName()+"（"+jd.getTime()+"）");
        }
        setAttr("jDrillcodes",jDrillcodes);
        setAttr("jkpis",jKpis);
        render("push.html");
    }

    public void gradeMessage(JKpi jKpi){
        JGrademark jGrademark = getModel(JGrademark.class);
        boolean isInts;
        String name = jKpi.getName();
        Integer id = jKpi.getId();
        jGrademark.setId(id);
        isInts = IsIntUtils.isNumeric(name);
        if (isInts){
            name = ObjectsQuery.me().findById(Integer.valueOf(name)).getName();
        }
        jGrademark.setName(name);
        jGrademark.setGradeParentId(jKpi.getParentId());
        JGrademark IsjGrademark = GradeMarkQuery.me().findById(id);
        if(IsjGrademark == null){
            jGrademark.save();
        }
    }

    /**
     * 推送
     */
    public void pushSave(){
    	ArrayList<Integer> mdeid = new ArrayList<>();
    	Integer stageParentId;
        List<PushObjectDto> pushList = new LinkedList<>();
        JKpi drill = new JKpi();
        Integer id = getParaToInt("stageId");
        JKpi stage = KpiQuery.me().findById(id);
        String drillname = getPara("drillname");
        Integer camp = getParaToInt("camp");
        stageParentId = GradeMarkQuery.me().findByName(drillname);
       /* if (stageParentId.equals(-1)){
            //插入训练代号
            drill.setName(drillname);
            drill.setParentId(0);
            gradeMessage(drill);
            stageParentId = GradeMarkQuery.me().findByName(drillname);
        }*/
        //插入阶段名称
        if (stageParentId.equals(-1)){
            drill.setName(stage.getName());
//        drill.setParentId(stageParentId);
            drill.setParentId(0);
            drill.setId(stage.getId());
            gradeMessage(drill);
        }
        List<JKpi> jKpis = KpiQuery.me().findByParentid(id);
        for (JKpi jk: jKpis){
            gradeMessage(jk);
            List<JKpi> jp = KpiQuery.me().findByParent(jk.getId());
            for (JKpi ki : jp){
                List<JMediator> jms;
                if (camp == 0){
                    jms = MediatorQuery.me().getMediatorRedByobjectName(ki.getName(),drillname);
                }else if (camp == 1){
                    jms = MediatorQuery.me().getMediatorBlueByobjectName(ki.getName(),drillname);
                }else {
                    jms = MediatorQuery.me().getMediatorByobjectName(ki.getName(),drillname);
                }
                gradeMessage(ki);
                for (JMediator jr : jms){
                        PushObjectDto pushObject = pullobjectData(ki.getId());
                        pushObject.setCamp(jr.getCamp());
                        pushObject.setId(jr.getId());
                        pushObject.setDrillname(drillname);
                        mdeid.add(jr.getId());
                        pushList.add(pushObject);
                }
            }
        }
        System.out.println(JSON.toJSON(pushList));
        //-------------------推送评分-------------------
        try{
        Map<Integer,List<PushObjectDto>> map = new HashMap<>();
        Set mdeSet = new HashSet(mdeid);
        for(Object i:mdeSet){
            List<PushObjectDto> pushs = new ArrayList<>();
            for(PushObjectDto pushObjectDto:pushList){
                if(i == pushObjectDto.getId()){
                    pushs.add(pushObjectDto);
                }
            }

            map.put(Integer.parseInt(i.toString()),pushs);
        }

        for(Map.Entry<Integer,List<PushObjectDto>> entry : map.entrySet()){
            boolean isoff=false;
            int key = entry.getKey();
            JTerminal terminal=TerminalQuery.me().getByMediator(key);
            ChannelContext context= (ChannelContext) IMcacheMap.cacheMap.get(terminal.getSdnum());
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
            String date=df.format(new Date());
            if(context!=null){
                //在线
                List<PushObjectDto> polist = entry.getValue();
                ScoreBean scoreBean=new ScoreBean();
                scoreBean.setMsg(polist);
                scoreBean.setSendTime(date);
                scoreBean.setMsgType(OffMsgType.SCORE_TYPE+"");
                IMPacket packet=new IMPacket();
                ResponseDto responseDto=new ResponseDto();

                responseDto.setCode(109);
                responseDto.setTime(date);
                responseDto.setResponseStatus(1);
                responseDto.setHandle(2);

                responseDto.setData(scoreBean);
                String respo= JSON.toJSONString(responseDto);
//                String respo= JSON.toJSONString("ma dong de ......");
                byte[] doZipMsg = new byte[0];
                try {
                    doZipMsg = GZipUtil.doZip(respo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                packet.setBody(doZipMsg);
                Aio.send(context,packet);
            }else {
                //离线
                JMsg msg1=new JMsg();
//               msg1.setSendName(mesg.getSendName());
                msg1.setMessage(JSON.toJSONString(entry.getValue()));
//               msg1.setSenderId(Integer.parseInt(mesg.getSender()));
                msg1.setMsgType(OffMsgType.SCORE_TYPE+"");
                msg1.setSendTime(date);

                if(!isoff){
                    msg1.save();
                    isoff=true;
                }

                JOffuser offuser=new JOffuser();
//               offuser.setGroupId(mesg.getGroupId());
                offuser.setOffType(msg1.getMsgType());
                offuser.setMsgId(msg1.getId().toString());
                offuser.setMediatorId(key);
                OffUserQuery.me().updateAndSave(offuser);
            }
        }
    }catch (Exception e){
        //推送失败
//            return ;
        renderAjaxResultForError(MessageConstants.PUSH_MSG_FAIL);
        return;
    }
        //--------------------------------------

       
        renderAjaxResultForSuccess(MessageConstants.PUSH_MSG_SUCCESS);
    }

    public PushObjectDto pullobjectData(Integer id){
        List<Contents> contentsList = new ArrayList<>();
        PushObjectDto pd = new PushObjectDto();
        JKpi jk = KpiQuery.me().findById(id);
        Integer projectId = jk.getParentId();
        //对象名称
        String role = jk.getName();
        boolean isInt = false;
        for (int i = 0; i < role.length(); i++) {
            if (!Character.isDigit(role.charAt(i))) {
                isInt = false;
            }else{
                isInt = true;
            }
        }
        if (isInt){
            role = ObjectsQuery.me().findById(Integer.valueOf(role)).getName();
        }
        //查询项目名称
        JKpi jKpi = KpiQuery.me().findById(projectId);
        String project = jKpi.getName();
        //查询阶段名称
        JKpi jKpi2 = KpiQuery.me().findById(jKpi.getParentId());
        String statg = jKpi2.getName();
        //查询内容
        pd.setMediator(statg);
        pd.setProject(project);
        pd.setRole(role);
        pd.setContent(content(id));
        return pd;
    }

    //内容递归
    public  List<Contents> content(Integer id){
        //查询内容
        List<Contents> contentsList = new ArrayList<>();
        List<JKpi> concten = KpiQuery.me().findByParentid(id);

        for(JKpi jk : concten){
            Contents contents = new Contents();
            contents.setName(jk.getName());
            if (!KpiQuery.me().findByParent(jk.getId()).isEmpty()){
                contents.setChildren(content(jk.getId()));
            }else {
                contents.setScore(jk.getScore());
                contents.setEndNode(jk.getId());
//                contents.setParentId(jk.getParentId());
            }
            gradeMessage(jk);
            contentsList.add(contents);
        }
        return contentsList;
    }


}
