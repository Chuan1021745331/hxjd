package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JPelgroup;
import com.base.model.JPelgrouppel;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class PelgroupPelQuery {
    protected static final JPelgrouppel  DAO = new JPelgrouppel();
    private static final PelgroupPelQuery QUERY = new PelgroupPelQuery();

    public static PelgroupPelQuery me() {
        return QUERY;
    }

    public JPelgrouppel findById(final Integer pelId) {
                return DAO.findById(pelId);
    }


    public List<JPelgrouppel> getAll(){
        return DAO.find("select * from pelgroup_pel order by id asc");
    }

    public Boolean delectByPelgroupId(int id){
        int b = DAO.doDelete("pelgroup_id =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }

    public List<JPelgrouppel> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("select * from pelgroup_pel p");
        String c=column==0?"id":column==1?"pel_id":"pelgroup_id";
        sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL('pel_id', ''),IFNULL(pelgroup_id, '')) LIKE '%"+search+"%' ");
        sqlBuilder.append(" order by "+c+" "+order+" LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(page);
        params.add(pagesize);

        if (params.isEmpty()) {
            return DAO.find(sqlBuilder.toString());
        } else {
            return DAO.find(sqlBuilder.toString(), params.toArray());
        }
    }

    public Long findConunt(String search) {
        if(StringUtils.isEmpty(search)){
            return DAO.doFindCount();
        }else{
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" where CONCAT(IFNULL(pelgroup_id, '')) LIKE '%"+search+"%' ");

            return DAO.doFindCount(sqlBuilder.toString());
        }
    }




}
