package com.base.admin.controller;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.jfinal.aop.Before;

/**
 * ----------暂未使用------------
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java   
 * @包名: com.base.admin.controller   
 * @描述: 插件管理 
 * @所属: 华夏九鼎     
 * @日期: 2017年11月20日 上午11:54:38   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/Plugin", viewPath = "/view/admin/plugin")
@RouterNotAllowConvert
public class PluginController extends BaseController {

	@Before(NewButtonInterceptor.class)
	public void index() {
		renderTable("user.html");
	}

}
