package com.base.interceptor;

import com.base.constants.Consts;
import com.base.model.JUser;
import com.base.model.JVisitor;
import com.base.query.UserQuery;
import com.base.service.VisitorService;
import com.base.utils.CookieUtils;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;


public class InterUtils {
	public static JUser tryToGetUser(Invocation inv) {
		String userId = CookieUtils.get(inv.getController(), Consts.COOKIE_LOGINED_USER);
		if (userId != null && !"".equals(userId)) {
			return UserQuery.me().findById(new Integer(userId));
		}

		return null;
	}

	public static JVisitor tryToGetVisitor(Invocation inv) {
		return tryToGetVisitor(inv.getController());
	}

	public static JVisitor tryToGetVisitor(Controller controller) {
		String visitorId = CookieUtils.get(controller, Consts.COOKIE_LOGINED_VISITOR);
		if (visitorId != null && !"".equals(visitorId)) {
			return VisitorService.me().findVisitorById(Integer.parseInt(visitorId));
		}

		return null;
	}
}
