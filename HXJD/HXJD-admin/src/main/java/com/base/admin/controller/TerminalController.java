package com.base.admin.controller;

import com.base.constants.MessageConstants;
import com.base.constants.RequestCodeConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.MsgUtil;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JMediatorterminal;
import com.base.model.JTerminal;
import com.base.model.dto.ResponseDto;
import com.base.router.RouterMapping;
import com.base.service.MediatorTerminalQuery;
import com.base.service.TerminalQuery;
import com.base.service.app.AppHandle.AppBean.DevInformationBean;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.tio.core.ChannelContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TerminalController
 * @包名: com.base.web.controller.admin
 * @描述: 终端管理
 * @所属: 华夏九鼎
 * @日期: 2017/6/2 11.15
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/terminal", viewPath = "/view/admin/terminal/")
public class TerminalController extends BaseController {
    @Before(ButtonInterceptor.class)
    public void index() {
        render("terminaloption.html");
    }

    /**
     * 查询、搜索、分页查询
     */
    public void optionData() {
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        List<JTerminal> list = TerminalQuery.me().findList(start, length, column, order, search);
        long count = TerminalQuery.me().findConunt(search);
        renderPageResult(draw, (int) count, (int) count, list);
    }

    /**
     * 进入添加界面
     */
    public void add() {
        render("terminaladd.html");
    }

    /**
     * 添加数据到数据库
     */
    public void addSave() {
        JTerminal jt = getModel(JTerminal.class);
        boolean a = jt.save();
        if (a) {
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }

    /**
     * 进入详情界面
     */
    public void details() {
        Integer id = getParaToInt("id");
        JTerminal terminal = TerminalQuery.me().findById(id);
        setAttr("terminal", terminal);
        render("terminaldetail.html");
    }

    /**
     * 进入到更新界面
     */
    public void edit() {
        Integer id = getParaToInt("id");
        JTerminal terminal = TerminalQuery.me().findById(id);
        setAttr("terminal", terminal);
        render("terminaledit.html");
    }

    /**
     * 更新数据到数据库
     */
    @Before(Tx.class)
    public void editSave() {
        JTerminal jt = getModel(JTerminal.class);
        JTerminal jTerminal=TerminalQuery.me().findById(jt.getId());
        boolean a = jt.update();
//        jt.removeCache(jt.getId());
//        jt.removeCache(jt.getOptionKey());
//        jt.putCache(jt.getOptionKey(), jo.getOptionValue());
        if (a) {
            //设备信息改变，则将改变的信息发送给相应的终端
            ChannelContext context= (ChannelContext) IMcacheMap.cacheMap.get(jTerminal.getSdnum());
            if(context!=null){

                ResponseDto responseDto=new ResponseDto();
                responseDto.setHandle(2);
                responseDto.setResponseStatus(1);
                responseDto.setCode(Integer.parseInt(RequestCodeConstants.PUSH_DEV_CODE));//101表示推送设备信息
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date=simpleDateFormat.format(new Date());
                responseDto.setTime(date);

                Record record= Db.findFirst("SELECT jt.sdnum,jt.terminal_name,jt.terminal_power,jt.terminal_stauts,jm.mediator_name,jm.mediator_grade,jm.camp,jm.id FROM j_terminal jt JOIN j_mediatorterminal jmt on jt.id=jmt.terminal_id JOIN j_mediator jm on jmt.mediator_id=jm.id WHERE jt.id=?",jt.getId());
                if(record==null){
                    //
                    return;
                }
                DevInformationBean devInformationBean=new DevInformationBean();

                devInformationBean.setSdNum(record.getStr("sdnum"));
                devInformationBean.setCamp(record.getStr("camp"));
                devInformationBean.setMediatorGrade(record.getStr("mediator_grade"));
                devInformationBean.setMediatorName(record.getStr("mediator_name"));
                devInformationBean.setTerminalName(record.getStr("terminal_name"));
                devInformationBean.setTerminalStauts(record.getStr("terminal_stauts"));
                devInformationBean.setMediatorId(record.getInt("id"));
                devInformationBean.setSdNum(record.getStr("sdnum"));

                responseDto.setData(devInformationBean);

                MsgUtil.Send(context,responseDto);//封装有发送消息
            }
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    /**
     * 删除操作
     */
    public void del() {
        Integer id = getParaToInt("id");
        JTerminal terminal = TerminalQuery.me().findById(id);
        JMediatorterminal mediatorterminal = MediatorTerminalQuery.me().findByTerminalId(terminal.getId());
        boolean at = false;
        if (mediatorterminal == null) {

//            if(mediatorterminal.delete()){
                at = terminal.delete();
//            }

        }else {
            renderAjaxResultForError(MessageConstants.DEL_RELIEVE_DEFEAT);
        }

//        CacheKit.remove("terminal", id);
        if (at) {
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return;
        }
        renderAjaxResultForError(MessageConstants.DEL_RELIEVE_DEFEAT);
    }
}
