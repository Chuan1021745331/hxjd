package com.base.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JRolemenubutton;
import com.jfinal.plugin.ehcache.IDataLoader;

public class RoleMenuButtonQuery {
	protected static final JRolemenubutton DAO = new JRolemenubutton();
	private static final RoleMenuButtonQuery QUERY = new RoleMenuButtonQuery();

	public static RoleMenuButtonQuery me() {
		return QUERY;
	}
	
	public JRolemenubutton findById(final Integer roleId) {
		return DAO.getCache(roleId, new IDataLoader() {
			@Override
			public Object load() {
				return DAO.findById(roleId);
			}
		});
	}
	
	public List<JRolemenubutton> findListByRoleId(final Integer roleId){
		return DAO.find("SELECT * FROM j_rolemenubutton WHERE roleId="+roleId);
	}
	public JRolemenubutton findListByRoleIdAndMenuId(final Integer roleId,final Integer menuId){
		return DAO.findFirst("SELECT * FROM j_rolemenubutton WHERE roleId="+roleId+" and menuId="+menuId);
	}
	
	public List<JRolemenubutton> findList(int page, int pagesize,int column,String order,String search) {
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
	public List<JRolemenubutton> getAll(){
		return DAO.find("select * from j_role order by id asc");
	}
	public int delbyRoleId(final Integer roleId){
		return DAO.doDelete("roleId=?", roleId);
	}
	public JRolemenubutton findByUserId(Integer id, Integer menuId){
		StringBuilder sql = new StringBuilder("SELECT rm.* FROM j_user u INNER JOIN j_userrole r ON u.id = r.userId INNER JOIN j_rolemenubutton rm ON r.roleId = rm.roleId WHERE u.id =");
		sql.append(id + " AND rm.menuId  = " + menuId);
		return DAO.findFirst(sql.toString())==null?new JRolemenubutton():DAO.findFirst(sql.toString());

	}
	
}
