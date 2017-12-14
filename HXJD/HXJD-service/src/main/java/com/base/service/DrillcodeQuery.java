package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JDrillcode;
import com.base.model.JUsergrade;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class DrillcodeQuery {
	
	private static Log log=Log.getLog(DrillcodeQuery.class);
	
    protected static final JDrillcode DAO = new JDrillcode();
    private static final DrillcodeQuery QUERY = new DrillcodeQuery();
    
    public static List<JUsergrade> jPelList = new ArrayList<JUsergrade>();

    public static DrillcodeQuery me() {
        return QUERY;
    }

    public JDrillcode findById(final Integer drillId) {
        return DAO.findById(drillId);
    }

    public List<JDrillcode> getAll() {
        return DAO.find("select * from j_drillcode order by id asc");
    }

    public List<JDrillcode> getGroupAll(int id) {
        return DAO.find("select p.pelname, p.id from j_pelgrouppel pg inner join j_pel p on p.id = pg.pel_id where pg.pelgroup_id=" + id + "");
    }

    public List<JDrillcode> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("select * from j_drillcode p");
        String c = column == 0 ? "name" : "time";
        sqlBuilder.append(" WHERE CONCAT(IFNULL(name, ''),IFNULL(time, '')) LIKE '%" + search + "%' ");
        sqlBuilder.append(" order by " + c + " " + order + " LIMIT ?, ? ");
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
        if (StringUtils.isEmpty(search)) {
            return DAO.doFindCount();
        } else {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" where CONCAT(IFNULL(name, ''),IFNULL(time, '')) LIKE '%" + search + "%' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }
    
    //==========================================================================
    public List<JDrillcode> findList_(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("select * from j_drillcode p");
        String c = column == 0 ? "name" : "time";
        sqlBuilder.append(" WHERE CONCAT(IFNULL(name, ''),IFNULL(time, '')) LIKE '%" + search + "%' ");
        sqlBuilder.append(" order by " + c + " " + order + " LIMIT ?, ? ");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(page);
        params.add(pagesize);

        if (params.isEmpty()) {
            return DAO.find(sqlBuilder.toString());
        } else {
            return DAO.find(sqlBuilder.toString(), params.toArray());
        }
    }
    
    public Long findConunt_(String search) {
        if (StringUtils.isEmpty(search)) {
            return DAO.doFindCount();
        } else {
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" where CONCAT(IFNULL(name, ''),IFNULL(time, '')) LIKE '%" + search + "%' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }
    
    //==========================================================================


    /**
     * 通过termainId查询JPel
     * @param termainId
     * @return
     */
    public List<JDrillcode>GainPelByJTera(int termainId){
        return DAO.find("SELECT jp.* FROM j_terminal jt INNER JOIN j_mediatorterminal jmt ON jt.id=jmt.terminal_id INNER JOIN j_medseat jms ON jmt.mediator_id=jms.mediatorId INNER JOIN j_seatpelgroup jsg ON jms.seatId=jsg.seatId INNER JOIN j_pelgrouppel jpg ON jsg.pelgroupId=jpg.pelgroup_id INNER JOIN j_pel jp ON jpg.pel_id=jp.id WHERE jt.id=? GROUP BY jp.id",termainId);
    }
    
    public int updataPelState(){
    	return Db.update("update j_pel set sstate=0 where state=1 ");
    }
    
    public JDrillcode getActiveDrill(){//同时最多只能进行一次训练
    	return  DAO.findFirst("SELECT * FROM j_drillcode WHERE state = 0");
    }
    public JDrillcode getDrillBySeatId(int id){//一个席位对应一次训练
    	return  DAO.findFirst("SELECT dc.* FROM j_seatdrill sd JOIN j_drillcode dc ON dc.id = sd.drillId WHERE sd.seatId = ?", id);
    	
    }

    
}
