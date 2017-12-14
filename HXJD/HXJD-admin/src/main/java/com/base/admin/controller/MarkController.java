package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JMark;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.MarkQuery;
import com.jfinal.aop.Before;

import java.util.List;

/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java
 * @包名: com.base.web.controller.admin
 * @描述: 用于用户管理相关操作
 * @所属: 华夏九鼎
 * @日期: 2017年5月15日 上午9:34:14
 * @版本: V1.0
 * @创建人：z
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/mark", viewPath = "/view/admin/map/")
@RouterNotAllowConvert
public class MarkController extends BaseController {
    @Before(ButtonInterceptor.class)
    public void index() {
        List<JMark> mark = MarkQuery.me().getMarkAll();
        setAttr("mark", JSON.toJSON(mark));
        render("mark.html");
    }
    public void edit() {
        Integer id = getParaToInt("id");
        JMark singleMark = MarkQuery.me().findById(id);
        renderJson(singleMark);
    }
    public void Save() {
        JMark jm = getModel(JMark.class);
        boolean a = jm.saveOrUpdate();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
            }
            renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }
    public void del() {
        Integer id = getParaToInt("id");
        JMark jm = MarkQuery.me().findById(id);
        boolean a = jm.delete();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
    }


}
