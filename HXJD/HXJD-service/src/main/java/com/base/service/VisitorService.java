package com.base.service;

import com.base.model.JDepartment;
import com.base.model.JDepartmentvisitor;
import com.base.model.JVisitor;
import com.base.query.DepartmentQuery;
import com.base.query.DepartmentVisitorQuery;
import com.base.query.VisitorQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;
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
            for(Record record:list){
                JDepartment department = DepartmentService.me().findDepartmentByVisitorId(record.getInt("id"));
                if(department!=null)
                    record.set("departmentName",department.getName());
                else
                    record.set("departmentName","暂无职称");
            }
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

    public JVisitor findByVisitorName(String visitorname){
        return VisitorQuery.me().findByVisitorName(visitorname);
    }

    /**
     * @MethodName: isExists
     * @Description: 判断访客是否存在
     * @param visitorname 访客用户名
     * @return: boolean
     */
    public boolean isExists(String visitorname){
        JVisitor visitor = VisitorQuery.me().findByVisitorName(visitorname);
        if(null==visitor)
            return false;
        return true;
    }

    @Before(Tx.class)
    public boolean editVisitorSave(JVisitor visitor, UploadFile uploadFile,Integer departmentId){
        //修改visitor
        JVisitor temp = VisitorService.me().findVisitorById(visitor.getId());
        temp.setVisitorname(visitor.getVisitorname());
        temp.setRelname(visitor.getRelname());
        temp.setEmail(visitor.getEmail());
        temp.setGender(visitor.getGender());
        temp.setMobile(visitor.getMobile());
        if (null != uploadFile) {
            String newPath = AttachmentUtils.moveFile(uploadFile,"avatar");
            visitor.setAvatar(newPath);
            //logger.info(newPath);
            temp.setAvatar(visitor.getAvatar());
        }
        boolean b = temp.update();
        //清除缓存
//        CacheKit.remove("visitor", temp.getId());
        //修改新的关联关系
        if(b){
            JDepartmentvisitor departmentvisitor = DepartmentVisitorQuery.me().findByVisitorId(visitor.getId());
            if(departmentvisitor==null){
                departmentvisitor=new JDepartmentvisitor();
                departmentvisitor.setVisitorId(temp.getId());
                departmentvisitor.setDepartmentId(departmentId);
                return departmentvisitor.save();
            }
            departmentvisitor.setDepartmentId(departmentId);
            return  departmentvisitor.update();
        }
        return false;
    }

    public boolean delByVisitorId(Integer visitorId){
        //删除自己
        boolean b = VisitorQuery.me().delById(visitorId);
        if(b){
            //清除缓存
//            CacheKit.remove("visitor",visitorId);
            //删除关联关系
            DepartmentVisitorQuery.me().delByVisitorId(visitorId);
            return true;
        }
        return false;
    }

    public boolean login(JVisitor visitor,String password){
        //匹配密码
        if(!EncryptUtils.verlifyUser(visitor.getPassword(),visitor.getSalt(),password)){
            return false;
        }
        visitor.setLogined(DateTime.now().toDate());
        visitor.saveOrUpdate();
        return true;
    }

    public boolean editPassword(JVisitor visitor,String oldPassword,String newPassword){
        String salt = visitor.getSalt();
        String oldPasswordEn=EncryptUtils.encryptPassword(oldPassword, salt);
        String newPasswordEn = EncryptUtils.encryptPassword(newPassword, salt);
        //匹配密码
        if(!visitor.getPassword().equals(oldPasswordEn)){
            return false;
        }
        visitor.setPassword(newPasswordEn);
        visitor.saveOrUpdate();
        return true;
    }
}
