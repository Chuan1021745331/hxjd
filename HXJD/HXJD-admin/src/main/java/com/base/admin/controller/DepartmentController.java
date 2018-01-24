package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JDepartment;
import com.base.model.dto.TreeSimpDto;
import com.base.query.DepartmentUserQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DepartmentService;
import com.jfinal.aop.Before;
import com.jfinal.core.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        //判断能否删除
        boolean canDel = DepartmentService.me().isCanDel(Integer.parseInt(id));
        //不能删除
        if(!canDel){
            renderAjaxResultForError(MessageConstants.DEPARTMENT_DEL_DEFEAT);
            return;
        }
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
            department.setName("根节点");
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
        JDepartment department = DepartmentService.me().saveOrUpdateForDepartment(model);
        if(null!=department){
            renderAjaxResult("",0,department);
        }else{
            renderAjaxResultForError();
        }
    }

    public void getAll(){
        renderJson(DepartmentService.me().getAll());
    }

    public void getChildren(){
        String pId = getPara("pId");
        List<JDepartment> children = DepartmentService.me().getChildren(Integer.parseInt(pId));
        renderJson(children);
    }

    public void getD(){
        String departmentId = getPara("departmentId");
        JDepartment department = DepartmentService.me().findDepartmentById(Integer.parseInt(departmentId));
        renderJson(department);
    }

    public void getDepartmentAndChildren(){
        String pId = getPara("pId");
        Map map=new HashMap<>();
        JDepartment department = DepartmentService.me().findDepartmentById(Integer.parseInt(pId));
        List<JDepartment> children = DepartmentService.me().getChildren(Integer.parseInt(pId));
        map.put("department",department);
        map.put("children",children);
        renderJson(map);
    }

    /**
     * @MethodName: positionData
     * @Description: 部门为departmentId的职位数据
     * @param
     * @return: void
     */
    public void positionData(){
        Integer departmentId = getParaToInt("menuId");
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        //默认为根节点
        if(null==departmentId)
            departmentId=new Integer(0);
        long count = DepartmentService.me().findCountPositionByDepartmentId(departmentId);
        List<JDepartment> positionList = DepartmentService.me().findPositionByDepartmentId(page, limit, departmentId, count);
        renderPageResult(0, "", (int)count, positionList);
    }

    public void addPosition(){
        Integer departmentId = getParaToInt("departmentId");
        JDepartment department = DepartmentService.me().findDepartmentById(departmentId);
        if(null==department){
            department=new JDepartment();
            department.setId(0);
            department.setName("根节点");
        }
        setAttr("department",department);
        renderTable("positionAdd.html");
    }

    public void editPosition(){
        Integer id = getParaToInt("id");
        JDepartment position = DepartmentService.me().findDepartmentById(id);
        List<JDepartment> parents = DepartmentService.me().getParents(position.getParentId());
        setAttr("department",position);
        setAttr("parents", parents);
        renderTable("positionEdit.html");
    }

    public void selPosition(){
        Integer id = getParaToInt("id");
        JDepartment position = DepartmentService.me().findDepartmentById(id);
        List<JDepartment> parents = DepartmentService.me().getParents(position.getParentId());
        setAttr("department",position);
        setAttr("parents", parents);
        renderTable("positionSel.html");
    }

    public void delPosition(){
        Integer id = getParaToInt("id");
        JDepartment position = DepartmentService.me().findDepartmentById(id);
        //需要判断有无用户属于该职称
        boolean canDel = DepartmentService.me().isCanDel(id);
        //如果不能删除
        if(!canDel){
            renderAjaxResultForError(MessageConstants.POSITION_DEL_DEFEAT);
            return ;
        }
        boolean flag = position.delete();
        if(flag){
            renderAjaxResultForSuccess("删除成功");
        }else{
            renderAjaxResultForError("删除失败");
        }
    }

}
