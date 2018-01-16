package com.base.query;

import com.base.model.JDepartmentvisitor;

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
    public DepartmentVisitorQuery me(){
        return QUERY;
    }
}
