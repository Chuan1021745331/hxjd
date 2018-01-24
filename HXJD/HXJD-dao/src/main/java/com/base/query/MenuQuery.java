package com.base.query;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.constants.MagicValueConstants;
import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.MenuDto;
import com.base.model.dto.MenuSimpDto;

public class MenuQuery {
	protected static final JMenu DAO = new JMenu();
	private static final MenuQuery QUERY = new MenuQuery();

	public static MenuQuery me() {
		return QUERY;
	}

	public long findCount() {
		return DAO.doFindCount();
	}
	
	public List<JMenu> findList(int page, int pagesize,int column,String order,String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_menu u ");
		String c=column==0?"id":column==1?"parent":column==2?"name":column==3?"url":column==4?"tag":column==5?"ico":"sort";
		sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(parent, ''),IFNULL(`name`, ''),IFNULL(url, ''),IFNULL(tag, ''),IFNULL(ico, ''),IFNULL(sort, '')) LIKE '%"+search+"%' ");
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
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL(parent, ''),IFNULL(`name`, ''),IFNULL(url, ''),IFNULL(tag, ''),IFNULL(ico, ''),IFNULL(sort, '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	
	public List<JMenu> getMenus(JUser user){
		List<JMenu> menus = null;
		if(MagicValueConstants.ADMINISTRATOR.equals(user.getRole())){
			menus = this.getAll();
		}else{
			menus = DAO.find("SELECT m.* FROM j_userrole u INNER JOIN j_rolemenubutton r ON r.roleId=u.roleId INNER JOIN  j_menu m ON m.id=r.menuId WHERE u.userId=?",user.getId());
		}
		return menus;
		
	}
	
	public List<JMenu> getAll(){
		return DAO.find("select * from j_menu order by sort asc");
	}

	public List<JMenu> findByParent(final Integer parent){
		return DAO.find("select * from j_menu where parent="+parent+"");
	}

	public JMenu findById(final Integer userId) {
		return DAO.findById(userId);
	}

}
