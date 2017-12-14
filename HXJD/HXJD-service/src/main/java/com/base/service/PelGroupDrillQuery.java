package com.base.service;

import com.base.model.JPelgroupdrill;


public class PelGroupDrillQuery {
    protected static final JPelgroupdrill DAO = new JPelgroupdrill();
    private static final PelGroupDrillQuery QUERY = new PelGroupDrillQuery();

    public static PelGroupDrillQuery me() {
        return QUERY;
    }
    
    public void deleteByPelgroupId(int pelgroupId, int drillId){
        DAO.doDelete("pelgroupId =? and  drillId = ? ", pelgroupId, drillId);
    }
    
    public void deleteByDrillId(int drillId){
        DAO.doDelete("drillId = ? ",  drillId);
    }
    
    public JPelgroupdrill getByPelGroup(int pelGroupId){
    	return  DAO.findFirst("SELECT * FROM j_pelgroupdrill WHERE pelgroupId = ? ", pelGroupId);
    }
}
