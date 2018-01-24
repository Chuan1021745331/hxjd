package com.base.service;

import java.util.ArrayList;
import java.util.List;

import com.base.constants.MagicValueConstants;
import com.base.model.JButton;
import com.base.model.JMenu;
import com.base.query.ButtonQuery;
import com.base.query.MenuQuery;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

public class ButtonService {
	private static final ButtonService SERVICE = new ButtonService();
	public static ButtonService me() {
		return SERVICE;
	}
	

	public long findConunt(String where){
		return ButtonQuery.me().findConunt(where);
	}
	
	public List<JButton> findList(Integer page, Integer limit, String where, long count){
		List<JButton> list = new ArrayList<>();
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
	        list = ButtonQuery.me().findList(page, limit,where);
		}
		return list;
	}
	
	@Before(Tx.class)
	public boolean saveOrUpdate(JButton button){
		button.setIsModal(null==button.getIsModal()?false:true);
		button.setIsRight(null==button.getIsRight()?false:true);
		if(button.getUrl().startsWith(MagicValueConstants.FORWARD_SLASH)){
			button.setUrl(button.getUrl().substring(1, button.getUrl().length()));
		}
		return button.saveOrUpdate();		
	}
	
	@Before(Tx.class)
	public void jButtonE(Integer id, String name, String code){
		JButton button = new JButton();
		button.setMenuId(id);
		button.setName(name);
		button.setCode(code);
		button.save();
	}
	
	public List<JButton> findByMenuId(Integer menuId){
		return ButtonQuery.me().findByMenuId(menuId);
	}
	
	@Before(Tx.class)
	public void addButton(Integer menuId, String add, String del, String edit, String sel, String[] newButtonName, String[] newButtonCode, String[] newButtonUrl){
		ButtonQuery.me().delButtonNotInByMenuId(menuId);
		if(null!=newButtonName){
			for (int i = 0; i < newButtonName.length; i++) {
				if(StringUtils.isNotEmpty(newButtonName[i]) && StringUtils.isNotEmpty(newButtonUrl[i]) && StringUtils.isNotEmpty(newButtonCode[i])){
					JButton button = new JButton();
					button.setMenuId(menuId);
					button.setName(newButtonName[i]);
					button.setCode(newButtonCode[i]);
					button.setUrl(newButtonUrl[i]);
					
					button.save();
				}
			}
		}
		ButtonQuery.me().updateByTypeAndMenuId("add", menuId, add);
		ButtonQuery.me().updateByTypeAndMenuId("del", menuId, del);
		ButtonQuery.me().updateByTypeAndMenuId("edit", menuId, edit);
		ButtonQuery.me().updateByTypeAndMenuId("sel", menuId, sel);
	}
	
	@Before(Tx.class)
	public boolean delTree(Integer id){
		JMenu menu = MenuQuery.me().findById(id);
		List<JMenu> menuList = MenuQuery.me().findByParent(id);
		if(menuList.size()>0){
			for (JMenu m:menuList) {
				m.delete();
			}
		}		
		CacheKit.removeAll("menu");
		ButtonQuery.me().delButtonByMenuId(id);
		return menu.delete();
	}
}
