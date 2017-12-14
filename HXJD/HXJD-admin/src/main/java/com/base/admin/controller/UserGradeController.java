package com.base.admin.controller;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.common.PushUtil;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JPel;
import com.base.model.JTerminal;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.PelQuery;
import com.base.service.TerminalQuery;
import com.jfinal.aop.Before;

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
@RouterMapping(url = "/admin/usergrade", viewPath = "/view/admin/pel")
@RouterNotAllowConvert
public class UserGradeController extends BaseController {

    @Before(ButtonInterceptor.class)
    public void index() {
        render("pel.html");
    }

    public void pelData() {
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        if("红方".equals(search)){
            search = "0";
        }
        else if ("蓝方".equals(search)){
            search = "1";
        }
        else if ("开启".equals(search)){
            search = "3";
        }
        else if ("关闭".equals(search)){
            search = "2";
        }
        List<JPel> list = PelQuery.me().findList(start, length,column,order,search);
        long count = PelQuery.me().findConunt(search);
        renderPageResult(draw, (int)count, (int)count, list);
    }

    public void details() {
        Integer id = getParaToInt("id");
        JPel pel = PelQuery.me().findById(id);
        setAttr("pel", pel);
        render("details.html");
    }
    public void add() {
        render("add.html");
    }
    public void addSave() {
        JPel jo = getModel(JPel.class);
        boolean a = jo.save();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }
    public void edit() {
        Integer id = getParaToInt("id");
        JPel pel = PelQuery.me().findById(id);
        setAttr("pel", pel);
        render("edit.html");
    }
    public void editSave() {
        JPel jo = getModel(JPel.class);
        boolean a = jo.update();
        if(a){
            PushPel(jo.getId());
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    public void del() {
        Integer id = getParaToInt("id");
        JPel pel = PelQuery.me().findById(id);
        boolean a = pel.delete();
        if(a){
            PushPel(id);
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
    }
    //推送图元原理：通过改变的图元Id查出与这个图元有关联的调理员，然后将这个调理员所有的图元推送
    private void PushPel(int pelId){
        List<JTerminal>terminals= TerminalQuery.me().GainTerminaByPelId(pelId);
        if(terminals!=null){
            for(JTerminal terminal:terminals){
                PushUtil.pushPel(terminal);
            }
        }
    }

}
