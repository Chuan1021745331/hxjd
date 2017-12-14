package com.base.service;

import com.base.model.JMedseat;
import com.base.model.JSeat;
import com.base.model.JSeatpelgroup;

import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class MeaSeatQuery {
    protected static final JMedseat DAO = new JMedseat();
    private static final MeaSeatQuery QUERY = new MeaSeatQuery();

    public static MeaSeatQuery me() {
        return QUERY;
    }

    public JMedseat findById(final Integer pelId) {
                return DAO.findById(pelId);
    }
    /**
     * 
     * getAll
     * (当前训练所有的)  
     * @return   
     *List<JMedseat>  
     * @exception   
     * @since  1.0.0
     */
    public List<JMedseat> getAll(){
        //return DAO.find("select * from j_medseat order by id asc");
    	return DAO.find("SELECT jms.* FROM j_medseat jms LEFT JOIN j_seat js ON js.id = jms.seatId LEFT JOIN j_drillcode dc ON dc.id = js.drill WHERE dc.state = 0  ORDER BY js.id ASC");
    }

    public List<JMedseat> getgroupId(int id){
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

    public  List<JMedseat> seatIsExist(int id){
        return DAO.doFind("seatId =? ",id);
    }

    public Boolean delectByMedId(int id){
        int b = DAO.doDelete("mediatorId =?",id);
        if (b != 0){
            return true;
        }else {
            return false;
        }
    }

    /**
     * by seatId
     * @param seatId
     * @return
     */
    public JMedseat GainMediatorId(int seatId){
        return DAO.doFindFirst("seatId=?",seatId);
}


}
