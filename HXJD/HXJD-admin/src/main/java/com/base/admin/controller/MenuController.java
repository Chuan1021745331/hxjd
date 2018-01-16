package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.ButtonService;
import com.base.service.MenuService;
import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.CacheKit;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
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

	@Before(NewButtonInterceptor.class)
	public void index() {
		renderTable("menu.html");
	}
	
	public void buttonData() {
    	Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("menuId");
		
		long count = ButtonService.me().findConunt(where);
		List<JButton> list = ButtonService.me().findList(page, limit, where, count);      
        renderPageResult(0, "", (int)count, list);
    }
	public void add(){
		Integer menuId = getParaToInt("menuId");
		if(null != menuId && menuId.intValue() != 0){
			JMenu m = MenuService.me().findMenuById(menuId);
			setAttr("menu", m);
		}
		render("buttonAdd.html");
	}
	public void edit(){
		String id = getPara("id");
		JButton button = MenuService.me().findButtonById(Integer.parseInt(id));
		JMenu m = MenuService.me().findMenuById(button.getMenuId());
		
		setAttr("button", button);
		setAttr("menu", m);
		render("buttonEdit.html");
	}
	public void sel(){
		String id = getPara("id");
		JButton button = MenuService.me().findButtonById(Integer.parseInt(id));
		JMenu m = MenuService.me().findMenuById(button.getMenuId());
		
		setAttr("button", button);
		setAttr("menu", m);
		render("buttonSel.html");
	}
	
	public void del(){
		String id = getPara("id");
		//JButton button = ButtonQuery.me().findById(Integer.parseInt(id));
		boolean b = MenuService.me().del(id);
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}
	
	public void addM(){
		String pId = getPara("pId");
		JMenu m = MenuService.me().findMenuById(Integer.parseInt(pId));
		if(null == m){
			m=new JMenu();
			m.setId(0);
			m.setName("根节点");
		}
		setAttr("menu", m);
		render("menuAdd.html");
	}
	public void editM(){
		String id = getPara("id");
		JMenu m = MenuService.me().findMenuById(Integer.parseInt(id));
		JMenu pm = MenuService.me().findMenuById(m.getParent());
		
		setAttr("menu", m);
		setAttr("pmenu", pm);
		render("menuEdit.html");
	}
	
	public void icon(){
		render("icon.html");
	}
	
	public void saveOrUpdata(){
		JButton button = getModel(JButton.class);
		boolean b = ButtonService.me().saveOrUpdate(button);
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}
	
	public void saveOrUpdataForMenu(){
		JMenu menu = getModel(JMenu.class);
		JMenu jMenu = MenuService.me().saveOrUpdataForMenu(menu);
		if(null != jMenu){
			renderAjaxResult("", 0, jMenu);
		}else{
			renderAjaxResultForError();
		}
	}
	
	public void tree() {
		List<MenuSimpDto> m = MenuService.me().getMenusSimp();
		renderJson(m);
	}
	
	public void addTree() throws UnsupportedEncodingException {
		Integer pId = getParaToInt("pId");
		String name = getPara("name");
		JMenu menu = MenuService.me().addTree(pId, name);
		
		/*JMenu menu = new JMenu();
		menu.setParent(pId);
		menu.setName(name);
		boolean b = menu.save();
		CacheKit.removeAll("menu");*/
		if(null != menu){
			ButtonService.me().JButtonE(menu.getId(), "新增", "add");
			ButtonService.me().JButtonE(menu.getId(), "删除", "del");
			ButtonService.me().JButtonE(menu.getId(), "修改", "edit");
			ButtonService.me().JButtonE(menu.getId(), "详情", "sel");
			renderJson(menu);
		}else{
			renderAjaxResultForError();
		}
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
			
			JMenu menu = MenuService.me().editTree(id, parent, name, url, tag, ico, sort);
			CacheKit.removeAll("menu");
			if(null != menu){
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
		List<JButton> menuButton = ButtonService.me().findByMenuId(menuId);
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
			String add = getPara("add");
			String del = getPara("del");
			String edit = getPara("edit");
			String sel = getPara("sel");
			String[] newButtonName = getParaValues("newButtonName");
			String[] newButtonCode = getParaValues("newButtonCode");
			String[] newButtonUrl = getParaValues("newButtonUrl");
			ButtonService.me().addButton(menuId, add, del, edit, sel, newButtonName, newButtonCode, newButtonUrl);			
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
		boolean b = ButtonService.me().delTree(id);
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}
	
	public void getMenu(){
		Integer id = getParaToInt("id");
		JMenu menu = MenuService.me().findMenuById(id);
//		List<JButton> button = ButtonQuery.me().findByMenuId(id);
//		MenuButton mb = new MenuButton();
//		mb.setMenu(menu);
//		mb.setButton(button);
		renderJson(menu);
	}
}
