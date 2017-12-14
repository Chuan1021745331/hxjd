package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JPel;
import com.base.model.JPelgroup;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class PelGroupQuery {
    protected static final JPelgroup DAO = new JPelgroup();
    private static final PelGroupQuery QUERY = new PelGroupQuery();

    public static PelGroupQuery me() {
        return QUERY;
    }

    public List<JPelgroup> getGroupAll(int id){
        return DAO.find("select p.id, p.plegroupname from j_seatpelgroup sg inner join j_pelgroup p on p.id = sg.pelgroupId where sg.seatId ="+id+"");
    }

    public JPelgroup findById(final Integer pelId) {
                return DAO.findById(pelId);
    }
    /**
     * 
     * getAll
     * 当前训练的所有图元组
     * @return   
     *List<JPelgroup>  
     * @exception   
     * @since  1.0.0
     */
    public List<JPelgroup> getAll(){
        return DAO.find("SELECT pg.* FROM j_pelgroup pg JOIN j_drillcode dc ON dc.id = pg.drill WHERE dc.state = 0  ORDER BY pg.id ASC");
    }

    public List<Record> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT a.id,a.state,a.camp,a.remark, count(a.pid) AS counts,a.plegroupname \n" +
                "FROM (\n" +
                "SELECT\n" +
                "\tpg.id as id ,pg.plegroupname,pg.camp,pg.remark,pg.state,p.id AS pid \n" +
                "FROM\n" +
                "\tj_pelgroup pg\n" +
                "LEFT JOIN j_pelgrouppel pgp ON pgp.pelgroup_id=pg.id LEFT JOIN j_pel p ON p.id=pgp.pel_id JOIN j_drillcode dc ON dc.id = pg.drill WHERE dc.state = 0 ORDER by p.id) a\n" +
                "");

        String c=column==0?"a.plegroupname":column==1?"a.camp":"a.state";
        sqlBuilder.append(" WHERE CONCAT( IFNULL(a.plegroupname, ''),IFNULL(a.camp, ''),IFNULL(a.state, '')) LIKE '%"+search+"%'");
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
    	 StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(id) FROM( SELECT pg.id FROM j_pelgroup pg LEFT JOIN j_pelgrouppel pgp ON pgp.pelgroup_id=pg.id LEFT JOIN j_pel p ON p.id = pgp.pel_id JOIN j_drillcode dc ON dc.id = pg.drill ");
    	 
    	 
    	
    	if(StringUtils.isEmpty(search)){
            sqlBuilder.append(" WHERE dc.state = 0 GROUP BY pg.id  ASC) a");
    		//return DAO.doFindCount();
        }else{
            //StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" where CONCAT( IFNULL(plegroupname, ''),IFNULL(camp, ''),IFNULL(state, '')) LIKE '%"+search+"%' AND dc.state = 0 GROUP BY pg.id  ASC) a");
            //return DAO.doFindCount(sqlBuilder.toString());
        }
    	return Db.queryLong(sqlBuilder.toString());
    }

    public List<JPelgroup> getByCurrentDrill(){
    	return DAO.find(" SELECT pg.* FROM j_pelgroup pg JOIN j_drillcode dc ON dc.id = pg.drill WHERE dc.state = 0 ");
    }

}
