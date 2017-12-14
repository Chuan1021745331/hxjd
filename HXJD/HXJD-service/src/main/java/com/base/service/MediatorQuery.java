package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JMediator;
import com.base.model.JOption;
import com.base.model.JSeat;
import com.base.model.JTerminal;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd on 2017/5/22.
 */
public class MediatorQuery extends JBaseQuery{
    protected static final JMediator DAO=new JMediator();
    private static final MediatorQuery QUERY=new MediatorQuery();
    public static MediatorQuery me() {
        return QUERY;
    }
    public JMediator findById(final Integer terminalId) {
//        return DAO.getCache(terminalId, new IDataLoader() {
//            @Override
//            public Object load() {
        return DAO.findById(terminalId);
//            }
//        });
    }

    public List<JMediator>getAll(){
        return DAO.find("select * from j_mediator order by id asc");
    }

    public List<JMediator> getMediatorId(int id){
        return DAO.find("SELECT m.* FROM j_mediator m LEFT JOIN j_objectmed om ON om.mediatorId = m.id WHERE om.objectId  =?",id);
    }

    public List<JMediator> getExist(){
        return DAO.find("SELECT m.* FROM j_mediator m LEFT JOIN j_objectmed om ON om.mediatorId = m.id WHERE om.id is NOT NULL");
    }

    public List<JMediator> getMyMediator(int id){
        return DAO.find("SELECT m.* FROM j_mediator m LEFT JOIN j_objectmed om ON om.mediatorId = m.id WHERE om.objectId = ?",id);
    }


    public List<Record> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT\n" +
                "\t\t\tm.id,\n" +
                "\t\t\tm.mediator_name,\n" +
                "\t\t\tm.camp,\n" +
                "\t\t\ts.seatname,\n" +
                "\t\t\ts.id AS sid,\n" +
                "\t\t\tm.x,\n" +
                "\t\t\tm.y,\n" +
                "\t\t\tm.mediator_stauts,\n" +
                "\t\t\tm.mediator_grade,\n" +
                "\t\t\tm.mediator_stauts\n" +
                "\t\tFROM\n" +
                "\t\t\tj_mediator m\n" +
                "\t\tLEFT JOIN j_medseat ms ON ms.mediatorId = m.id\n" +
                "\t\tLEFT JOIN j_seat s ON s.id = ms.seatId JOIN j_drillcode dc ON dc.id = m.drillId ");

        String c=column==0?"m.id":column==1?"m.mediator_name":column==2?"m.camp":column==3?"m.x":column==4?"m.y":column==5?"m.mediator_stauts":"m.mediator_grade";
        sqlBuilder.append(" WHERE CONCAT( IFNULL(m.id, ''),IFNULL(m.mediator_name, ''),IFNULL(m.camp, ''),IFNULL(m.x, ''),IFNULL(m.y, ''),IFNULL(m.mediator_stauts, ''),IFNULL(m.mediator_grade, '')) LIKE '%"+search+"%' ");
        //
        sqlBuilder.append(" AND dc.state = 0 ");
        
        sqlBuilder.append(" order by "+c+" "+order+" LIMIT ?, ? ");
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
    	StringBuilder sqlBuilder = new StringBuilder("SELECT\n" +
                "\t\t\tcount(m.id)\n" +
                "\t\tFROM\n" +
                "\t\t\tj_mediator m\n" +
                "\t\tLEFT JOIN j_medseat ms ON ms.mediatorId = m.id\n" +
                "\t\tLEFT JOIN j_seat s ON s.id = ms.seatId JOIN j_drillcode dc ON dc.id = m.drillId ");
    	
    	
    	if(StringUtils.isEmpty(search)){
    		sqlBuilder.append(" WHERE dc.state = 0 ");
    		//return DAO.doFindCount();
        }else{
            //StringBuilder sqlBuilder = new StringBuilder();
        	sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(mediator_name, ''),IFNULL(camp, ''),IFNULL(x, ''),IFNULL(y, ''),IFNULL(mediator_stauts, ''),IFNULL(mediator_grade, '')) LIKE '%"+search+"%' ");
        	sqlBuilder.append(" AND dc.state = 0 ");
            //return DAO.doFindCount(sqlBuilder.toString());
        }
    	return Db.queryLong(sqlBuilder.toString());
    }

    public List<JMediator> getMediatorByobjectName(String name,String drillname){
        return DAO.find("SELECT m.* FROM j_mediator m INNER  JOIN j_objectmed om ON om.mediatorId = m.id INNER JOIN j_object o ON om.objectId = o.id  WHERE  o.id = ? AND o.drillname = ?",name,drillname);
    }

    public List<JMediator> getMediatorRedByobjectName(String name,String drillname){
        return DAO.find("SELECT m.* FROM j_mediator m INNER  JOIN j_objectmed om ON om.mediatorId = m.id INNER JOIN j_object o ON om.objectId = o.id  WHERE  m.camp = 0 AND o.id = ? AND o.drillname = ?",name,drillname);
    }

    public List<JMediator> getMediatorBlueByobjectName(String name,String drillname){
        return DAO.find("SELECT m.* FROM j_mediator m INNER  JOIN j_objectmed om ON om.mediatorId = m.id INNER JOIN j_object o ON om.objectId = o.id  WHERE  m.camp = 1 AND o.id = ? AND o.drillname = ?",name,drillname);
    }


