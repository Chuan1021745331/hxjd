package com.base.query;

import com.base.model.JDepartmentuser;
import com.base.model.JUser;
import com.jfinal.plugin.activerecord.Db;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentUserQuery
 * @包名: com.base.query
 * @描述: 部门和用户关联操作
 * @所属: 华夏九鼎
 * @日期: 2018/1/9 10:15
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class DepartmentUserQuery {
    protected final static JDepartmentuser DAO=new JDepartmentuser();
    private final static DepartmentUserQuery QUERY=new DepartmentUserQuery();

    public static DepartmentUserQuery me(){
        return QUERY;
    }

    public long findCountByDepartmentId(int departmentId){
        return Db.queryLong("select count(*) from j_departmentuser where departmentId="+departmentId);
    }

    public int delByUserId(int userId){
        return DAO.doDelete("userId=?", userId);
    }
}
