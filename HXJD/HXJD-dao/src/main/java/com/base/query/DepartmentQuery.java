package com.base.query;

import com.base.model.JDepartment;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentQuery
 * @包名: com.base.query
 * @描述: 部门数据操作
 * @所属: 华夏九鼎
 * @日期: 2018/1/9 9:26
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DepartmentQuery {
    protected final static JDepartment DAO=new JDepartment();
    private final static DepartmentQuery QUERY=new DepartmentQuery();
    public static DepartmentQuery me(){
        return QUERY;
    }

    public List<JDepartment> findByParentId(int parentId){
        return DAO.find("select * from j_department where parentId="+parentId);
    }

    public JDepartment findById(int id){
        return DAO.findById(id);
    }

    public List<JDepartment> getAll(){
        return DAO.find("select * from j_department");
    }

    public JDepartment findByUserId(int userId){
        return DAO.findFirst("select d.* from j_department d join j_departmentuser du on d.id=du.departmentId where du.userId="+userId);
    }
}
