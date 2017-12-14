package com.base.web.controller.admin;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.RoleQuery;
import com.base.service.UserQuery;
import com.base.service.UserRoleQuery;
import com.base.service.app.AppHandle.AppBean.MessageBean;
import com.base.utils.EncryptUtils;
import com.base.utils.GZipUtil;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import com.alibaba.fastjson.JSON;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.im.common.IMPacket;
import com.base.im.server.IMServerStarter;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JRole;
import com.base.model.JUser;
import com.base.model.JUserrole;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 用于用户管理相关操作   
 * @所属: 华夏九鼎     
 * @日期: 2017年5月15日 上午9:34:14   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/user", viewPath = "/view/admin/user")
@RouterNotAllowConvert
public class UserController extends BaseController {

	@Before(ButtonInterceptor.class)
	public void index() {
		render("user.html");
	}
	public static void main(String[] args) {
//		Map<String, Object> m = new HashMap<String, Object>();
//		m.put("handle", 1);
//		m.put("time", "20170525");
//		System.out.println(JSON.toJSON(m));
		String a = DateTime.now().toDate().getTime()+"";
		System.out.println(a.getBytes().length);
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
		Integer draw = getParaToInt("draw");
		Integer start = getParaToInt("start");
		Integer length = getParaToInt("length");
		Integer column = getParaToInt("order[0][column]");
		String order = getPara("order[0][dir]");
		String search = getPara("search[value]");
		
		List<Record> list = UserQuery.me().findListUserRole(start, length,column,order,search);
		long count = UserQuery.me().findConuntUserRole(search);
		renderPageResult(draw, (int)count, (int)count, list);
	}
	
	public void details() {
		Integer id = getParaToInt("id");
		JUser user = UserQuery.me().findById(id);
		JRole role = RoleQuery.me().findByUserId(id);
		
		setAttr("role", role);
		setAttr("user", user);
		render("details.html");
	}
	public void edit() {
		Integer id = getParaToInt("id");
		JUser user = UserQuery.me().findById(id);
		List<JRole> roles = RoleQuery.me().getAll();
		JRole role = RoleQuery.me().findByUserId(id);
		
		setAttr("role", role);
		setAttr("roles", roles);
		setAttr("user", user);
		render("edit.html");
	}
	public void add() throws Exception {
		try {
			ChannelContext<Object, IMPacket, Object> a = (ChannelContext<Object, IMPacket, Object>) IMcacheMap.cacheMap.get("b9dd2b1cf3b0aea4ea59dc2b6a716098");
			IMPacket resppacket = new IMPacket();
			MessageBean mesg=new MessageBean();
	        mesg.setMsgType("0");
	        mesg.setSendTime("20170101");
	        mesg.setSender("52");
	        mesg.setGroupId(5);
	        mesg.setMsg("dsfsfdsf");
			resppacket.setBody(GZipUtil.doZip(JSON.toJSONString(mesg)));
			Aio.send(a, resppacket);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		List<JRole> roles = RoleQuery.me().getAll();
		
		setAttr("roles", roles);
		render("add.html");
	}
	
	public void editSave() {
		JUser user = getModel(JUser.class);
		JUser u = UserQuery.me().findById(user.getId());
		u.setNickname(user.getNickname());
		u.setRelname(user.getRelname());
		u.setEmail(user.getEmail());
		u.setGender(user.getGender());
		u.setMobile(user.getMobile());
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
	
	public void del() {
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
}
