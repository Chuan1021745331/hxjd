package com.base.web.controller.web;

import com.base.model.JCircuit;
import com.base.model.JVisitor;
import com.base.model.dto.CircuitWorksiteDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.CircuitService;
import com.base.service.UserService;
import com.base.service.VisitorService;
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

import java.util.List;

/**
 * 首页
 * @author z
 *
 */
@RouterMapping(url = "/", viewPath = "/view/web")
@RouterNotAllowConvert
public class IndexController extends BaseController {

	public void index() {
		List<CircuitWorksiteDto> circuitWorksiteDtos = CircuitService.me().getCircuitWorksiteDtos();
		setAttr("circuitWorksiteDtos",circuitWorksiteDtos);
		render("index.html");
	}
	public void main() {
		
		render("main.html");
	}
	
	@Clear(WebInterceptor.class)
	public void login() {
		String visitorname = getPara("username");
		String password = getPara("password");
		if (!StringUtils.areNotEmpty(visitorname, password)) {
			render("login.html");
			return;
		}
		JVisitor visitor = VisitorService.me().findByVisitorName(visitorname);
		if(null==visitor){
			renderAjaxResultForError(MessageConstants.USER_NULL);
			return ;
		}
		boolean b = VisitorService.me().login(visitor, password);
		if(b){
			MessageKit.sendMessage(Actions.VISITOR_LOGINED, visitor);
			CookieUtils.put(this, Consts.COOKIE_LOGINED_VISITOR, visitor.getId().toString());
			renderAjaxResultForSuccess(MessageConstants.USER_SUCCESS);
		}else {
			renderAjaxResultForError(MessageConstants.PASS_ERROR);
		}
	}

	@Clear(WebInterceptor.class)
	public void logout() {
		CookieUtils.remove(this, Consts.COOKIE_LOGINED_VISITOR);
		redirect("/login");
	}
}
