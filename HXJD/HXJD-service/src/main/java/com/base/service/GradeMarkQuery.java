package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JGrademark;
import com.base.model.JKpi;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GradeMarkQuery extends JBaseQuery {
	protected static final JGrademark DAO = new JGrademark();
	private static final GradeMarkQuery QUERY = new GradeMarkQuery();

	public static GradeMarkQuery me() {
		return QUERY;
	}

	public long findCount() {
		return DAO.doFindCount();
	}
	
	public List<JGrademark> findList(int page, int pagesize, int column, String order, String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_gradeMark gm ");
		String c=column==0?"id":column==1?"parentId":column==2?"name":column==3?"score":"details";
		sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(gradeParentId, ''),IFNULL(`name`, ''),IFNULL(score, ''),IFNULL(mark, '')) LIKE '%"+search+"%' ");
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
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL(gradeParentId, ''),IFNULL(`name`, ''),IFNULL(score, ''),IFNULL(mark, '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}

	public Integer findByName(String name){
		JGrademark jGrademark = DAO.findFirst("select * from j_gradeMark where name= ?",name);
		if (jGrademark != null){
			return jGrademark.getId();
		}else {
			return -1;
		}
	}

	public List<JGrademark> findListByName(String name,String drillName){
		if (drillName!=null){
			return DAO.find("select * from j_gradeMark where name= ? AND drillname",name,drillName);
		}else {
			return DAO.find("select * from j_gradeMark where name= ?",name);
		}
	}

	public JGrademark findById(final Integer id) {
		return DAO.findById(id);
	}

	public Integer findIsExist(Integer parendId,String name,String drillName){
		JGrademark jGrademark = DAO.findFirst("select * from j_gradeMark where gradeParentId = ? and name = ? and drillname = ?",parendId,name,drillName);
		if (jGrademark != null){
			return jGrademark.getId();
		}else {
			return null;
		}
	}

	public List<JGrademark> findScoreDetails(Integer parendId){
		return DAO.find("select * from j_gradeMark where gradeParentId = ?",parendId);
	}

	public JGrademark findByCampAndDrillName(Integer id,String camp,String drillName){
		return DAO.findFirst("select * from j_gradeMark where id = ? and camp = ? and drillname = ?",id,camp,drillName);
	}

	public List<Integer> findIdByparentId(final Integer id){
		List<JGrademark> jGrademarks = DAO.find("select * from j_gradeMark where gradeParentId="+id+"");
		List<Integer> listId = new ArrayList<>();
		if (jGrademarks.isEmpty()){
			return null;
		}else {
			for (JGrademark score: jGrademarks){
				listId.add(score.getId());
			}
			return listId;
		}
	}
}
