package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.query.MenuQuery;
import com.base.service.IndexService;
import com.base.service.MenuService;
import com.base.service.UserService;
import com.base.utils.CookieUtils;
import com.base.utils.EncryptUtils;
import com.base.utils.GZipUtil;
import com.base.utils.IPUtils;
import com.base.utils.ServerUtil;
import com.base.utils.StringUtils;
import com.base.utils.SystemInfo;
import com.base.utils.SystemUtils;
import com.cybermkd.mongo.kit.MongoQuery;
import com.jfinal.aop.Clear;
import com.jfinal.plugin.ehcache.CacheKit;
import com.xiaoleilu.hutool.util.ThreadUtil;
import com.xiaoleilu.hutool.util.ZipUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.servlet.ServletContext;

import org.joda.time.DateTime;

import com.alibaba.fastjson.JSONObject;
import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.AdminInterceptor;
import com.base.message.Actions;
import com.base.message.MessageKit;
import com.base.model.JMenu;
import com.base.model.JUser;

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

	public void index() {
		render("index.html");
	}
	
	public void main() {
		
		System.out.println(getRequest().getSession().getServletContext().getRealPath("/"));
		Long pel = 0L;
		Long pelG = 0L;
		Long ter = 0L;
		Long med = 0L;
		Long sea = 0L;
		Long gro = 0L;
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
		Map<String, Long> map = new HashMap<>();
		map.put("pel", pel);
		map.put("pelG", pelG);
		map.put("ter", ter);
		map.put("med", med);
		map.put("sea", sea);
		map.put("gro", gro);
		
		setAttr("map", map);
		render("main.html");
	}
	
	public void getMenuss(){		
		String userId = CookieUtils.get(this, Consts.COOKIE_LOGINED_USER);		
		JUser user = UserService.me().findById(new Integer(userId));
		if (null != user) {
			List<Map<String, Object>> um = CacheKit.get("menu", "userMenu_"+user.getUsername());
			if(null == um){
				um = IndexService.me().getMenuss(um, user);			
			}
			renderJson(um);
			return;
		}
		redirect("/admin/login");
	}
	
	
	

	@Clear(AdminInterceptor.class)
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
	
	@Clear(AdminInterceptor.class)
	public void checkUserCookie(){
		String userId = CookieUtils.get(this, Consts.COOKIE_LOGINED_USER);
		if (null != userId && !"".equals(userId)){
			renderAjaxResultForSuccess();
		} else {
			renderAjaxResultForError();
		}
	}
	
	@Clear(AdminInterceptor.class)
	public void logout() {
		CookieUtils.remove(this, Consts.COOKIE_LOGINED_USER);
		redirect("/admin/login");
	}

	public void aData() {
		Integer draw = getParaToInt("draw");
		Integer start = getParaToInt("start");
		Integer length = getParaToInt("length");
		Integer column = getParaToInt("order[0][column]");
		String order = getPara("order[0][dir]");
		String search = getPara("search[value]");
		
		
		List<JMenu> list = MenuService.me().findList(start, length,column,order,search);
		long count = MenuService.me().findConunt(search);
		renderPageResult(draw, (int)count, (int)count, list);
	}
	public void editFile(){
		ServletContext s1=getRequest().getServletContext();
		String temp=s1.getRealPath("/");
		System.out.println(temp+"common\\ueditor\\dialogs\\template\\config.js");
	}
	public void test(){
		ThreadUtil.excAsync(new Runnable() {
			public void run() {
				while (true) {
					MongoQuery query = new MongoQuery();
					query.use("test").set("a", "a")
					.set("b", "a")
					.set("c", "a")
					.set("d", "a")
					.set("e", "a")
					.set("f", "a")
					.set("g", "a")
					.set("h", "a")
					.set("i", "a")
					.set("j", "a")
					.set("k", "a")
					.set("l", "a")
					.set("m", "a")
					.set("n", "a")
					.set("t", DateTime.now().toString("yyyy-MM-dd HH:mm:ss")).save();
				}
			}
		}, true);
		renderAjaxResultForSuccess();
	}
	public void test1(){
		MongoQuery query = new MongoQuery();
		renderAjaxResultForSuccess(query.use("test").count()+"");
	}
	public void test2() throws Exception{
		String time = getPara("time");
		MongoQuery query = new MongoQuery();
		List<JSONObject> list = query.use("test").eq("t", time).find();
		System.out.println(list.toString().getBytes().length);
		byte[] l = GZipUtil.doZip(list.toString());
		System.out.println(l.length);
		renderAjaxResultForSuccess(GZipUtil.unZipByte(l));
	}
	public void test3(){
		JUser user = UserService.me().findById(1);
		renderAjaxResultForSuccess(user.getUsername()+"");
	}
	public static void main(String[] args) {
//		try {
//			ChannelContext<Object, IMPacket, Object> cc = Aio.getChannelContextByUserid(IMServerStarter.serverGroupContext, "1234");
//			IMPacket resppacket = new IMPacket();
//			resppacket.setBody("推送消息".getBytes(IMPacket.CHARSET));
//			Aio.send(cc, resppacket);
//			
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//		}
		ZipUtil.unzip("C:\\Users\\Administrator\\Desktop\\page.jar", "C:\\Users\\Administrator\\Desktop\\page");
	}

}
