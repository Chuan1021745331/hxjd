package com.base.service;

import java.util.ArrayList;
import java.util.List;

import com.base.model.JButton;
import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.MenuDto;
import com.base.model.dto.MenuSimpDto;
import com.base.query.ButtonQuery;
import com.base.query.MenuQuery;

public class MenuService {
	private static final MenuService SERVICE = new MenuService();
	public static MenuService me() {
		return SERVICE;
	}
	
	public JMenu findMenuById(Integer menuId){
		return MenuQuery.me().findById(menuId);
	}
	
	public JButton findButtonById(Integer buttonId){
		return ButtonQuery.me().findById(buttonId);
	}
	
	public boolean del(String id){
		JButton button = ButtonQuery.me().findById(Integer.parseInt(id));
		return button.delete();
	}
	
	public boolean delTree(Integer id){
		JMenu menu = MenuQuery.me().findById(id);
		List<JMenu> menuList = MenuQuery.me().findByParent(id);
		if(menuList.size()>0){
			for (JMenu m:menuList) {
				m.delete();
			}
		}
		return menu.delete();
	}
	
	
	public List<MenuSimpDto> getMenusSimp(){
		List<JMenu> menus = MenuQuery.me().getAll();
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
	
	public List<MenuDto> getMenus(JUser user){
		List<JMenu> menus = MenuQuery.me().getMenus(user);
		
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
}
