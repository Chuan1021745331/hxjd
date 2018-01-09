package com.base.web;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.core.BaseController;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: IndexController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 后台首页的相关操作   
 * @所属: 华夏九鼎     
 * @日期: 2017年5月15日 上午9:37:41   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/", viewPath = "/view/web")
@RouterNotAllowConvert
public class IndexController extends BaseController {
	String a= "";
	public void index() {
		render("index.html");
	}
}
