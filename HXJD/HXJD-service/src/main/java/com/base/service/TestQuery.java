package com.base.service;

import com.base.model.JTest;

public class TestQuery extends JBaseQuery {
	protected static final JTest DAO = new JTest();
	private static final TestQuery QUERY = new TestQuery();

	public static TestQuery me() {
		return QUERY;
	}

	public long findCount() {
		return DAO.doFindCount();
	}
	
	public boolean saveData(JTest jTest){
		return jTest.save();
	}
	
}
