package com.base.query;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.constants.NewsConstants;
import com.base.model.JNews;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import jodd.util.StringUtil;

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

	/**
	 * 根据信息typeId查询信息数量
	 * @param type
	 * @return
	 */
	public long findCountByTypeId(int type){
		StringBuilder sqlBuilder = new StringBuilder("SELECT count(*)");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		if(type>0){
			sqlBuilder.append(" where n.type="+type);
		}else{
			sqlBuilder.append(" where nt.type="+ NewsConstants.NEWS_TYPE_COMMON);
		}
		return Db.queryLong(sqlBuilder.toString());
	}

	public List<Record> findListNewsByType(int page,int limit,int type){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content, n.attachment, n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		if(type>0){
			sqlBuilder.append(" where n.type="+type);
		}else{
			sqlBuilder.append(" where nt.type="+ NewsConstants.NEWS_TYPE_COMMON);
		}
		sqlBuilder.append(" order by n.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	
	public List<Record> findListNews(int page, int limit,String where) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content, n.attachment, n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		sqlBuilder.append(" order by n.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);		
		return Db.find(sqlBuilder.toString(), params.toArray());
	}
	
	
	public List<Record> getAll(){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		sqlBuilder.append(" order by n.postTime DESC ");
		return Db.find(sqlBuilder.toString());
	}
	
	public List<Record> getIndexNews(){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment,  n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		sqlBuilder.append(" order by n.postTime DESC limit 0, 10 ");
		return Db.find(sqlBuilder.toString());
	}

	/**
	 * 查询发布时间小于time的limit条数据
	 * @param time
	 * @param limit
	 * @return
	 */
	public List<Record> getLtTimeNews(String time,int limit){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type ");
		if(StringUtil.isNotBlank(time)){
			sqlBuilder.append(" where n.postTime<'"+time+"'");
		}
		sqlBuilder.append(" order by n.postTime DESC limit 0,"+limit);
		return Db.find(sqlBuilder.toString());
	}

	public JNews getNewsById(int id){
		return DAO.findById(id);
	}

	public boolean deleteById(Integer id){
		return DAO.deleteById(id);
	}
	
	public Record getById(Integer id){
		return Db.findFirst("SELECT n.id, n.title, n.postTime, n.postMan, n.content, n.type, n.attachment, n.attachmentName,nt.name from j_news n join j_newsType nt on nt.id = n.type where n.id =  ? ", id);
	}
	
	public List<Record> getNewsByPageTag(Integer start, Integer tag){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type WHERE n.type = ? ");
		sqlBuilder.append(" order by n.postTime DESC limit ?, 10 ");
		return Db.find(sqlBuilder.toString(),tag, start);
	}
	
	public List<Record> getByTag(Integer id){
		StringBuilder sqlBuilder = new StringBuilder("SELECT n.id, n.title, n.postTime, n.postMan, n.content,n.attachment, n.attachmentName, n.type, nt.name  ");
		sqlBuilder.append(" from j_news n join j_newsType nt on nt.id = n.type WHERE n.type = ?");
		sqlBuilder.append(" order by n.postTime DESC limit 0, 10 ");
		return Db.find(sqlBuilder.toString(),id);
		
	}
	
}
