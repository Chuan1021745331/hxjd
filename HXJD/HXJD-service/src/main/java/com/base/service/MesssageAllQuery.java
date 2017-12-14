package com.base.service;

import java.util.List;

import com.base.model.JMessageall;

public class MesssageAllQuery extends JBaseQuery {
	protected static final JMessageall DAO = new JMessageall();
	private static final MesssageAllQuery QUERY = new MesssageAllQuery();
	
	public static MesssageAllQuery me() {
		return QUERY;
	}

	public long findCount() {
		return DAO.doFindCount();
	}
	
/*	public void findAll(){
		
	}*/
	
	/**
	 * 
	 * findByCamp(这里用一句话描述这个方法的作用)  
	 * 根据阵营查询未读的消息 
	 * @param camp
	 * @return   
	 *List<JMessageall>  
	 * @exception   
	 * @since  1.0.0
	 */
	public List<JMessageall> findByCamp(String camp){	
		return DAO.find("select * from j_messageall where camp in (2, ?)  and state = 1 order by sendTime", camp); 
	}
	
	public List<JMessageall> findReadByCamp(String camp){
		return DAO.find("select * from (select * from j_messageall where camp in (2, ?) and state = 0 order by sendTime desc LIMIT 0 , 20 ) t order by t.sendTime", camp); 
	}
}
