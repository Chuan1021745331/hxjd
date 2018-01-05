package com.base.query;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JUserrole;
import com.jfinal.plugin.ehcache.IDataLoader;

public class UserRoleQuery {
	protected static final JUserrole DAO = new JUserrole();
	private static final UserRoleQuery QUERY = new UserRoleQuery();

	public static UserRoleQuery me() {
		return QUERY;
	}
	
	public JUserrole findById(final BigInteger roleId) {
		return DAO.getCache(roleId, new IDataLoader() {
			@Override
			public Object load() {
				return DAO.findById(roleId);
			}
		});
	}
	
//	public List<JUserRole> findByUserId(final BigInteger userId){
//		List<Record> list = Db.find("select * from j_user_role");
//		return list;
//	}
	public List<JUserrole> findList(int page, int pagesize,int column,String order,String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_role u ");
		String c=column==0?"id":column==1?"name":"describe";
		sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL('name', ''),IFNULL('describe', '')) LIKE '%"+search+"%' ");
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
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL('name', ''),IFNULL('describe', '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	public List<JUserrole> getAll(){
		return DAO.find("select * from j_role order by id asc");
	}
	
	public int delByUserId(final Integer userId){
		return DAO.doDelete("userId=?", userId);
	}
	
}
