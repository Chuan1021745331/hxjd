package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JPel;
import com.base.model.JUsergrade;
import com.base.socket.SocketService;
import com.base.utils.GsonUtil;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.Db;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class UserGradeQuery {
	
	private static Log log=Log.getLog(UserGradeQuery.class);
	
    protected static final JUsergrade DAO = new JUsergrade();
    private static final UserGradeQuery QUERY = new UserGradeQuery();
    
    public static List<JUsergrade> jPelList = new ArrayList<JUsergrade>();

    public static UserGradeQuery me() {
        return QUERY;
    }

    public JUsergrade findById(final Integer pelId) {
        return DAO.findById(pelId);
    }

    public List<JUsergrade> getAll() {
        return DAO.find("select * from j_usergrade order by id asc");
    }

    public List<JUsergrade> getGroupAll(int id) {
        return DAO.find("select p.pelname, p.id from j_pelgrouppel pg inner join j_pel p on p.id = pg.pel_id where pg.pelgroup_id=" + id + "");
    }

    public List<JUsergrade> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("select * from j_usergrade p");
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
    /**
     * 查找自身所属图元
     * @param
     * @return
     */
    public List<JUsergrade> getPelByTerminal(String terminal_num,String sdnum) {
        return DAO.find("select jp.* from j_terminal jt\n" +
                "INNER join j_mediatorterminal jmt on jt.id = jmt.terminal_id\n" +
                "INNER join j_mediator jm on jmt.mediator_id =  jm.id\n" +
                "INNER join j_medseat jms on  jm.id = jms.mediatorId\n" +
                "INNER join j_seat js on jms.seatId = js.id\n" +
                "INNER join j_seatpelgroup jspg  on js.id = jspg.seatId\n" +
                "INNER join j_pelgroup jpg on jspg.pelgroupId = jpg.id\n" +
                "INNER join j_pelgrouppel jpgp on jpg.id = jpgp.pelgroup_id\n" +
                "INNER join j_pel jp on jpgp.pel_id = jp.id\n" +
                "where jt.terminal_num=? and jt.sdnum=? GROUP BY jp.id",terminal_num,sdnum);
    }

    /**
     * 通过termainId查询JPel
     * @param termainId
     * @return
     */
    public List<JUsergrade>GainPelByJTera(int termainId){
        return DAO.find("SELECT jp.* FROM j_terminal jt INNER JOIN j_mediatorterminal jmt ON jt.id=jmt.terminal_id INNER JOIN j_medseat jms ON jmt.mediator_id=jms.mediatorId INNER JOIN j_seatpelgroup jsg ON jms.seatId=jsg.seatId INNER JOIN j_pelgrouppel jpg ON jsg.pelgroupId=jpg.pelgroup_id INNER JOIN j_pel jp ON jpg.pel_id=jp.id WHERE jt.id=? GROUP BY jp.id",termainId);
    }
    
    public int updataPelState(){
    	return Db.update("update j_pel set state=0 where state=1 ");
    }

}
