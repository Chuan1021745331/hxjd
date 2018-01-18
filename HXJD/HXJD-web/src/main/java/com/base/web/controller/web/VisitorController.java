package com.base.web.controller.web;

import com.alibaba.fastjson.JSON;
import com.base.constants.Consts;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JDepartment;
import com.base.model.JVisitor;
import com.base.query.VisitorQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DepartmentService;
import com.base.service.VisitorService;
import com.base.utils.CookieUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: VisitorController
 * @包名: com.base.admin.controller
 * @描述: (只有访问权限的用户controller)
 * @所属: 华夏九鼎
 * @日期: 2018/1/16 14:13
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/visitor", viewPath = "/view/web/visitor")
@RouterNotAllowConvert
public class VisitorController extends BaseController{
    public void visitorData(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        String where = getPara("id");
        long count = VisitorService.me().findCountVisitorDepartment();
        List<Record> list = VisitorService.me().findListVisitorDepartment(page, limit, where, count);
        renderPageResult(0,"",(int) count,list);
    }


    public void edit(){
        Integer id = getParaToInt("id");
        JVisitor visitor = VisitorService.me().findVisitorById(id);
        setAttr("visitor", visitor);
        render("edit.html");
    }

    public void editSave(){
        Integer id = getParaToInt("visitorId");
        String newPassword = getPara("newPassword");
        String oldPassword = getPara("oldPassword");
        JVisitor visitor = VisitorService.me().findVisitorById(id);
        boolean b = VisitorService.me().editPassword(visitor, oldPassword, newPassword);
        if(b){
            //移除cookie
            CookieUtils.remove(this, Consts.COOKIE_LOGINED_VISITOR);
            renderAjaxResultForSuccess("修改成功");
        }else{
            renderAjaxResultForError("原始密码错误");
        }

    }

    public void sel(){
        Integer id = getParaToInt("id");
        JVisitor visitor = VisitorService.me().findVisitorById(id);
        JDepartment department = DepartmentService.me().findDepartmentByVisitorId(id);
        List<JDepartment> parents=new ArrayList<>();
        if(null!=department){
            parents=DepartmentService.me().getParents(department.getParentId());
        }
        setAttr("department",department);
        setAttr("parents", JSON.toJSON(parents));
        setAttr("visitor", visitor);
        renderTable("sel.html");
    }
}
