package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JObject;
import com.base.model.JPelgrouppel;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class ObjectsQuery {
    protected static final JObject DAO = new JObject();
    private static final ObjectsQuery QUERY = new ObjectsQuery();

    public static ObjectsQuery me() {
        return QUERY;
    }

    public JObject findById(final Integer pelId) {
                return DAO.findById(pelId);
    }


    public List<JObject> getAll(){
        return DAO.find("select * from j_object order by id asc");
    }

    public Boolean delectByPelgroupId(int id){
        int b = DAO.doDelete("pelgroup_id =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<Record> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT a.id,a.drillname,a.camp,a.remark,a.grade, count(a.mediator_name) AS counts,a.name FROM " +
                "(SELECT o.id,o.drillname, o.name,o.remark,o.grade, m.mediator_name, m.camp FROM j_object o LEFT JOIN j_objectmed om ON om.objectId = o.id LEFT JOIN j_mediator m ON m.id = om.mediatorId) a");
        String c=column==0?"a.drillname":column==1?"a.grade":column==2?"a.name":"a.remark";
//        sqlBuilder.append(" WHERE CONCAT(IFNULL(a.grade, ''),IFNULL(a.name, ''),IFNULL(a.remark, '')) LIKE '%"+search+"%'and a.name='指挥机构' ");
        sqlBuilder.append(" WHERE CONCAT(IFNULL(a.drillname, ''),IFNULL(a.grade, ''),IFNULL(a.name, ''),IFNULL(a.remark, '')) LIKE '%"+search+"%'");
        sqlBuilder.append(" GROUP BY a.id order by "+c+" "+order+" LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(page);
        params.add(pagesize);

        if (params.isEmpty()) {
            return Db.find(sqlBuilder.toString());
        } else {
            return Db.find(sqlBuilder.toString(), params.toArray());
        }
    }

    public List<Record> condition(int page, int pagesize, int column, String order, String grade,String drillname) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT a.id,a.drillname,a.camp,a.remark,a.grade, count(a.mediator_name) AS counts,a.name FROM " +
                "(SELECT o.id,o.drillname, o.name,o.remark,o.grade, m.mediator_name, m.camp FROM j_object o LEFT JOIN j_objectmed om ON om.objectId = o.id LEFT JOIN j_mediator m ON m.id = om.mediatorId) a");
        String c=column==0?"a.drillname":column==1?"a.grade":column==2?"a.name":"a.remark";
        sqlBuilder.append(" WHERE CONCAT(IFNULL('a.drillname', ''),IFNULL(a.grade, ''),IFNULL('a.name', ''),IFNULL('a.remark', '')) LIKE '%"+grade+"%'and a.drillname='"+drillname+"' ");
        sqlBuilder.append(" GROUP BY a.id order by "+c+" "+order+" LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(page);
        params.add(pagesize);

        if (params.isEmpty()) {
            return Db.find(sqlBuilder.toString());
        } else {
            return Db.find(sqlBuilder.toString(), params.toArray());
        }
    }

    public Long findConunt(String search) {
        if(StringUtils.isEmpty(search)){
            return DAO.doFindCount();
        }else{
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" where CONCAT(IFNULL(drillname, ''),IFNULL(grade, ''),IFNULL(name, ''),IFNULL(remark, '')) LIKE '%"+search+"%' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }

    public Long findCondition(String grade,String drillname) {
        if(StringUtils.isEmpty(grade)){
            return DAO.doFindCount();
        }else{
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" where CONCAT(IFNULL('drillname', ''),IFNULL(grade, ''),IFNULL('name', ''),IFNULL('remark', '')) LIKE '%"+grade+"%' and drillname='"+drillname+"' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }




}
