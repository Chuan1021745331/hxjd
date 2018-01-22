package com.base.query;


import com.base.model.JTbmrepair;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmrepairQuery
 * @包名: com.base.query
 * @描述: (盾构机维修记录查询)
 * @所属: 华夏九鼎
 * @日期: 2018/1/22 10:12
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TbmrepairQuery {
    protected static final JTbmrepair DAO = new JTbmrepair();
    private static final TbmrepairQuery QUERY = new TbmrepairQuery();

    public static TbmrepairQuery me() {
        return QUERY;
    }

    public JTbmrepair findByTbmrepairId(int tbmrepairId){
        return DAO.findFirst("select * from j_tbmrepair where id="+tbmrepairId);
    }

    public List<JTbmrepair> findByTbmId(int page,int limit,int tbmId){
        StringBuilder sql=new StringBuilder("select * from j_tbmrepair ");
        sql.append(" where tbmId=? ");
        sql.append(" order by id desc limit ?,?");
        LinkedList<Object> params = new LinkedList<>();
        params.add(limit*page-limit);
        params.add(limit);
        return DAO.find(sql.toString(),params.toArray());
    }
    public List<JTbmrepair> findByTbmId(int tbmId){
        return DAO.find("select * from j_tbmrepair where tbmId="+tbmId);
    }

    public long  findCountByTbmId(int tbmId){
        return Db.queryLong("select count(*) from j_tbmrepair where tbmId=" + tbmId);
    }

    public long findCountTbmrepairTbmUser(){
        StringBuilder sql=new StringBuilder("select count(*)");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id ");
        return Db.queryLong(sql.toString());
    }

    public List<Record> findListTbmrepairTbmUser(int page, int limit,String where){
        StringBuilder sql=new StringBuilder("select tr.*,t.name as tbmName,u.relname as writer");
        sql.append(" from j_tbmrepair tr left join j_tbm t on tr.tbmId=t.id left join j_user u on tr.userId=u.id ");
        sql.append(" order by tr.id desc LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(limit*page-limit);
        params.add(limit);

        return Db.find(sql.toString(), params.toArray());
    }


}
