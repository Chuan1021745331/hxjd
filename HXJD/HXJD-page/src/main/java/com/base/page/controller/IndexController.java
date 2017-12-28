package com.base.page.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.MenuQuery;
import com.base.service.UserQuery;
import com.base.utils.CookieUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.IPUtils;
import com.base.utils.ServerUtil;
import com.base.utils.StringUtils;
import com.base.utils.SystemInfo;
import com.base.utils.SystemUtils;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.ehcache.CacheKit;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.joda.time.DateTime;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;

import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.common.IMPacket;
import com.base.im.server.IMServerStarter;
import com.base.interceptor.AdminInterceptor;
import com.base.message.Actions;
import com.base.message.MessageKit;
import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.MenuDto;
import com.base.page.service.PageService;

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
@RouterMapping(url = "/page", viewPath = "/view/page")
@RouterNotAllowConvert
public class IndexController extends BaseController {

	public void index() {
		setAttr("list", PageService.me().getAll());
		render("index.html");
	}
}
