package com.base.query;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JNews;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;

public class NewsQuery {
	protected static final JNews DAO = new JNews();
	private static final NewsQuery QUERY = new NewsQuery();

	public static NewsQuery me() {
		return QUERY;
	}

	public long findCount(String search) {
		if(StringUtils.isEmpty(search)){
			return DAO.doFindCount();
		}else{
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL(`titie`, ''),IFNULL(`postMan`, ''),IFNULL(`postTime`, '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	
	public List<Record> findListNews(int page, int limit,String where) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		sqlBuilder.append(" order by n.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);		
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	
	
	public List<Record> getAll(){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		sqlBuilder.append(" order by n.postTime DESC ");
		return Db.find(sqlBuilder.toString());
	}
	
	public List<Record> getIndexNews(){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		sqlBuilder.append(" order by n.postTime DESC limit 0, 10 ");
		return Db.find(sqlBuilder.toString());
	}
	
	public boolean deleteById(Integer id){
		return DAO.deleteById(id);
	}
	
	public Record getById(Integer id){
		return Db.findFirst("SELECT n.id, n.title, n.postTime, n.postMan, n.content, n.type, n.attachment, nt.name from j_news n join j_newsType nt on nt.id = n.type where n.id =  ? ", id);
	}
	
	public List<Record> getNewsByPageTag(Integer start, Integer tag){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type WHERE n.type = ? ");
		sqlBuilder.append(" order by n.postTime DESC limit ?, 10 ");
		return Db.find(sqlBuilder.toString(),tag, start);
	}
	
	public List<Record> getByTag(Integer id){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type WHERE n.type = ?");
		sqlBuilder.append(" order by n.postTime DESC limit 0, 10 ");
		return Db.find(sqlBuilder.toString(),id);
		
	}
	
}
