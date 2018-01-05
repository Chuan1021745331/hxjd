package com.base.interceptor;

import com.base.constants.Consts;
import com.base.model.JUser;
import com.base.query.UserQuery;
import com.base.utils.CookieUtils;
import com.jfinal.aop.Invocation;


public class InterUtils {
	public static JUser tryToGetUser(Invocation inv) {
		String userId = CookieUtils.get(inv.getController(), Consts.COOKIE_LOGINED_USER);
		if (userId != null && !"".equals(userId)) {
			return UserQuery.me().findById(new Integer(userId));
		}

		return null;
	}
}
