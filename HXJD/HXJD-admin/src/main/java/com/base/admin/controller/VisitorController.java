package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JDepartment;
import com.base.model.JVisitor;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DepartmentService;
import com.base.service.VisitorService;
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
@RouterMapping(url = "/admin/visitor", viewPath = "/view/admin/visitor")
@RouterNotAllowConvert
public class VisitorController extends BaseController{
    @Before(NewButtonInterceptor.class)
    public void index() {
        renderTable("visitor.html");
    }

    public void visitorData(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        String where = getPara("id");
        long count = VisitorService.me().findCountVisitorDepartment();
        List<Record> list = VisitorService.me().findListVisitorDepartment(page, limit, where, count);
        renderPageResult(0,"",(int) count,list);
    }
    public void add(){
        render("add.html");
    }

    public void addSave(){
        JVisitor visitor = getModel(JVisitor.class);
        Integer departmentId = getParaToInt("departmentId");
        //访客已经存在
        if(VisitorService.me().isExists(visitor.getVisitorname())){
            renderAjaxResultForError("用户名已经存在,添加失败!!!");
            return ;
        }
        boolean b = VisitorService.me().addVisitorSave(visitor, departmentId);
        if(b){
            renderAjaxResultForSuccess("添加成功");
        }else{
            renderAjaxResultForError("添加失败");
        }
    }

    public void edit(){
        Integer id = getParaToInt("id");
        JVisitor visitor = VisitorService.me().findVisitorById(id);
        JDepartment department = DepartmentService.me().findDepartmentByVisitorId(id);
        List<JDepartment> parents=new ArrayList<>();
        if(null!=department){
            parents=DepartmentService.me().getParents(department.getParentId());
            parents.add(department);
        }
        setAttr("department",department);
        setAttr("parents", JSON.toJSON(parents));
        setAttr("visitor", visitor);
        render("edit.html");
    }

    public void editSave(){
        UploadFile uploadFile = this.getFile();
        Integer departmentId = getParaToInt("departmentId");
        JVisitor visitor = getModel(JVisitor.class);
        boolean b = VisitorService.me().editVisitorSave(visitor, uploadFile, departmentId);
        if(b){
            renderAjaxResultForSuccess("修改成功");
        }else{
            renderAjaxResultForSuccess("修改失败");
        }
    }

    public void del(){
        Integer id = getParaToInt("id");
        boolean b = VisitorService.me().delByVisitorId(id);
        if(b){
            renderAjaxResultForSuccess("删除成功");
        }else{
            renderAjaxResultForError("删除失败,请重试");
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
        render("sel.html");
    }
}
