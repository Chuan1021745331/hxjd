package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JMediator;
import com.base.model.JOption;
import com.base.model.JTerminal;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.IDataLoader;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd on 2017/5/22.
 */
public class TerminalQuery extends JBaseQuery {
    protected static final JTerminal DAO=new JTerminal();
    private static final TerminalQuery QUERY=new TerminalQuery();
    public static TerminalQuery me() {
        return QUERY;
    }
    public JTerminal findById(final Integer terminalId) {
//        return DAO.getCache(terminalId, new IDataLoader() {
//            @Override
//            public Object load() {
                return DAO.findById(terminalId);
//            }
//        });
    }
    public List<JTerminal> findList(int page, int pagesize, int column, String order, String search) {
        StringBuilder sqlBuilder = new StringBuilder("select * from j_terminal u ");
        String c=column==0?"id":column==1?"terminal_name":column==2?"terminal_num":column==3?"sdnum":"terminal_stauts";
        sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(terminal_name, ''),IFNULL(terminal_num, ''),IFNULL(sdnum, ''),IFNULL(terminal_stauts, '')) LIKE '%"+search+"%' ");
        sqlBuilder.append(" order by "+c+" "+order+" LIMIT ?, ? ");
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
        if(StringUtils.isEmpty(search)){
            return DAO.doFindCount();
        }else{
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(terminal_name, ''),IFNULL(terminal_num, ''),IFNULL(sdnum, ''),IFNULL(terminal_stauts, '')) LIKE '%"+search+"%' ");
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }

    /**
     * 得到所有的终端
     * @return
     */
    public List<JTerminal> getAllTerminal(){
        return DAO.find("select * from j_terminal order by id asc");
    }

    /**
     * 得到没有被配备的终端
     * @return
     */
    public List<JTerminal> getNotInTM(){
        return DAO.find("SELECT jt.* FROM j_terminal jt WHERE jt.id not in(SELECT jm.terminal_id FROM j_mediatorterminal jm JOIN j_mediator m ON jm.mediator_id = m.id JOIN j_drillcode dc ON dc.id = m.drillId WHERE dc.state  = 0 )");
        //return DAO.find("");
    }

    /**
     * 通过mediatorId（调度员id）或得相应的终端信息
     * @param mediatorId
     * @return
     */
    public JTerminal getByMediator(int mediatorId){
        return DAO.findFirst("select jt.* from j_terminal jt left join j_mediatorterminal jm on jt.id=jm.terminal_id where jm.mediator_id=?",mediatorId);
    }

    /**
     * 通过sdnum查询终端
     * @param sdnum
     * @return
     */
    public JTerminal initializeTerminal(String sdnum){
        //return DAO.findFirst("select jt.* from j_terminal jt join j_mediatorterminal jm on jt.id=jm.terminal_id where jt.sdnum=?",sdnum);
        return DAO.findFirst("select jt.* from j_terminal jt join j_mediatorterminal jm on jt.id=jm.terminal_id JOIN j_mediator m ON m.id = jm.mediator_id JOIN j_drillcode dc ON dc.id = m.drillId AND dc.state = 0 where jt.sdnum = ? ",sdnum);
    }
    
    /**
     * 通过terminal_num查询终端
     * @param sdnum
     * @return
     */
    public JTerminal getTerminalByTernum(String terNum){
        return DAO.findFirst("select jt.* from j_terminal jt where jt.terminal_num=?",terNum);
    }
    /**
     * 通过sdNum查询terminal
     * @param sdnum
     * @return
     */
    public JTerminal getTerminalBysdnum(String sdNum){
        return DAO.findFirst("select jt.* from j_terminal jt where jt.sdnum=?",sdNum);
    }
    
    
    /**
     * 修改群组信息的时候，查找出这组所拥有的用户
     * @param groupId
     * @return
     */
    public List<Record> GainTerminaByGroupId(int groupId){
        //return Db.find("SELECT jt.*,jmt.mediator_id FROM j_groupmediator jgm INNER JOIN j_mediatorterminal jmt ON jgm.mediatorId=jmt.mediator_id INNER JOIN j_terminal jt ON jmt.terminal_id=jt.id WHERE jgm.groupId=?",groupId);
        return Db.find("SELECT jt.*,jmt.mediator_id FROM j_groupmediator jgm INNER JOIN j_mediatorterminal jmt ON jgm.mediatorId=jmt.mediator_id INNER JOIN j_terminal jt ON jmt.terminal_id=jt.id INNER JOIN j_mediator jm ON jm.id = jgm.mediatorId INNER JOIN j_drillcode dc ON dc.id = jm.drillId WHERE dc.state = 0 AND jgm.groupId = ?",groupId);
        
    }

    
    public List<JTerminal> getTerminaByGroupId(int groupId){
    	return DAO.find("SELECT jt.* FROM j_groupmediator jgm INNER JOIN j_mediatorterminal jmt ON jgm.mediatorId=jmt.mediator_id INNER JOIN j_terminal jt ON jmt.terminal_id=jt.id WHERE jgm.groupId=?",groupId);
    }
    
    /**
     * 通过图元id查询JTerminal
     * @param pelId
     * @return
     */
    public List<JTerminal>GainTerminaByPelId(int pelId){
        return DAO.find("SELECT jt.* FROM j_terminal jt INNER JOIN j_mediatorterminal jmt ON jt.id=jmt.terminal_id INNER JOIN j_medseat jms ON jmt.mediator_id=jms.mediatorId INNER JOIN j_seatpelgroup jsg ON jms.seatId=jsg.seatId INNER JOIN j_pelgrouppel jpg ON jsg.pelgroupId=jpg.pelgroup_id INNER JOIN j_pel jp ON jpg.pel_id=jp.id WHERE jp.id=? GROUP BY jt.id",pelId);
    }

    /**
     * 通过pelGroupId查询
     * @param pelGroupId
     * @return
     */
    public List<JTerminal>GainTerminalByPelGroupId(int pelGroupId){
        return DAO.find("SELECT jt.* FROM j_pelgroup jp INNER JOIN j_seatpelgroup jsg ON jp.id=jsg.pelgroupId INNER JOIN j_medseat jm ON jsg.seatId=jm.seatId INNER JOIN j_mediatorterminal jmt ON jm.mediatorId=jmt.mediator_id INNER JOIN j_terminal jt ON jmt.terminal_id=jt.id WHERE jp.id=?",pelGroupId);
    }
/*    public List<JTerminal> getNotInTMCurrent(){
        return DAO.find("SELECT jt.* FROM j_terminal jt WHERE jt.id not in(SELECT jm.terminal_id FROM j_mediatorterminal jm)");
    }*/
}
