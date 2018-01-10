package com.base.admin.controller;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JDepartment;
import com.base.model.dto.TreeSimpDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DepartmentService;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentController
 * @包名: com.base.admin.controller
 * @描述: 部门控制器
 * @所属: 华夏九鼎
 * @日期: 2018/1/9 11:10
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */

@RouterMapping(url = "/admin/department", viewPath = "/view/admin/department")
@RouterNotAllowConvert
public class DepartmentController extends BaseController{
    @Before(NewButtonInterceptor.class)
    public void index(){
        renderTable("department.html");
    }
    public void tree(){
        TreeSimpDto departmentSimp = DepartmentService.me().getDepartmentSimp(0);
        renderJson(departmentSimp);
    }

    public void delTree(){
        String id = getPara("id");
        boolean flag = DepartmentService.me().delTree(Integer.parseInt(id));
        if(flag){
            renderAjaxResultForSuccess();
        }else{
            renderAjaxResultForError();
        }
    }

    public void addD(){
        String pid = getPara("pId");
        JDepartment department = DepartmentService.me().findDepartmentById(Integer.parseInt(pid));
        if(null==department){
            department=new JDepartment();
            department.setId(0);
            department.setName("总部");
        }
        this.setAttr("department",department);
        render("departmentAdd.html");
    }


    public void editD(){
        String id = getPara("id");
        JDepartment department = DepartmentService.me().findDepartmentById(Integer.parseInt(id));
        JDepartment pdepartment = DepartmentService.me().findDepartmentById(department.getParentId());
        setAttr("department",department);
        setAttr("pdepartment",pdepartment);
        renderTable("departmentEdit.html");
    }

    public void saveOrUpdateForDepartment(){
        JDepartment model = getModel(JDepartment.class);
        boolean flag = DepartmentService.me().save(model);
        if(flag){
            renderAjaxResultForSuccess();
        }else{
            renderAjaxResultForError();
        }
    }

    public void getChildren(){
        String pId = getPara("pId");
        List<JDepartment> children = DepartmentService.me().getChildren(Integer.parseInt(pId));
        renderJson(children);
    }
}
