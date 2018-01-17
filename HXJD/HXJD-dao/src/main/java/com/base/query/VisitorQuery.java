package com.base.query;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JUser;
import com.base.model.JVisitor;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: VisitorQuery
 * @包名: com.base.query
 * @描述: (用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2018/1/16 13:46
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class VisitorQuery {
    private final static JVisitor DAO=new JVisitor();
    private  final static VisitorQuery VISITORQUERY=new VisitorQuery();
    public static VisitorQuery me(){
        return VISITORQUERY;
    }
    public JVisitor findById(int visitorId){
        return DAO.findById(visitorId);
    }

    public JVisitor findVisitorByVisitorName(String visitorname){
        return DAO.findFirst("select * from j_Visitor where visitorname = ?",visitorname);
    }

    public List<Record> findListVisitorDepartment(int page,int limit,String where){
        StringBuilder sql=new StringBuilder("select v.*,d.name as departmentName ");
        sql.append(" from j_visitor v left join j_departmentvisitor dv on v.id=dv.visitorId left join j_department d on d.id=dv.departmentId ");
        sql.append(" order by v.id asc limit ?,?");
        LinkedList params = new LinkedList();
        params.add(limit*page-limit);
        params.add(limit);
        return Db.find(sql.toString(),params.toArray());
    }

    public long findCountVisitorDepartment(String search){
        StringBuilder sqlBuilder = new StringBuilder("SELECT count(*) FROM j_visitor v LEFT JOIN j_departmentvisitor dv ON dv.visitorId=v.id LEFT JOIN j_department d ON d.id=dv.departmentId");
        if(StringUtils.isEmpty(search)){
            return Db.queryLong(sqlBuilder.toString());
        }else{
            sqlBuilder.append(" where CONCAT( IFNULL(v.id, ''),IFNULL(v.visitorname, ''),IFNULL(v.relname, ''),IFNULL(v.email, ''),IFNULL(v.mobile, ''),IFNULL(d.name, '')) LIKE '%"+search+"%' ");

            return Db.queryLong(sqlBuilder.toString());
        }
    }
    public Long findCountVisitorDepartment() {
        StringBuilder sqlBuilder = new StringBuilder("SELECT count(*) FROM j_visitor v LEFT JOIN j_departmentvisitor dv ON dv.visitorId=v.id LEFT JOIN j_department d ON d.id=dv.departmentId");
        return Db.queryLong(sqlBuilder.toString());
    }

    public JVisitor findByVisitorName(String visitorname){
        return DAO.findFirst("select * from j_visitor where visitorname=?",visitorname);
    }

    public boolean delById(int visitorId){
       return DAO.deleteById(visitorId);
    }
}
