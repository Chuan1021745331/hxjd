package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JDrillcode;
import com.base.model.JGroup;
import com.base.model.JMediator;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: GroupQuery
 * @包名: com.base.service
 * @描述: 群组管理sql
 * @所属: 华夏九鼎
 * @日期: 2017/6/1 11:11
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class GroupQuery {
    protected static final JGroup DAO = new JGroup();
    private static final GroupQuery QUERY = new GroupQuery();
    public static GroupQuery me() {
        return QUERY;
    }
    public JGroup findById(final Integer id) {
//        return DAO.getCache(terminalId, new IDataLoader() {
//            @Override
//            public Object load() {
        return DAO.findById(id);
//            }
//        });
    }
    public List<Record> findList(int page, int pagesize, int column, String order, String search) {
    	JDrillcode drill =  DrillcodeQuery.me().getActiveDrill();
    	int drillId = 0;
    	if(drill != null){
    		drillId = drill.getId();
    	}
    	//SELECT *,IFNULL(COUNT(m.id),0) AS counts  FROM  j_group g JOIN j_groupdrill gd ON gd.groupId = g.id AND gd.drillId = 2 LEFT JOIN j_groupmediator gm ON gm.groupId = g.id LEFT JOIN j_mediator m ON m.id = gm.mediatorId AND m.drillId = 2
    	//StringBuilder sqlBuilder = new StringBuilder("SELECT g.*,IFNULL(COUNT(m.id),0) AS counts  FROM j_mediator m LEFT JOIN j_groupmediator gp ON gp.mediatorId = m.id LEFT JOIN j_group g ON g.id = gp.groupId ");
        //
        //sqlBuilder.append("LEFT JOIN j_groupdrill gd ON gd.groupId = g.id LEFT JOIN j_drillcode dc ON dc.id = gd.drillId AND m.drillId = dc.id ");
    	StringBuilder sqlBuilder = new StringBuilder("SELECT g.id, g.groupName, g.remark, g.camp, IFNULL(COUNT(m.id),0) AS counts  FROM  j_group g JOIN j_groupdrill gd ON gd.groupId = g.id AND gd.drillId = " + drillId + " LEFT JOIN j_groupmediator gm ON gm.groupId = g.id LEFT JOIN j_mediator m ON m.id = gm.mediatorId AND m.drillId = " + drillId);
    			
        String c=column==0?"id":column==1?"groupName":"remark";
        sqlBuilder.append(" WHERE CONCAT( IFNULL(g.id, ''),IFNULL(g.groupName, ''),IFNULL(g.remark, '')) LIKE '%"+search+"%' ");
        //
        
        sqlBuilder.append("GROUP BY g.id order by "+c+" "+order+" LIMIT ?, ? ");
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
        /*if(StringUtils.isEmpty(search)){
            return DAO.doFindCount();
        }else{
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" WHERE CONCAT( IFNULL(jg.id, ''),IFNULL(jg.groupName, ''),IFNULL(jg.remark, '')) LIKE '%"+search+"%' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }*/
    	StringBuilder sqlBuilder = new StringBuilder("SELECT COUNT(*) FROM  j_group g JOIN j_groupdrill gd ON gd.groupId = g.id JOIN j_drillcode dc ON dc.id = gd.drillId AND dc.state = 0 ");
    	if (com.base.utils.StringUtils.isNotEmpty(search)){
    		sqlBuilder.append(" WHERE CONCAT( IFNULL(g.id, ''),IFNULL(g.groupName, ''),IFNULL(g.remark, '')) LIKE '%"+search+"%' ");
    		return Db.queryLong(sqlBuilder.toString());
    	} else {
    		return Db.queryLong(sqlBuilder.toString());
    	}
    	
    	/*if(!StringUtils.isEmpty(search)){//不为空
    		sqlBuilder.append(" WHERE CONCAT( IFNULL(g.id, ''),IFNULL(g.groupName, ''),IFNULL(g.remark, '')) LIKE '%"+search+"%' AND dc.state = 0 GROUP BY m.id) a ");        		
    	} else {
    		sqlBuilder.append(" WHERE dc.state = 0 GROUP BY m.id) a "); 
    	}
    	return Db.queryLong(sqlBuilder.toString());
    	*/
    }

    /**
     * 群组获得未分配的组员
     * @return
     */
    public List<JMediator>getAllMediator(int groupId, int drillId){
        return MediatorQuery.me().getMediatorToGroup(groupId, drillId);
    }
    public List<JMediator>getGroupMediator(int groupId){
        return MediatorQuery.me().getMediatorByGroupId(groupId);
    }

    /**
     * 获得除了基础群以外的群组
     * @return
     */
    public List<JGroup> getAllGroup(){
        //return DAO.doFind("id not in ('1','2','3')");
    	return DAO.find("SELECT g.* FROM j_group g LEFT JOIN j_groupdrill gd ON gd.groupId = g.id LEFT JOIN j_drillcode dc ON dc.id = gd.drillId WHERE dc.state = 0 AND g.camp IS NULL");
    }

    /**
     * 查询被饭配的群组
     * @param meId
     * @return
     */
    public List<JGroup>getGroupByMedorId(int meId){
        return DAO.find("SELECT jg.* FROM j_group jg INNER JOIN j_groupmediator jgm ON jg.id=jgm.groupId WHERE jgm.groupId not in (1,2,3) and jgm.mediatorId=?",meId);
    }

    /**
     * 返回没有匹配的群组
     * @param medorID
     * @return
     */
    public List<JGroup>getNotIn(int medorID){
        return DAO.find("SELECT jg.* FROM j_group jg WHERE jg.id not in (SELECT jgm.groupId FROM j_groupmediator jgm WHERE jgm.mediatorId=?) and jg.id NOT in (1,2,3)",medorID);
    }
    /**
     * 根据设备的sdNum和terminalNum来获取所属群主
     * @param
     * @return
     */
     public List<JGroup> getGroupByTerminal(String terminal_num,String sdnum) {
        return DAO.find("select jg.* from j_terminal jt\n" +
                "INNER join j_mediatorterminal jmt on jt.id = jmt.terminal_id\n" +
                "INNER join j_mediator jm on jmt.mediator_id = jm.id JOIN j_drillcode dc ON jm.drillId = dc.id\n" +
                "INNER join j_groupmediator jgm on jm.id = jgm.mediatorId\n" +
                "INNER join j_group jg on jgm.groupId = jg.id\n" +
                "where dc.state = 0 AND jt.terminal_num = ? and jt.sdnum= ?",terminal_num,sdnum);
     }

    /**
     * 通过mediatorId查询出所属群
     * @param mediatorId
     * @return
     */
     public List<JGroup> GainGroupByMediatorId(int mediatorId){
         return DAO.find("SELECT jg.* FROM j_group jg INNER JOIN j_groupmediator jgm ON jg.id=jgm.groupId WHERE jgm.mediatorId=?",mediatorId);
     }
     
     /**
      * 
      * getGroupByCamp  
      * (这里描述这个方法适用条件 – 可选)  
      * @param camp
      * @return   
      *JGroup  
      * @exception   
      * @since  1.0.0
      */
     public JGroup getGroupByCamp(int camp){
    	 return DAO.findFirst("SELECT * FROM j_group WHERE camp = ?", camp);
    	 //return DAO.findFirst("SELECT g.* FROM j_group g JOIN j_groupdrill gd ON gd.groupId = g.id JOIN j_drillcode dc ON dc.id =  gd.drillId WHERE dc.state = 0");
     }
     
     /**
      * 
      * GainCampByMediatorId(这里用一句话描述这个方法的作用)  
      * 根据groupId得到阵营的Group记录 
      * @param mediatorId
      * @return   
      */
     public JGroup GainCampByMediatorId(int mediatorId){
         return DAO.findFirst("SELECT jg.* FROM j_group jg INNER JOIN j_groupmediator jgm ON jg.id=jgm.groupId WHERE jgm.mediatorId=? and jg.id != 1 AND jg.camp is not null ",mediatorId);
     }
     
}
