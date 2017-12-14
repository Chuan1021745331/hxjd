package com.base.web.controller.web;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.core.BaseController;
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
	
}
