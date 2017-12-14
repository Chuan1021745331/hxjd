package com.base.admin.controller;

import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.jfinal.aop.Before;

/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: UserController.java
 * @包名: com.base.web.controller.admin
 * @描述: 用于用户管理相关操作
 * @所属: 华夏九鼎
 * @日期: 2017年5月15日 上午9:34:14
 * @版本: V1.0
 * @创建人：z
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/position", viewPath = "/view/admin/map/")
@RouterNotAllowConvert
public class MapController extends BaseController {
    @Before(ButtonInterceptor.class)
    public void index() {
        render("position.html");
    }
}
