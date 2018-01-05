package com.base.interceptor;

import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.MenuDto;
import com.base.query.MenuQuery;
import com.base.service.MenuService;

import java.util.List;

import com.base.constants.UserConstants;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

public class AdminInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		String target = controller.getRequest().getRequestURI();
		String cpath = controller.getRequest().getContextPath();
		
		if (!target.startsWith(cpath + "/admin")) {
			inv.invoke();
			return;
		}
		JUser user = InterUtils.tryToGetUser(inv);
		if (user != null) {
			List<MenuDto> m = CacheKit.get("menu", user.getUsername());
			if(null == m){
				m = MenuService.me().getMenus(user);
				CacheKit.put("menu", user.getUsername(),m);
			}
			String reUrl="";
			List<JMenu> menuAll = MenuQuery.me().getAll();
			for (JMenu m_ : menuAll) {
				if(StringUtils.isNotEmpty(m_.getUrl())){
					if(target.contains(m_.getUrl())){
						reUrl=m_.getUrl();
						continue;
					}
				}
			}
			
			
			controller.setAttr(UserConstants.ATT_USER, user);
			controller.setAttr(UserConstants.ATT_MENUS, m);
			
			
			
			controller.setAttr("ucode", EncryptUtils.generateUcode(user.getId().toString(),user.getSalt()));
			controller.setAttr("sys_title", CacheKit.get("option", "sys_title"));
			controller.setAttr("copyright", CacheKit.get("option", "copyright"));
			controller.setAttr("admin_top_title", CacheKit.get("option", "admin_top_title"));
			controller.setAttr("reUrl", cpath+"/"+reUrl);
			inv.invoke();
			return;
		}
		controller.redirect("/admin/login");
	}

}
