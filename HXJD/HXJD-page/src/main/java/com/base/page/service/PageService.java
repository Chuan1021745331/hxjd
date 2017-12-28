package com.base.page.service;

import java.util.List;

import com.base.model.JButton;
import com.base.service.ButtonQuery;

public class PageService {
	protected static final JButton DAO = new JButton();
	private static final ButtonQuery QUERY = new ButtonQuery();

	public static ButtonQuery me() {
		return QUERY;
	}
	public List<JButton> getAll(){
		return DAO.find("select * from j_button");
	}
}
