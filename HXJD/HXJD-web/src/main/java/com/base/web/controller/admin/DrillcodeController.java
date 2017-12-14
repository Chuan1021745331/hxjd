package com.base.web.controller.admin;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.common.PushUtil;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JDrillcode;
import com.base.model.JPel;
import com.base.model.JTerminal;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DrillcodeQuery;
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
@RouterMapping(url = "/admin/drillcode", viewPath = "/view/admin/drillcode")
@RouterNotAllowConvert
public class DrillcodeController extends BaseController {

    @Before(ButtonInterceptor.class)
    public void index() {
        render("drill.html");
    }

    public void drillcodeData() {
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
        render("add.html");
    }
    public void addSave() {
        JDrillcode jo = getModel(JDrillcode.class);
        boolean a = jo.save();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }
    public void edit() {
        Integer id = getParaToInt("id");
        JDrillcode drillcode = DrillcodeQuery.me().findById(id);
        setAttr("drillcode", drillcode);
        render("edit.html");
    }
    public void editSave() {
        JDrillcode jo = getModel(JDrillcode.class);
        boolean a = jo.update();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    public void del() {
        Integer id = getParaToInt("id");
        JDrillcode drillcode = DrillcodeQuery.me().findById(id);
        boolean a = drillcode.delete();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
    }



}
