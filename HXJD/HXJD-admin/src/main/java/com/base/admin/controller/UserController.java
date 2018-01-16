package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.model.JDepartment;
import com.base.model.dto.TreeSimpDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DepartmentService;
import com.base.service.RoleService;
import com.base.service.UserService;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

import java.util.ArrayList;
import java.util.List;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JRole;
import com.base.model.JUser;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java   
 * @包名: com.base.admin.controller   
 * @描述: 后台用户管理  
 * @所属: 华夏九鼎     
 * @日期: 2017年11月20日 上午11:54:38   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/user", viewPath = "/view/admin/user")
@RouterNotAllowConvert
public class UserController extends BaseController {

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
		
		long count = UserService.me().findConuntUserRole();
		List<Record> list = UserService.me().findListUserRole(page, limit, where, count);

		renderPageResult(0, "", (int)count, list);
	}
	
	public void add(){
		List<JRole> roles = RoleService.me().getAll();
		TreeSimpDto departmentes = DepartmentService.me().getDepartmentSimp(0);
		setAttr("roles", roles);
		render("add.html");
	}
	
	public void edit(){
		Integer id = getParaToInt("id");
		
		JUser user = UserService.me().findById(id);
		List<JRole> roles = RoleService.me().getAll();
		JRole role = RoleService.me().findByUserId(id);
		JDepartment department = DepartmentService.me().findDepartmentByUserId(id);
		List<JDepartment> parents=new ArrayList<>();
		if(department!=null){
			parents = DepartmentService.me().getParents(department.getParentId());
			parents.add(department);
		}
		setAttr("role", role);
		setAttr("roles", roles);
		setAttr("department",department);
		setAttr("parents", JSON.toJSON(parents));
		setAttr("user", user);
		render("edit.html");
	}
	
	public void sel(){
		Integer id = getParaToInt("id");
		JUser user = UserService.me().findById(id);;
		JRole role = RoleService.me().findByUserId(id);
		JDepartment department = DepartmentService.me().findDepartmentByUserId(id);
		List<JDepartment> parents=new ArrayList<>();
		if(department!=null){
			parents = DepartmentService.me().getParents(department.getParentId());

		}
		setAttr("role", role);
		setAttr("user", user);
		setAttr("department",department);
		setAttr("parents",parents);
		render("sel.html");
	}
	
	public void del(){
		Integer id = getParaToInt("id");
	
		boolean a = UserService.me().delUserById(id);				
		if(a){			
			renderAjaxResultForSuccess("删除成功");			
			return ;
		}
		renderAjaxResultForError("删除失败，请重试！");
	}
	
	public void addSave() {
		JUser user = getModel(JUser.class);
		Integer departmentId = getParaToInt("departmentId");
		Integer roleId = getParaToInt("role");
		boolean a = UserService.me().addUserSave(user, roleId,departmentId);
		if(a){
			renderAjaxResultForSuccess("新增用户成功");
			return ;
		}
		renderAjaxResultForError("新增用户失败，请重试！");
	}
	
	public void editSave() {
		UploadFile uploadFile = this.getFile();
		Integer roleId = getParaToInt("role");
		Integer departmentId = getParaToInt("departmentId");
		JUser user = getModel(JUser.class);
		
		boolean a = UserService.me().editUserSave(user, uploadFile, roleId,departmentId);
		
		if(a){
			renderAjaxResultForSuccess("修改用户成功");
			return ;	
		}
		renderAjaxResultForError("修改用户失败，请重试！");
	}
	
/*	public void saveOrUpdata(){
		JButton button = getModel(JButton.class);
		boolean b = button.saveOrUpdate();
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}*/
}
