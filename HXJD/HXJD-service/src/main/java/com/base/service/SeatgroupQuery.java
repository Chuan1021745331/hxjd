package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JPelgrouppel;
import com.base.model.JSeatpelgroup;
import com.jfinal.plugin.activerecord.Db;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class SeatgroupQuery {
    protected static final JSeatpelgroup  DAO = new JSeatpelgroup();
    private static final SeatgroupQuery QUERY = new SeatgroupQuery();

    public static SeatgroupQuery me() {
        return QUERY;
    }

    public JSeatpelgroup findById(final Integer pelId) {
                return DAO.findById(pelId);
    }

    public List<JSeatpelgroup> getgroupId(int id){
        return DAO.find("select j.pelgroupId from j_seatpelgroup j WHERE seatId =?",id);
    }

    public Boolean delectByPelgroupId(int id){
        int b = DAO.doDelete("seatId =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }

    public  List<JSeatpelgroup> seatIsExist(int id){
        return DAO.doFind("pelgroupId =? ",id);
    }

    public Boolean delectBygroupId(int id){
        int b = DAO.doDelete("pelgroupId =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }
    
    public Boolean deleteBySeatId(int id){
        int b = DAO.doDelete("seatId =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }
    
    
    public List<JSeatpelgroup> findByTerminal(String terminal_num,String sdnum) {
    	String sql = "select jspg.* from j_terminal jt INNER join j_mediatorterminal jmt on jt.id = jmt.terminal_id INNER join j_mediator jm on jmt.mediator_id =  jm.id INNER join j_medseat jms on  jm.id = jms.mediatorId INNER join j_seat js on jms.seatId = js.id INNER join j_seatpelgroup jspg  on js.id = jspg.seatId  where jt.terminal_num=? and jt.sdnum=?";
    	return DAO.find(sql, terminal_num,sdnum);
    	//return null;
    }
    

}
