package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.RoleService;
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
import com.base.query.ButtonQuery;
import com.base.query.MenuQuery;
import com.base.query.RoleMenuButtonQuery;
import com.base.query.RoleQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	private final static Logger logger= LoggerFactory.getLogger(RoleController.class);
	
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

	public void details() {
		Integer id = getParaToInt("id");
		JRole role = RoleService.me().findById(id);
		setAttr("role", role);
		render("details.html");
	}
	public void add() {
		render("add.html");
	}
	
	
	public void addSave() {
		JRole jr = getModel(JRole.class);
		//String roleMenus = getPara("roleMenus");
		//String[] buttons = getParaValues("checkbox_"+rms[i]);
		
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
		JRole role = RoleService.me().findById(id);
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
					logger.info("buttons: " + sb.toString());
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
		boolean a = RoleService.me().del(id);
		if(a){
			renderAjaxResultForSuccess();
			return;
		}
		renderAjaxResultForError();
	}
	
	public void tree() {
		Integer roleId = getParaToInt("id");
		Map<String, Object> map = RoleService.me().getTree(roleId);
		renderJson(map);
	}
	
	public void getButton(){
		Integer id = getParaToInt("id");
		List<JButton> buttons = RoleService.me().getButton(id);
		renderJson(buttons);
	}
	
	public void getCheckButton(){
		Integer mid = getParaToInt("mid");
		Integer rid = getParaToInt("rid");
			
		List<MenusButtonsDto> mbs = RoleService.me().getCheckButton(mid, rid);
		renderJson(mbs);
	}
}
