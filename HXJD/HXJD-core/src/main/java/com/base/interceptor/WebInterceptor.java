package com.base.interceptor;

import com.base.model.JDepartment;
import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.JVisitor;
import com.base.model.dto.MenuDto;
import com.base.query.MenuQuery;
import com.base.service.DepartmentService;
import com.base.service.MenuService;

import java.util.ArrayList;
import java.util.List;

import com.base.constants.UserConstants;
import com.base.utils.EncryptUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import com.jfinal.plugin.ehcache.CacheKit;

public class WebInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		String target = controller.getRequest().getRequestURI();
		String cpath = controller.getRequest().getContextPath();
		
		JVisitor visitor=InterUtils.tryToGetVisitor(inv);
		if (visitor != null) {
			JDepartment position = DepartmentService.me().findDepartmentByVisitorId(visitor.getId());
			List<JDepartment> parents = new ArrayList<>();
			if(null!=position){
				parents=DepartmentService.me().getParents(position.getParentId());
			}
			controller.setAttr(UserConstants.ATT_VISITOR, visitor);
			controller.setAttr("position",position);
			controller.setAttr("parents",parents);
			controller.setAttr("ucode", EncryptUtils.generateUcode(visitor.getId().toString(),visitor.getSalt()));
			controller.setAttr("sys_title", CacheKit.get("option", "sys_title"));
			controller.setAttr("tbm_logo", CacheKit.get("option", "tbm_logo"));
			controller.setAttr("tbm_title", CacheKit.get("option", "tbm_title"));
			controller.setAttr("copyright", CacheKit.get("option", "copyright"));
			inv.invoke();
			return;
		}
		controller.redirect("/login");
	}

}
