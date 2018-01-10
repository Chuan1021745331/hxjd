package com.base.service;

import com.base.model.JDepartment;
import com.base.model.dto.MenuSimpDto;
import com.base.model.dto.TreeSimpDto;
import com.base.query.DepartmentQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentService
 * @包名: com.base.service
 * @描述: 部门业务服务
 * @所属: 华夏九鼎
 * @日期: 2018/1/9 10:37
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DepartmentService {
    private final static DepartmentService SERVICE=new DepartmentService();
    public static DepartmentService me(){
        return SERVICE;
    }

    public JDepartment findDepartmentById(int departmentId){
        return DepartmentQuery.me().findById(departmentId);
    }

    public boolean save(JDepartment department){
        //父节点为根节点，直接添加
        if(department.getParentId()==0){
            return department.saveOrUpdate();
        }
        JDepartment parent = this.findDepartmentById(department.getParentId());
        //如果上一级是职称，不能添加
        if(parent.getType()==JDepartment.TYPE_POSITION){
            return false;
        }
        return department.saveOrUpdate();
    }
    public boolean del(String id){
        JDepartment department = DepartmentQuery.me().findById(Integer.parseInt(id));
        return department.delete();
    }

    public boolean delTree(int departmentId){
        JDepartment department = DepartmentQuery.me().findById(departmentId);
        List<JDepartment> departmentList = DepartmentQuery.me().findByParentId(departmentId);
        if(departmentList.size()>0){
            for(JDepartment d:departmentList){
                delTree(d.getId());
            }
        }
        return department.delete();
    }

    public List<JDepartment> getChildren(int departmentId){
        return DepartmentQuery.me().findByParentId(departmentId);
    }

    /**
     * @MethodName: getJDepartmentSimp
     * @Description: 获取部门id为departmentId下面的所有子部门,并封装为树形数据
     * @param departmentId
     * @return: java.util.List<com.base.model.dto.TreeSimpDto<com.base.model.JDepartment>>
     */
    public TreeSimpDto getDepartmentSimp(int departmentId){
        List<JDepartment> children = DepartmentQuery.me().findByParentId(departmentId);
        TreeSimpDto dtt = new TreeSimpDto();
        if(departmentId!=0){
            JDepartment department = DepartmentQuery.me().findById(departmentId);
            dtt.setId(department.getId()+"");
            dtt.setName(department.getName());
            dtt.setScore(department.getType()+"");
        }else{
            dtt.setId("0");
            dtt.setName("总部");
        }
        List<TreeSimpDto> treeChildren= new ArrayList<>();
        if(children.size()>0){
            for(JDepartment d:children){
                treeChildren.add(getDepartmentSimp(d.getId()));
            }
        }
        dtt.setChildren(treeChildren);
        return dtt;
    }
}