/*    *//**
     * 查询本训练中所有没有被添加进群的调理员
     * @return
     *//*
    public List<JMediator>getMediatorToGroup(){
        return DAO.find("SELECT jm.id,jm.mediator_name,jm.mediator_grade,jm.camp,jm.mediator_stauts FROM j_mediator jm WHERE jm.id not in(SELECT jgm.mediatorId FROM j_groupMediator jgm WHERE jgm.groupId NOT IN (1,2,3))");
    }*/
    public List<JMediator> getMediatorToGroup(int groupId, int drillId){
        //return DAO.find("SELECT jm.id,jm.mediator_name,jm.mediator_grade,jm.camp,jm.mediator_stauts FROM j_mediator jm JOIN j_group g ON g.drillId = jm.drillId WHERE g.id = ? AND jm.id NOT IN ( SELECT jgm.mediatorId FROM j_groupMediator jgm WHERE jgm.groupId = ? )", id, id);
    	return DAO.find("SELECT * FROM j_mediator jm WHERE jm.id NOT IN (SELECT gm.mediatorId FROM j_groupmediator gm JOIN j_groupDrill gd ON gm.groupId = gd.groupId WHERE gd.drillId = ? AND gm.groupId = ?) AND jm.drillId = ?", drillId, groupId, drillId);
    }
    
    /**
     * 查询群组的调度员
     * @param groupId
     * @return
     */
    public List<JMediator>getMediatorByGroupId(int groupId){
        return DAO.find("SELECT jm.id,jm.mediator_name,jm.mediator_grade,jm.camp,jm.mediator_stauts FROM j_groupmediator jgm INNER JOIN j_mediator jm ON jgm.mediatorId=jm.id INNER JOIN j_drillcode dc ON dc.id = jm.drillId  WHERE dc.state = 0 AND jgm.groupId = ? ",groupId);
    }

    public List<JMediator> getSeatMediator(int id){
        return DAO.find("SELECT m.* FROM j_mediator m LEFT JOIN j_medseat om ON om.mediatorId = m.id WHERE om.seatId = ?",id);
    }
    
    public JMediator getMediatorByTerminalId(int terminalId){
    	return DAO.findFirst("select m.* from  j_mediator m INNER join j_mediatorterminal mt where m.id = mt.tmediator_id and mt.terminal_id = ?", terminalId);
    	
    }
    
    public List<JMediator> getByCurrentDrill(){
    	return DAO.find("SELECT m.id, m.mediator_name, m.camp, m.x ,m.y ,m.mediator_stauts, m.mediator_grade FROM j_mediator m INNER JOIN j_drillcode dc ON dc.id = m.drillId WHERE dc.state = 0 "); 
    }
    
    /**
     * 
     * getByTerminalMac
     * 根据设备码、SD码查询当前训练对应的调理员
     * @param devMac
     * @param sdMac
     * @return   
     *JMediator  
     * @exception   
     * @since  1.0.0
     */
    public JMediator getByTerminalMac(String devMac, String sdMac){
    	
    	return DAO.findFirst("select * from j_terminal t join j_mediatorterminal mt on mt.terminal_id = t.id join j_mediator m on m.id = mt.mediator_id join j_drillcode dc on dc.id = m.drillId where dc.state = 0 and t.terminal_num = ? and t.sdnum = ?", devMac, sdMac);
    }
    
    public void deleteByDrill(int drillId){
    	DAO.doDelete(" drillId = ?", drillId);
    }
}
