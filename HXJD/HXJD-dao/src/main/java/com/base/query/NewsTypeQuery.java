package com.base.query;

import java.util.List;

import com.base.model.JNewstype;

public class NewsTypeQuery {
	protected static final JNewstype DAO = new JNewstype();
	private static final NewsTypeQuery QUERY = new NewsTypeQuery();

	public static NewsTypeQuery me() {
		return QUERY;
	}
	
	public List<JNewstype> getAll(){
		return DAO.find("SELECT nt.id , nt.name, nt.type FROM j_newstype nt order by Length(nt.name)");
	}
	
	public JNewstype saveByName(String name, Integer type){
		JNewstype newstype = new JNewstype();
		newstype.setName(name);
		newstype.setType(type);
		newstype.saveOrUpdate();
		return newstype;
	}

	/**
	 * 根据类型获取信息类型
	 * @param type
	 * @return
	 */
	public List<JNewstype> getByType(int type){
		return DAO.find("SELECT nt.id , nt.name, nt.type FROM j_newstype nt where nt.type="+type);
	}
}
