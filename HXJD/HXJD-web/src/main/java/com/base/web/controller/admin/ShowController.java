package com.base.web.controller.admin;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.core.BaseController;

@RouterMapping(url = "/admin/show", viewPath = "/view/admin/show")
@RouterNotAllowConvert
public class ShowController extends BaseController {

	public void ftext() {
		render("ftext.html");
	}

}
