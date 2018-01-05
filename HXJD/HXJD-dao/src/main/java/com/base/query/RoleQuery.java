package com.base.query;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JRole;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.IDataLoader;

public class RoleQuery {
	protected static final JRole DAO = new JRole();
	private static final RoleQuery QUERY = new RoleQuery();

	public static RoleQuery me() {
		return QUERY;
	}
	
	public JRole findById(final Integer roleId) {
		return DAO.getCache(roleId, new IDataLoader() {
			@Override
			public Object load() {
				return DAO.findById(roleId);
			}
		});
	}
	
	public List<Record> findListRole(int page, int limit,String where) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT r.id, r.name, r.describe FROM j_role r ");
		sqlBuilder.append(" order by r.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);
		
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	
	public List<JRole> findList(int page, int pagesize,int column,String order,String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_role u ");
		String c=column==0?"id":column==1?"name":"describe";
		sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(`name`, ''),IFNULL(`describe`, '')) LIKE '%"+search+"%' ");
		sqlBuilder.append(" order by `"+c+"` "+order+" LIMIT ?, ? ");
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
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL(`name`, ''),IFNULL(`describe`, '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	public List<JRole> getAll(){
		return DAO.find("select * from j_role order by id asc");
	}
	public JRole findByUserId(final Integer ruserId){
		return DAO.findFirst("SELECT r.* FROM j_role r INNER JOIN j_userrole u ON u.roleId=r.id WHERE u.userId=?",ruserId);
	}
}
