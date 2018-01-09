package com.base.web.controller.web;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.UserService;
import com.base.utils.CookieUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Clear;
import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.AdminInterceptor;
import com.base.interceptor.WebInterceptor;
import com.base.message.Actions;
import com.base.message.MessageKit;
import com.base.model.JUser;
/**
 * 首页
 * @author z
 *
 */
@RouterMapping(url = "/", viewPath = "/view/web")
@RouterNotAllowConvert
public class IndexController extends BaseController {

	public void index() {
		
		render("index.html");
	}
	public void main() {
		
		render("main.html");
	}
	
	@Clear(WebInterceptor.class)
	public void login() {
		String username = getPara("username");
		String password = getPara("password");
		
		if (!StringUtils.areNotEmpty(username, password)) {
			render("login.html");
			return;
		}
		JUser user = UserService.me().findUserByUserName(username);
		if (null == user) {
			renderAjaxResultForError(MessageConstants.USER_NULL);
			return;
		}
		
		boolean a =  UserService.me().login(user, password);
		if(a){
			MessageKit.sendMessage(Actions.USER_LOGINED, user);
			CookieUtils.put(this, Consts.COOKIE_LOGINED_USER, user.getId().toString());
			renderAjaxResultForSuccess(MessageConstants.USER_SUCCESS);
		} else {
			renderAjaxResultForError(MessageConstants.PASS_ERROR);
		}
	}
}
