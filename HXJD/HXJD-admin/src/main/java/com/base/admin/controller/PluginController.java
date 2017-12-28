package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.RoleQuery;
import com.base.service.UserQuery;
import com.base.service.UserRoleQuery;
import com.base.utils.AttachmentUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.upload.UploadFile;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JButton;
import com.base.model.JRole;
import com.base.model.JUser;
import com.base.model.JUserrole;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java   
 * @包名: com.base.admin.controller   
 * @描述: 插件管理 
 * @所属: 华夏九鼎     
 * @日期: 2017年11月20日 上午11:54:38   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/Plugin", viewPath = "/view/admin/plugin")
@RouterNotAllowConvert
public class PluginController extends BaseController {

	@Before(NewButtonInterceptor.class)
	public void index() {
		renderTable("user.html");
	}
	/**
	 * 
	 * userData(这里用一句话描述这个方法的作用)  
	 * (这里描述这个方法适用条件 – 可选)     
	 *void  
	 * @exception   
	 * @since  1.0.0
	 */
	public void userData() {
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		
		List<Record> list = new ArrayList<>();
		long count = UserQuery.me().findConuntUserRole();
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
	        list = UserQuery.me().findListUserRole(page, limit,where);
		}
		renderPageResult(0, "", (int)count, list);
	}
	public void add(){
		
		setAttr("roles", RoleQuery.me().getAll());
		render("add.html");
	}
	public void edit(){
		Integer id = getParaToInt("id");
		JUser user = UserQuery.me().findById(id);
		List<JRole> roles = RoleQuery.me().getAll();
		JRole role = RoleQuery.me().findByUserId(id);
		
		setAttr("role", role);
		setAttr("roles", roles);
		setAttr("user", user);
		render("edit.html");
	}
	public void sel(){
		String id = getPara("id");
		JUser user = UserQuery.me().findById(Integer.parseInt(id));
		
		JRole role = RoleQuery.me().findByUserId(Integer.parseInt(id));
		
		setAttr("role", role);
		
		setAttr("user", user);
		render("sel.html");
	}
	
	public void del(){
		Integer id = getParaToInt("id");
		JUser user = UserQuery.me().findById(id);
		
		boolean a = user.delete();
		CacheKit.remove("user", id);
		
		if(a){
			UserRoleQuery.me().delByUserId(id);
			renderAjaxResultForSuccess("删除成功");
			return ;
		}
		renderAjaxResultForError("删除失败，请重试！");
	}
	
	public void addSave() {
		JUser user = getModel(JUser.class);
		String salt = EncryptUtils.salt();
		user.setSalt(salt);
		user.setCreated(DateTime.now().toDate());
		if (StringUtils.isNotEmpty(user.getPassword())) {
			String password = EncryptUtils.encryptPassword(user.getPassword(), salt);
			user.setPassword(password);
		}
		
		boolean a = user.save();
		
		if(a){
			Integer roleId = getParaToInt("role");
			JUserrole jUserrole= new JUserrole();
			jUserrole.setUserId(user.getId());
			jUserrole.setRoleId(roleId);
			boolean b = jUserrole.save();
			if(b){
				renderAjaxResultForSuccess("新增用户成功");
				return ;
			}
		}
		renderAjaxResultForError("新增用户失败，请重试！");
	}
	
	public void editSave() {
		UploadFile uploadFile = this.getFile();
		
		JUser user = getModel(JUser.class);
		JUser u = UserQuery.me().findById(user.getId());
		u.setUsername(user.getUsername());
		u.setNickname(user.getNickname());
		u.setRelname(user.getRelname());
		u.setEmail(user.getEmail());
		u.setGender(user.getGender());
		u.setMobile(user.getMobile());
		
		
		if (null != uploadFile) {
			String newPath = AttachmentUtils.moveFile(uploadFile,"avatar");
			user.setAvatar(newPath);
			//System.out.println(newPath);
			u.setAvatar(user.getAvatar());
		}

		boolean a = u.update();

		CacheKit.remove("user", u.getId());
		if(a){
			if("administrator".equals(user.getRole())){
				renderAjaxResultForSuccess("修改用户成功");
				return ;
			}else{
				Integer roleId = getParaToInt("role");
				UserRoleQuery.me().delByUserId(user.getId());
				JUserrole jUserrole= new JUserrole();
				jUserrole.setUserId(user.getId());
				jUserrole.setRoleId(roleId);
				boolean b = jUserrole.save();
				if(b){
					renderAjaxResultForSuccess("修改用户成功");
					return ;
				}
			}
			
		}
		renderAjaxResultForError("修改用户失败，请重试！");
	}
	
	public void saveOrUpdata(){
		JButton button = getModel(JButton.class);
		boolean b = button.saveOrUpdate();
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}
}
