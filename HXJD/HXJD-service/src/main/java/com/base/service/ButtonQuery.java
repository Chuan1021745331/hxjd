package com.base.service;

import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JButton;
import com.jfinal.plugin.activerecord.Db;

public class ButtonQuery {
	protected static final JButton DAO = new JButton();
	private static final ButtonQuery QUERY = new ButtonQuery();

	public static ButtonQuery me() {
		return QUERY;
	}
	
	public JButton findById(final Integer optionId) {
//		return DAO.getCache(optionId, new IDataLoader() {
//			@Override
//			public Object load() {
				return DAO.findById(optionId);
//			}
//		});
	}
	public JButton findByCode(final Integer menuId,final String code) {
		return DAO.findFirst("select * from j_button where code =? and menuId=?", code, menuId);
	}
	
	public int delButtonByMenuId(final Integer menuId) {
		return Db.update("delete from j_button where menuId = ? ", menuId);
	}
	public int delButtonNotInByMenuId(final Integer menuId) {
		return Db.update("delete from j_button where menuId = ? and code not in('add','del','edit','sel')", menuId);
	}
	public int updateByTypeAndMenuId(final String type,final Integer menuId,final String url){
		return Db.update("update j_button set url = '"+url+"' where menuId="+menuId+" and code='"+type+"'");
	}
	
	public List<JButton> findByMenuId(final Integer menuId) {
		List<JButton> list = DAO.find("select * from j_button where menuId = '"+menuId+"'");
		return list;
	}
	
	public List<JButton> getAll(){
		return DAO.find("select * from j_button");
	}
	
	public List<JButton> findList(int page, int limit,String where) {
        StringBuilder sqlBuilder = new StringBuilder("SELECT p.* FROM j_button p ");
        if(!StringUtils.isEmpty(where)){
        	if(Integer.parseInt(where)!=0){
        		sqlBuilder.append(" WHERE menuId = '" + where + "' ");
        	}
        }
		sqlBuilder.append(" order by p.id asc LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(limit*page-limit);
		params.add(limit);
		
		return DAO.find(sqlBuilder.toString(), params.toArray());
    }

    public Long findConunt(String where) {
        if (StringUtils.isEmpty(where)) {
            return DAO.doFindCount();
        } else {
            StringBuilder sqlBuilder = new StringBuilder();
            if(!StringUtils.isEmpty(where)){
            	if(Integer.parseInt(where)!=0){
            		sqlBuilder.append(" WHERE menuId = '" + where + "' ");
            	}
            }
            return DAO.doFindCount(sqlBuilder.toString());
        }
    }
}
