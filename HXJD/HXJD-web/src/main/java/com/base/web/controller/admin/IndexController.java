package com.base.web.controller.admin;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.query.MenuQuery;
import com.base.query.UserQuery;
import com.base.utils.CookieUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.IPUtils;
import com.base.utils.ServerUtil;
import com.base.utils.StringUtils;
import com.base.utils.SystemInfo;
import com.base.utils.SystemUtils;
import com.jfinal.aop.Clear;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.joda.time.DateTime;
import com.alibaba.fastjson.JSON;
import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.IMcacheMap;
import com.base.interceptor.AdminInterceptor;
import com.base.message.Actions;
import com.base.message.MessageKit;
import com.base.model.JMenu;
import com.base.model.JUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
@RouterMapping(url = "/admin", viewPath = "/view/admin")
@RouterNotAllowConvert
public class IndexController extends BaseController {
	private final static Logger logger= LoggerFactory.getLogger(IndexController.class);

	public void index() {
		
		int cpu = Runtime.getRuntime().availableProcessors();
		long totalMemory = Runtime.getRuntime().totalMemory();		//虚拟机内存总量
		long freeMemory = Runtime.getRuntime().freeMemory();		//虚拟机空闲内存量
		long maxMemory = Runtime.getRuntime().maxMemory();			//虚拟机使用最大内存量
		Double compare = (Double)(1 - freeMemory * 1.0 / totalMemory) * 100;
		String serverId = ServerUtil.getServerId();
//		String nodepath = this.getClass().getClassLoader().getResource("/").getPath();  
		// 项目的根目录路径  
		
		setAttr("localIP", IPUtils.getLocalIP(getRequest()));
		setAttr("serverIP", IPUtils.getServerIP());
		setAttr("serverName", IPUtils.getServerName());
		setAttr("osName", SystemUtils.getSystem().getProperty("os.name"));
		setAttr("osArch", SystemUtils.getSystem().getProperty("os.arch"));
		setAttr("osUserName", SystemInfo.getInstance(getRequest()).getOs_user_name());
		setAttr("javaVersion", SystemUtils.getSystem().getProperty("java.version"));
		
		setAttr("cpu", cpu);
		setAttr("totalMemory", new BigDecimal(totalMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),4,RoundingMode.HALF_DOWN));
		setAttr("freeMemory", new BigDecimal(freeMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),4,RoundingMode.HALF_DOWN));
		setAttr("maxMemory", new BigDecimal(maxMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),4,RoundingMode.HALF_DOWN));
		setAttr("serverId", serverId);
		setAttr("compare", compare);
		setAttr("osUserDir", getRequest().getSession().getServletContext().getRealPath("/"));
		render("index.html");
	}

	@Clear(AdminInterceptor.class)
	public void login() {
		String username = getPara("username");
		String password = getPara("password");

		if (!StringUtils.areNotEmpty(username, password)) {
			render("login.html");
			return;
		}

		JUser user = UserQuery.me().findUserByUserName(username);
		if (null == user) {
			renderAjaxResultForError(MessageConstants.USER_NULL);
			return;
		}

		if (EncryptUtils.verlifyUser(user.getPassword(), user.getSalt(), password)) {
			MessageKit.sendMessage(Actions.USER_LOGINED, user);

			CookieUtils.put(this, Consts.COOKIE_LOGINED_USER, user.getId().toString());
			user.setLogined(DateTime.now().toDate());
			user.saveOrUpdate();

			renderAjaxResultForSuccess(MessageConstants.USER_SUCCESS);
		} else {
			renderAjaxResultForError(MessageConstants.PASS_ERROR);
		}
	}

	@Clear(AdminInterceptor.class)
	public void logout() {
		CookieUtils.remove(this, Consts.COOKIE_LOGINED_USER);
		redirect("/admin");
	}

	public void aData() {
		Integer draw = getParaToInt("draw");
		Integer start = getParaToInt("start");
		Integer length = getParaToInt("length");
		Integer column = getParaToInt("order[0][column]");
		String order = getPara("order[0][dir]");
		String search = getPara("search[value]");
		
		
		List<JMenu> list = MenuQuery.me().findList(start, length,column,order,search);
		long count = MenuQuery.me().findConunt(search);
		renderPageResult(draw, (int)count, (int)count, list);
	}
	public void editFile(){
		ServletContext s1=getRequest().getServletContext();
		String temp=s1.getRealPath("/");
		logger.info(temp+"common\\ueditor\\dialogs\\template\\config.js");
	}
/*	public static void main(String[] args) {
		try {
			ChannelContext cc = Aio.getChannelContextByUserid(IMServerStarter.serverGroupContext, "1234");
			IMPacket resppacket = new IMPacket();
			resppacket.setBody("推送消息".getBytes(IMPacket.CHARSET));
			Aio.send(cc, resppacket);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}*/
	
	public void getUnusedTerminal(){
		//IMcacheMap.cacheMap.put("unTerminalList", unTerminalList);
		List<Map<String, String>> list =  (List<Map<String, String>>) IMcacheMap.cacheMap.get("unTerminalList");
		String str =  JSON.toJSONString(list);
		renderText(str);
	}
	


}
