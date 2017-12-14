package com.base.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JUser;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.IDataLoader;


public class UserQuery extends JBaseQuery {
	protected static final JUser DAO = new JUser();
	private static final UserQuery QUERY = new UserQuery();

	public static UserQuery me() {
		return QUERY;
	}
	
	public List<Record> findListUserRole(int page, int pagesize,int column,String order,String search) {
		String[] s = {"u.id","u.username","u.relname","u.nickname","u.email","u.mobile","r.name"};
		return DAO.pageSqlR("u.*,r.`name` as roleName", "j_user u LEFT JOIN j_userrole ur ON ur.userId=u.id LEFT JOIN j_role r ON r.id=ur.roleId", s, page,  pagesize, column, order, search);
	}
	public List<Record> findListUserRole(int page, int limit,String where) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT u.*,r.`name` as roleName ");
		sqlBuilder.append(" FROM j_user u LEFT JOIN j_userrole ur ON ur.userId=u.id LEFT JOIN j_role r ON r.id=ur.roleId ");
		sqlBuilder.append(" order by u.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);
		
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	public Long findConuntUserRole(String search) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT count(*) FROM j_user u LEFT JOIN j_userrole ur ON ur.userId=u.id LEFT JOIN j_role r ON r.id=ur.roleId");
		if(StringUtils.isEmpty(search)){
			return Db.queryLong(sqlBuilder.toString());
		}else{
			sqlBuilder.append(" where CONCAT( IFNULL(u.id, ''),IFNULL(u.username, ''),IFNULL(u.relname, ''),IFNULL(u.nickname, ''),IFNULL(u.email, ''),IFNULL(u.mobile, ''),IFNULL(r.name, '')) LIKE '%"+search+"%' ");

			return Db.queryLong(sqlBuilder.toString());
		}
	}
	public Long findConuntUserRole() {
		StringBuilder sqlBuilder = new StringBuilder("SELECT count(*) FROM j_user u LEFT JOIN j_userrole ur ON ur.userId=u.id LEFT JOIN j_role r ON r.id=ur.roleId");
		return Db.queryLong(sqlBuilder.toString());
		
	}

	public JUser findById(final Integer userId) {
		return DAO.getCache(userId, new IDataLoader() {
			@Override
			public Object load() {
				return DAO.findById(userId);
			}
		});
	}
	
	public JUser findUserByUserName(String username){
		return DAO.findFirst("select * from j_user where username = ?",username);
	}

	protected static void buildOrderBy(String orderBy, StringBuilder fromBuilder) {
		if ("content_count".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.content_count DESC");
		}

		else if ("comment_count".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.comment_count DESC");
		}

		else if ("username".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.username DESC");
		}

		else if ("nickname".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.nickname DESC");
		}

		else if ("amount".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.amount DESC");
		}

		else if ("logged".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.logged DESC");
		}

		else if ("activated".equals(orderBy)) {
			fromBuilder.append(" ORDER BY u.activated DESC");
		}

		else {
			fromBuilder.append(" ORDER BY u.created DESC");
		}
	}

}
