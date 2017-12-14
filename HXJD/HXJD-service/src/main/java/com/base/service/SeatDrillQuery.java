package com.base.service;

import com.base.model.JSeatdrill;

public class SeatDrillQuery {
	protected static final JSeatdrill  DAO = new JSeatdrill();
    private static final SeatDrillQuery QUERY = new SeatDrillQuery();

    public static SeatDrillQuery me() {
        return QUERY;
    }
    
    public int deleteByDrill(int id ){
    	return DAO.doDelete("drillId = ?", id);
    }
    
    public JSeatdrill getBySeatId(int seatId){
    	return DAO.findFirst("SELECT s.id, s.seatId, s.drillId FROM j_seatdrill s where s.seatId = ?", seatId);
    }
}
