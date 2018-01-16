package com.base.service;

import com.base.model.JDepartmentvisitor;
import com.base.model.JVisitor;
import com.base.query.VisitorQuery;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: VisitorService
 * @包名: com.base.service
 * @描述: (用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2018/1/16 14:02
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class VisitorService {
    private final static VisitorService SERVICE=new VisitorService();
    public static VisitorService me(){
        return SERVICE;
    }

    public JVisitor findVisitorById(Integer integer){
        return VisitorQuery.me().findById(integer);
    }

    public long findCountVisitorDepartment(String search){
        return VisitorQuery.me().findCountVisitorDepartment(search);
    }
    public long findCountVisitorDepartment(){
        return VisitorQuery.me().findCountVisitorDepartment();
    }

    public List<Record> findListVisitorDepartment(Integer page,Integer limit,String search,long count){
        List<Record> list=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            list=VisitorQuery.me().findListVisitorDepartment(page,limit,search);
        }
        return list;
    }

    @Before(Tx.class)
    public boolean addVisitorSave(JVisitor visitor,Integer departmentId){
        //补全信息
        String salt= EncryptUtils.salt();
        visitor.setSalt(salt);
        visitor.setCreated(DateTime.now().toDate());
        //加密密码
        if(StringUtils.isNotEmpty(visitor.getPassword())){
            String encryptPassword = EncryptUtils.encryptPassword(visitor.getPassword(), salt);
            visitor.setPassword(encryptPassword);
        }
        boolean b = visitor.save();
        if(b){
            JDepartmentvisitor departmentvisitor=new JDepartmentvisitor();
            departmentvisitor.setDepartmentId(departmentId);
            departmentvisitor.setVisitorId(visitor.getId());
            return departmentvisitor.save();
        }
        return false;
    }
}
