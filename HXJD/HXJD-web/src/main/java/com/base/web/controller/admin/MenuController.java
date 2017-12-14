package com.base.web.controller.admin;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.ButtonQuery;
import com.base.service.MenuQuery;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.CacheKit;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JButton;
import com.base.model.JMenu;
import com.base.model.dto.MenuSimpDto;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: MenuController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 菜单管理   
 * @所属: 华夏九鼎     
 * @日期: 2017年5月15日 上午9:37:27   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/menu", viewPath = "/view/admin/menu")
@RouterNotAllowConvert
public class MenuController extends BaseController {
	private Log log = Log.getLog(MenuController.class);

	@Before(ButtonInterceptor.class)
	public void index() {
		render("menu.html");
	}
	
	public void tree() {
		List<MenuSimpDto> m = MenuQuery.me().getMenusSimp();
		renderJson(m);
	}
	
	public void addTree() throws UnsupportedEncodingException {
		Integer pId = getParaToInt("pId");
		String name = getPara("name");
		
		JMenu menu = new JMenu();
		menu.setParent(pId);
		menu.setName(name);
		boolean b = menu.save();
		CacheKit.removeAll("menu");
		if(b){
			this.JButtonE(menu.getId(), "新增", "add");
			this.JButtonE(menu.getId(), "删除", "del");
			this.JButtonE(menu.getId(), "修改", "edit");
			this.JButtonE(menu.getId(), "详情", "sel");
			renderJson(menu);
		}else{
			renderAjaxResultForError();
		}
	}
	
	private void JButtonE(Integer id,String name,String code){
		JButton button = new JButton();
		button.setMenuId(id);
		button.setName(name);
		button.setCode(code);
		button.save();
	}
	
	public void editTree() {
		try {
			Integer id = getParaToInt("id");
			Integer parent = getParaToInt("parent");
			String name = getPara("name");
			String url = getPara("url");
			String tag = getPara("tag");
			String ico = getPara("ico");
			int sort = getParaToInt("sort");
			
			JMenu menu = MenuQuery.me().findById(id);
			menu.setParent(parent);
			menu.setName(name);
			menu.setUrl(url);
			menu.setTag(tag);
			menu.setIco(ico);
			menu.setSort(sort);
			
			boolean b = menu.saveOrUpdate();
			CacheKit.removeAll("menu");
			if(b){
				renderAjaxResult("", 0, menu);
			}else{
				renderAjaxResultForError(MessageConstants.EDIT_TREE_DEFEAT);
			}
		} catch (Exception e) {
			renderAjaxResultForError(MessageConstants.EDIT_TREE_NONE_PARENT);
		}
		
	}
	public void button(){
		Integer menuId = getParaToInt("id");
		List<JButton> menuButton = ButtonQuery.me().findByMenuId(menuId);
		String add="";
		String del="";
		String edit="";
		String sel="";
		for (int i=0;i<menuButton.size();i++) {
			if(menuButton.get(i).getCode().equals("add")){
				add=menuButton.get(i).getUrl();
				menuButton.remove(i);
				i--;
				continue;
			}else if(menuButton.get(i).getCode().equals("del")){
				del=menuButton.get(i).getUrl();
				menuButton.remove(i);
				i--;
				continue;
			}else if(menuButton.get(i).getCode().equals("edit")){
				edit=menuButton.get(i).getUrl();
				menuButton.remove(i);
				i--;
				continue;
			}
			else if(menuButton.get(i).getCode().equals("sel")){
				sel=menuButton.get(i).getUrl();
				menuButton.remove(i);
				i--;
				continue;
			}
		}
		setAttr("menuId", menuId);
		setAttr("add", add);
		setAttr("del", del);
		setAttr("edit", edit);
		setAttr("sel", sel);
		setAttr("list", menuButton);
		render("button.html");
	}
	
	public void addButton(){
		try {
			Integer menuId = getParaToInt("menuId");
			ButtonQuery.me().delButtonNotInByMenuId(menuId);
			String[] newButtonName = getParaValues("newButtonName");
			String[] newButtonCode = getParaValues("newButtonCode");
			String[] newButtonUrl = getParaValues("newButtonUrl");
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
			
			String add = getPara("add");
			String del = getPara("del");
			String edit = getPara("edit");
			String sel = getPara("sel");
			
			ButtonQuery.me().updateByTypeAndMenuId("add", menuId, add);
			ButtonQuery.me().updateByTypeAndMenuId("del", menuId, del);
			ButtonQuery.me().updateByTypeAndMenuId("edit", menuId, edit);
			ButtonQuery.me().updateByTypeAndMenuId("sel", menuId, sel);
			
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
		} catch (Exception e) {
			log.debug(e.toString());
			renderAjaxResultForSuccess(MessageConstants.EDIT_DEFEAT);
		}
		
	}
	
	public void optionIco(){
		render("ico.html");
	}
	
	public void delTree() {
		Integer id = getParaToInt("id");
		JMenu menu = MenuQuery.me().findById(id);
		List<JMenu> menuList = MenuQuery.me().findByParent(id);
		if(menuList.size()>0){
			for (JMenu m:menuList) {
				m.delete();
			}
		}
		boolean b = menu.delete();
		CacheKit.removeAll("menu");
		ButtonQuery.me().delButtonByMenuId(id);
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}
	
	public void getMenu(){
		Integer id = getParaToInt("id");
		JMenu menu = MenuQuery.me().findById(id);
//		List<JButton> button = ButtonQuery.me().findByMenuId(id);
//		MenuButton mb = new MenuButton();
//		mb.setMenu(menu);
//		mb.setButton(button);
		renderJson(menu);
	}
}
