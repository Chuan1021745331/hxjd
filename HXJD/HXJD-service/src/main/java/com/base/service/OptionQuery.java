package com.base.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JOption;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.IDataLoader;

public class OptionQuery {
	protected static final JOption DAO = new JOption();
	private static final OptionQuery QUERY = new OptionQuery();

	public static OptionQuery me() {
		return QUERY;
	}
	
	public JOption findById(final Integer optionId) {
		return DAO.getCache(optionId, new IDataLoader() {
			@Override
			public Object load() {
				return DAO.findById(optionId);
			}
		});
	}
	/**
	 * 
	 * @param page
	 * @param pagesize
	 * @param column
	 * @param order
	 * @param search
	 * @return
	 */
	public List<Record> findListOptions(int page, int limit,String where) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT o.id, o.option_name, o.option_key, o.option_value, o.remark FROM j_option o ");
		sqlBuilder.append(" order by o.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);
		
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	
	public List<JOption> findList(int page, int pagesize,int column,String order,String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_option u ");
		String c=column==0?"id":column==1?"option_name":column==2?"option_key":"option_value";
		sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL('option_name', ''),IFNULL(option_key, ''),IFNULL(option_value, '')) LIKE '%"+search+"%' ");
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
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL('option_name', ''),IFNULL(option_key, ''),IFNULL(option_value, '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	public List<JOption> getAll(){
		return DAO.find("select * from j_option order by id asc");
	}
	
	public void optionCacheAll(){
		List<JOption> all = getAll();
		for (JOption o :all) {
			DAO.putCache(o.getOptionKey(), o.getOptionValue());
		}
	}
	
	public JOption findJOption4OptionKey(final String optionKey){
		return DAO.findFirst("select * from j_option where option_key=?", optionKey);
	}
}
