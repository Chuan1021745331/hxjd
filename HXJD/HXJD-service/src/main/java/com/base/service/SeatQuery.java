package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JPel;
import com.base.model.JSeat;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/22.
 */
public class SeatQuery {
    protected static final JSeat DAO = new JSeat();
    private static final SeatQuery QUERY = new SeatQuery();

    public static SeatQuery me() {
        return QUERY;
    }

    public JSeat findById(final Integer pelId) {
                return DAO.findById(pelId);
    }

    public List<JSeat> getAll(){
        return DAO.find("select * from j_seat order by id asc");
    }

    public List<JSeat> getGroupAll(int id){
        return DAO.find("select p.pelname, p.id from j_pelgrouppel pg inner join j_pel p on p.id = pg.pel_id where pg.pelgroup_id="+id+"");
    }

    public List<JSeat> getSeatAll(int id){
        return DAO.find("select s.seatname, s.id from j_medseat m inner join j_seat s on s.id = m.seatId where m.mediatorId ="+id+"");
    }

    public List<Record> findList(int page, int pagesize, int column, String order, String search) {
//        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM ( SELECT s.id,s.camp,s.remark,s.directing,s.seatname,pp.plegroupname FROM j_seat s INNER JOIN j_seatpelgroup sg ON sg.seatId = s.id INNER JOIN j_pelgroup pp ON pp.id = sg.pelgroupId");
        StringBuilder sqlBuilder = new StringBuilder("SELECT a.id,a.seatname,a.camp,a.remark, count(a.plegroupname) AS counts,a.directing \n" +
                "FROM (\n" +
                "SELECT\n" +
                "\tjs.id as id ,js.seatname,js.camp,js.remark,js.directing,jp.plegroupname\n" +
                "FROM\n" +
                "\tj_seat js\n" +
                "LEFT JOIN j_seatpelgroup jsp ON jsp.seatId = js.id LEFT JOIN j_pelgroup jp ON jp.id = jsp.pelgroupId JOIN j_drillcode dc ON dc.id = js.drill WHERE dc.state = 0 ORDER by js.id) a");
        String c=column==0?"a.seatname":column==1?"a.directing":"a.camp";
        sqlBuilder.append(" WHERE CONCAT(IFNULL(a.seatname, ''),IFNULL(a.directing, ''),IFNULL(a.camp, '')) LIKE '%"+search+"%'");
        sqlBuilder.append(" GROUP BY a.id ORDER BY "+c+" "+order+" LIMIT ?, ? ");
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
        StringBuilder sqlBuilder = new StringBuilder(" SELECT COUNT(a.id) FROM (SELECT js.id FROM j_seat js LEFT JOIN j_seatpelgroup jsp ON jsp.seatId = js.id LEFT JOIN j_pelgroup jp ON jp.id = jsp.pelgroupId JOIN j_drillcode dc ON dc.id = js.drill  ");
    	if(StringUtils.isEmpty(search)){
    		sqlBuilder.append("WHERE  dc.state = 0 GROUP BY js.id ) a");
    		//return DAO.doFindCount();
        }else{
            sqlBuilder.append(" where CONCAT( IFNULL('seatname', ''),IFNULL(camp, ''),IFNULL(directing, '')) LIKE '%"+search+"%' AND dc.state = 0 GROUP BY js.id ) a");

            //return DAO.doFindCount(sqlBuilder.toString());
        }
    	return Db.queryLong(sqlBuilder.toString());
    }

    public List<JSeat> getOneSeat(int id){
        return DAO.find("SELECT s.* FROM j_seatpelgroup sp LEFT JOIN j_seat s ON sp.seatId = s.id WHERE sp.pelgroupId ="+id+"");
    }
    
    public List<JSeat> getUnselectedSeat(){
    	return DAO.find("SELECT s.id, s.seatname, s.camp, s.directing, s.remark FROM j_seat s WHERE s.id NOT IN (SELECT id FROM j_seatdrill)");
    	
    }

    public List<JSeat> getSelectedSeat(int id){
    	return DAO.find("SELECT s.id, s.seatname, s.camp, s.directing, s.remark FROM j_seat s JOIN j_seatdrill sd ON s.id = sd.seatId WHERE drillId = ?", id);
    	
    }
    
    /**
     * 
     * getByCurrentDrill
     * 得到当前训练所辖的席位
     * @return   
     * List<JSeat>  
     * @exception   
     * @since  1.0.0
     */
    public List<JSeat> getByCurrentDrill(){
    	return DAO.find("SELECT s.id,s.seatname,s.camp,s.camp,s.remark FROM j_seat s JOIN j_drillcode dc ON s.drill = dc.id WHERE dc.state = 0 ");
    }

}
