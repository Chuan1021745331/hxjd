package com.base.admin.controller;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.common.PushUtil;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.model.base.BaseJPelgroup;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
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
@RouterMapping(url = "/admin/pelgroup", viewPath = "/view/admin/pelgroup")
@RouterNotAllowConvert
public class PelGroupController extends BaseController {

    @Before(ButtonInterceptor.class)
    public void index() {
        render("pelgroup.html");
    }

    public void pelgroupData() {
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        if ("红方".equals(search)) {
            search = "0";
        } else if ("蓝方".equals(search)) {
            search = "1";
        } else if ("开启".equals(search)) {
            search = "3";
        } else if ("关闭".equals(search)) {
            search = "2";
        }
        List<Record> list = PelGroupQuery.me().findList(start, length, column, order, search);
        long count = PelGroupQuery.me().findConunt(search);
        renderPageResult(draw, (int) count, (int) count, list);
    }

    public void details() {
        Integer id = getParaToInt("id");
        JPelgroup jPelgroup = PelGroupQuery.me().findById(id);
        List<JPel> pel = PelQuery.me().getGroupAll(id);
        //获取绑定的席位
        List<JSeat> seatName = SeatQuery.me().getOneSeat(id);

        setAttr("seatName", seatName);
        setAttr("pel", pel);
        setAttr("jPelgroup", jPelgroup);
        render("details.html");
    }

    public void add() {
        List<JSeat> seats = SeatQuery.me().getAll();
        List<JPel> pelgroups = PelQuery.me().getAll();
        setAttr("seats", seats);
        setAttr("pelgroups", pelgroups);
        render("add.html");
    }

    @Before(Tx.class)
    public void addSave() {
        JPelgroup jo = getModel(JPelgroup.class);
        String[] pelid = getParaValues("pelid");
        String[] seatId = getParaValues("seatId");
        boolean a = jo.save();
        int pelgroupid = jo.getId();
        //保存关联的席位
        if (seatId != null) {
            for (int i = 0; i < seatId.length; i++) {
                JSeatpelgroup js = getModel(JSeatpelgroup.class);
                js.setSeatId(Integer.valueOf(seatId[i]));
                js.setPelgroupId(jo.getId());
                js.save();
            }
        }
        //保存图元
        if (pelid != null) {
            for (int i = 0; i < pelid.length; i++) {
                JPelgrouppel jpl = getModel(JPelgrouppel.class);
                jpl.setPelgroupId(pelgroupid);
                jpl.setPelId(Integer.valueOf(pelid[i]));
                boolean b = jpl.save();
                if (b = false) {
                    renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
                    return;
                }
            }
        }
        if (a) {
            PushPel(jo);//推送
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }

    public void edit() {
        Integer id = getParaToInt("id");
        JPelgroup pelgroup = PelGroupQuery.me().findById(id);
        List<JPel> pel = PelQuery.me().getAll();
        List<JPel> pelgroupPelId = PelQuery.me().getGroupAll(id);
        //获取全部席位
        List<JSeat> seats = SeatQuery.me().getAll();
        //自己选择的席位
        List<JSeat> seatExist = SeatQuery.me().getOneSeat(id);
        setAttr("seatExist", seatExist);
        setAttr("seats", seats);
        setAttr("pelgroupPelId", pelgroupPelId);
        setAttr("pel", pel);
        setAttr("pelgroup", pelgroup);
        render("edit.html");
    }

    @Before(Tx.class)
    public void editSave() {
        JPelgroup jo = getModel(JPelgroup.class);
        String[] pelid = getParaValues("pelid");
        String[] seatId = getParaValues("seatId");
        //保存选择的席位
        SeatgroupQuery.me().delectBygroupId(jo.getId());
        if (seatId != null) {
            for (int i = 0; i < seatId.length; i++) {
                JSeatpelgroup js = getModel(JSeatpelgroup.class);
                js.setSeatId(Integer.valueOf(seatId[i]));
                js.setPelgroupId(jo.getId());
                js.save();
            }
        }
        //保存选择的图元组
        Boolean c = PelgroupPelQuery.me().delectByPelgroupId(jo.getId());
        if (pelid != null) {
            for (int i = 0; i < pelid.length; i++) {
                JPelgrouppel jpl = getModel(JPelgrouppel.class);
                jpl.setPelgroupId(jo.getId());
                jpl.setPelId(Integer.valueOf(pelid[i]));
                jpl.save();
            }
        }
        boolean a = jo.update();
        if (a) {
            PushPel(jo);//推送
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    @Before(Tx.class)
    public void del() {
        Integer id = getParaToInt("id");
        List<JSeatpelgroup> seatQuery = SeatgroupQuery.me().seatIsExist(id);
        List<JMedseat> medseats = MeaSeatQuery.me().seatIsExist(id);
        if (seatQuery.isEmpty()) {
            PelgroupPelQuery.me().delectByPelgroupId(id);
            JPelgroup jPelgroup = PelGroupQuery.me().findById(id);
            boolean a = jPelgroup.delete();
            if (a) {
                PushPel(jPelgroup);//推送
                renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
                return;
            }
        } else {
            renderAjaxResultForError(MessageConstants.DEL_RELIEVE_SEAT);
        }
    }
//推送
    public void PushPel(JPelgroup pelgroup) {
        List<JTerminal>terminals=TerminalQuery.me().GainTerminalByPelGroupId(pelgroup.getId());
        if(terminals!=null){
            for(JTerminal terminal:terminals){
                PushUtil.pushPel(terminal);
            }


        }
    }

}
