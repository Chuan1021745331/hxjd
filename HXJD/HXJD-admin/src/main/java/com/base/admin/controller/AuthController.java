package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.MenuService;
import com.base.query.MenuQuery;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.model.JMenu;
/**
 * 权限管理
 * @author z
 *
 */
@RouterMapping(url = "/admin/auth", viewPath = "/view/admin/auth")
@RouterNotAllowConvert
public class AuthController extends BaseController {

	public void index() {
		render("auth.html");
	}
	
	public void tree() {
		renderJson(MenuService.me().getMenusSimp());
	}
	
	public void addTree() throws UnsupportedEncodingException {
		Integer pId = getParaToInt("pId");
		String name = new String(getPara("name").getBytes("iso8859_1"),"utf-8");
		JMenu menu= MenuService.me().addTree(pId, name);
		if(null != menu){
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
			if(null != menu){
				renderAjaxResult("", 0, menu);
			}else{
				renderAjaxResultForError("密码修改失败，请重试！");
			}
		} catch (Exception e) {
			renderAjaxResultForError(MessageConstants.EDIT_TREE_NONE_PARENT);
		}
		
	}
	
	public void optionIco(){
		render("ico.html");
	}
	
	public void delTree() {
		Integer id = getParaToInt("id");
		boolean b = MenuService.me().delTree(id); 
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}
	
	public void getMenu(){
		Integer id = getParaToInt("id");
		JMenu menu = MenuService.me().findMenuById(id);
		renderJson(menu);
	}
}
