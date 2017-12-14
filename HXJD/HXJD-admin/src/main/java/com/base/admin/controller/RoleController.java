package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.ButtonQuery;
import com.base.service.MenuQuery;
import com.base.service.RoleMenuButtonQuery;
import com.base.service.RoleQuery;
import com.base.service.UserQuery;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JButton;
import com.base.model.JRole;
import com.base.model.JRolemenubutton;
import com.base.model.dto.MenusButtonsDto;
/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: RoleController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 用于权限相关的操作  
 * @所属: 华夏九鼎     
 * @日期: 2017年5月15日 上午9:35:45   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/role", viewPath = "/view/admin/role")
@RouterNotAllowConvert
public class RoleController extends BaseController {
	
	@Before(NewButtonInterceptor.class)
	public void index() {
		renderTable("role.html");
	}
	
	public void roleData() {
		/*Integer draw = getParaToInt("draw");
		Integer start = getParaToInt("start");
		Integer length = getParaToInt("length");
		Integer column = getParaToInt("order[0][column]");
		String order = getPara("order[0][dir]");
		String search = getPara("search[value]");
		
		List<JRole> list = RoleQuery.me().findList(start, length,column,order,search);
		
		long count = RoleQuery.me().findConunt(search);
		renderPageResult(draw, (int)count, (int)count, list);*/
		
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		
		List<Record> list = new ArrayList<Record>();
		long count = RoleQuery.me().findConunt(where);
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
	        //list = UserQuery.me().findListUserRole(page, limit,where);
			list = RoleQuery.me().findListRole(page, limit, where);
		}
		renderPageResult(0, "", (int)count, list);
		
	}
	public void tree() {
		Integer roleId = getParaToInt("id");
		Map<String, Object> map = new HashMap<String, Object>();
		List<JRolemenubutton> rmb = RoleMenuButtonQuery.me().findListByRoleId(roleId);
		map.put("tree", MenuQuery.me().getMenusSimp());
		map.put("tree_", rmb);
		renderJson(map);
	}
	
	public void details() {
		Integer id = getParaToInt("id");
		JRole role = RoleQuery.me().findById(id);
		setAttr("role", role);
		render("details.html");
	}
	public void add() {
		render("add.html");
	}
	public void getButton(){
		Integer id = getParaToInt("id");
		List<JButton> buttons = ButtonQuery.me().findByMenuId(id);
		renderJson(buttons);
	}
	public void getCheckButton(){
		Integer mid = getParaToInt("mid");
		Integer rid = getParaToInt("rid");
		List<JButton> buttons = ButtonQuery.me().findByMenuId(mid);
		JRolemenubutton mb = RoleMenuButtonQuery.me().findListByRoleIdAndMenuId(rid, mid);
		List<MenusButtonsDto> mbs = new ArrayList<MenusButtonsDto>();
		String[] mArray=null;
		if(null!=mb){
			mArray = StringUtils.isNotEmpty(mb.getButtons())?mb.getButtons().split("\\|"):null;
		}
		
		for (JButton b : buttons) {
			MenusButtonsDto dto = new MenusButtonsDto();
			dto.setId(b.getId());
			dto.setName(b.getName());
			dto.setCode(b.getCode());
			dto.setTf(false);
			if(null!=mArray){
				List<String> list=Arrays.asList(mArray);
				if(list.contains(b.getCode())){
					dto.setTf(true);
				}
			}
			mbs.add(dto);
		}
		renderJson(mbs);
	}
	public void addSave() {
		JRole jr = getModel(JRole.class);
		
		boolean a = jr.save();
		if(a){
			String roleMenus = getPara("roleMenus");
			
			if(roleMenus.length()>1){
				String[] rms = roleMenus.split(",");
				for (int i = 0; i < rms.length; i++) {
					StringBuffer sb = new StringBuffer();
					String[] buttons = getParaValues("checkbox_"+rms[i]);
					if(null !=buttons){
						for (int j = 0; j < buttons.length; j++) {
							sb.append(buttons[j]+"|");
						}
					}
					
					JRolemenubutton jmb= new JRolemenubutton();
					jmb.setRoleId(jr.getId());
					jmb.setMenuId(new Integer(rms[i]));
					if(sb.length()>0){
						jmb.setButtons(sb.toString().substring(0, sb.toString().length()-1));
					}
					jmb.save();
					
				}
			}
			//renderAjaxResultForSuccess();
			renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
			return ;
		}
		//renderAjaxResultForError();
		renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		JRole role = RoleQuery.me().findById(id);
		setAttr("role", role);
		render("edit.html");
	}
	public void editSave() {
		JRole jr = getModel(JRole.class);
		boolean a = jr.update();
		if(a){
			String roleMenus = getPara("roleMenus");
			RoleMenuButtonQuery.me().delbyRoleId(jr.getId());
			CacheKit.remove("role", jr.getId());
			CacheKit.removeAll("menu");
			if(roleMenus.length()>1){
				String[] rms = roleMenus.split(",");
				for (int i = 0; i < rms.length; i++) {
					StringBuffer sb = new StringBuffer();
					String[] buttons = getParaValues("checkbox_"+rms[i]);
					if(null !=buttons){
						for (int j = 0; j < buttons.length; j++) {
							sb.append(buttons[j]+"|");
						}
					}
					
					JRolemenubutton jmb= new JRolemenubutton();
					jmb.setRoleId(jr.getId());
					jmb.setMenuId(new Integer(rms[i]));
					jmb.setButtons(sb.toString());
					System.out.println("buttons: " + sb.toString());
					jmb.saveOrUpdate();
				}
			}
			
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
	}
	public void del() {
		Integer id = getParaToInt("id");
		JRole option = RoleQuery.me().findById(id);
		boolean a = option.delete();
		CacheKit.remove("role", id);
		if(a){
			RoleMenuButtonQuery.me().delbyRoleId(id);
			renderAjaxResultForSuccess();
			return ;
		}
		renderAjaxResultForError();
	}
}
