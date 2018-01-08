package com.base.query;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JJob;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JobQuery {
	protected static final JJob DAO = new JJob();
	private static final JobQuery QUERY = new JobQuery();
	private final static Logger logger= LoggerFactory.getLogger(JobQuery.class);


	public static JobQuery me() {
		return QUERY;
	}

/*	public List<JJob> findList(int page, int pagesize, String gender, String role, String status, String orderBy) {
		StringBuilder sqlBuilder = new StringBuilder("select * from user u ");
		LinkedList<Object> params = new LinkedList<Object>();

		boolean needWhere = true;
		needWhere = appendIfNotEmpty(sqlBuilder, "u.gender", gender, params, needWhere);
		needWhere = appendIfNotEmpty(sqlBuilder, "u.role", role, params, needWhere);
		needWhere = appendIfNotEmpty(sqlBuilder, "u.status", status, params, needWhere);

		buildOrderBy(orderBy, sqlBuilder);

		sqlBuilder.append(" LIMIT ?, ?");
		params.add(page - 1);
		params.add(pagesize);

		if (params.isEmpty()) {
			return DAO.find(sqlBuilder.toString());
		} else {
			return DAO.find(sqlBuilder.toString(), params.toArray());
		}

	}*/

	public List<Record> findJobList(int page, int limit,String where){
		StringBuilder sqlBuilder = new StringBuilder("SELECT j.id, j.jobName, j.jobGroup, j.jobStatus, j.cronExp, j.remark FROM j_job j ");
		sqlBuilder.append(" order by j.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);
		
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	
	public List<JJob> findList(int page, int pagesize, int column, String order, String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_job u ");
		String c = column == 0 ? "jobName" : column == 1 ? "jobGroup" : column == 2 ? "jobStatus" : column == 3 ? "cronExp" : "remark";
		sqlBuilder.append(" WHERE CONCAT( IFNULL('jobName', ''),IFNULL(jobGroup, ''),IFNULL(jobStatus, ''),IFNULL(cronExp, ''),IFNULL(remark, '')) LIKE '%" + search + "%' ");
		sqlBuilder.append(" order by " + c + " " + order + " LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(page);
		params.add(pagesize);

		if (params.isEmpty()) {
			return DAO.find(sqlBuilder.toString());
		} else {
			return DAO.find(sqlBuilder.toString(), params.toArray());
		}
	}

	public Page<JJob> paginate(int pageNumber, int pageSize , String orderby) {
		String select = "select * ";
		StringBuilder fromBuilder = new StringBuilder(" from j_job u ");
		buildOrderBy(orderby, fromBuilder);
		return DAO.paginate(pageNumber, pageSize, select, fromBuilder.toString());
	}

	public Long findConunt(String search) {
		if (StringUtils.isEmpty(search)) {
			return DAO.doFindCount();
		} else {
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" where CONCAT( IFNULL('jobName', ''),IFNULL(jobGroup, ''),IFNULL(jobStatus, ''),IFNULL(cronExp, ''),IFNULL(remark, '')) LIKE '%" + search + "%' ");
			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	
	public List<JJob> findAll(){
		List<JJob> job = DAO.find("select * from j_job");
		return job;
	}

	public long findCount() {
		return DAO.doFindCount();
	}

	public JJob findById(final BigInteger userId) {
		return DAO.findById(userId);
	}

	public JJob findById(final Integer id) {
		return DAO.findById(id);
	}
	
	public JJob findUserByUserName(String username){
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



	public  void test(){
		logger.info("==========================a+b= ");
	}

}