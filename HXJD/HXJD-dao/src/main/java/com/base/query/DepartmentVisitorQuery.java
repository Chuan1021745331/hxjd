package com.base.query;

import com.base.model.JDepartmentvisitor;
import com.jfinal.plugin.activerecord.Db;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentVisitorQuery
 * @包名: com.base.query
 * @描述: 访客与部门的关联
 * @所属: 华夏九鼎
 * @日期: 2018/1/16 16:50
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DepartmentVisitorQuery {
    private final static JDepartmentvisitor DAO=new JDepartmentvisitor();
    private final static DepartmentVisitorQuery QUERY=new DepartmentVisitorQuery();
    public static DepartmentVisitorQuery me(){
        return QUERY;
    }

    public JDepartmentvisitor findByVisitorId(int visitorId){
        return DAO.findFirst("select * from j_departmentvisitor where visitorId="+visitorId);
    }

    public long findCountByDepartmentId(int departmentId){
        return Db.queryLong("select count(*) from j_departmentvisitor where departmentId="+departmentId);
    }

    public int delByVisitorId(int visitorId){
        return DAO.doDelete("visitorId="+visitorId);
    }
}
