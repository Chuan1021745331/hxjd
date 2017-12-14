package com.base.service;

import com.base.model.JGroupdrill;

public class GroupDrillQuery {
	protected static final JGroupdrill DAO = new JGroupdrill();
    private static final GroupDrillQuery QUERY = new GroupDrillQuery();
    public static GroupDrillQuery me() {
        return QUERY;
    }
    
    public void deleteByGroupId(int groupId, int drillId){
    	DAO.doDelete("groupId = ? and drillId= ? ", groupId, drillId);
    }
    
    public void deleteByDrillId(int drillId){
    	DAO.doDelete("drillId = ? ", drillId);
    }
}
