package com.base.service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.MenuDto;
import com.base.model.dto.MenuSimpDto;

public class MenuQuery extends JBaseQuery {
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
	
	public List<MenuDto> getMenus(JUser user){
		List<JMenu> menus=null;
		if("administrator".equals(user.getRole())){
			menus = this.getAll();
		}else{
			menus = DAO.find("SELECT m.* FROM j_userrole u INNER JOIN j_rolemenubutton r ON r.roleId=u.roleId INNER JOIN  j_menu m ON m.id=r.menuId WHERE u.userId=?",user.getId());
		}
		
		List<MenuDto> mts = new ArrayList<MenuDto>();
		for (JMenu m : menus) {
			if (m.getParent() == 0) {
				MenuDto md = new MenuDto();
				md.setM(m);
				Integer n = 0 ;
				for (JMenu m_ : menus) {
					if(m.getId().intValue()==m_.getParent().intValue()){
						n++;
					}
				}
				md.setN(n);
				if(n>0){
					md.setChildren(getChild(m,menus));
				}
				mts.add(md);
			}
		}
		return mts;
	}
	public List<MenuSimpDto> getMenusSimp(){
		List<JMenu> menus = this.getAll();
		List<MenuSimpDto> fmts = new ArrayList<MenuSimpDto>();
		List<MenuSimpDto> mts = new ArrayList<MenuSimpDto>();
		MenuSimpDto msd = new MenuSimpDto();
		msd.setId("0");
		msd.setName("根节点");
		for (JMenu m : menus) {
			if (m.getParent() == 0) {
				MenuSimpDto md = new MenuSimpDto();
				md.setId(m.getId()+"");
				md.setName(m.getName());
				Integer n = 0 ;
				for (JMenu m_ : menus) {
					if(m.getId().intValue()==m_.getParent().intValue()){
						n++;
					}
				}
				if(n>0){
					md.setChildren(getChildSimp(m,menus));
				}
				mts.add(md);
			}
		}
		msd.setChildren(mts);
		fmts.add(msd);
		return fmts;
	}
	
	public List<MenuDto> getChild(JMenu pm,List<JMenu> ms){
//		List<Menu> menus = this.findByParent(pm.getId());
		List<MenuDto> mts = new ArrayList<MenuDto>();
		for (JMenu m:ms) {
			if(m.getParent().intValue()==pm.getId().intValue()){
				MenuDto md = new MenuDto();
				md.setM(m);
				Integer n = 0 ;
				for (JMenu m_ : ms) {
					if(m.getId().intValue()==m_.getParent().intValue()){
						n++;
					}
				}
				md.setN(n);
				
				if(n>0){
					
					md.setChildren(getChild(m,ms));
				}
				mts.add(md);
			}
		}
		
		return mts;
	}
	
	public List<MenuSimpDto> getChildSimp(JMenu pm,List<JMenu> ms){
		List<MenuSimpDto> mts = new ArrayList<MenuSimpDto>();
		for (JMenu m:ms) {
			if(m.getParent().intValue()==pm.getId().intValue()){
				MenuSimpDto md = new MenuSimpDto();
				md.setId(m.getId()+"");
				md.setName(m.getName());
				Integer n = 0 ;
				for (JMenu m_ : ms) {
					if(m.getId().intValue()==m_.getParent().intValue()){
						n++;
					}
				}
				if(n>0){
					md.setChildren(getChildSimp(m,ms));
				}
				mts.add(md);
			}
		}
		
		return mts;
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
