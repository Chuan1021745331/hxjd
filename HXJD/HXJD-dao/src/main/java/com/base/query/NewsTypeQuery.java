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
		return DAO.find("SELECT nt.id , nt.name, nt.type FROM j_newstype nt ");
	}
}
