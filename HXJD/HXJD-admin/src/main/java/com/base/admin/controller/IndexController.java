package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.GroupQuery;
import com.base.service.MediatorQuery;
import com.base.service.MenuQuery;
import com.base.service.PelGroupQuery;
import com.base.service.PelQuery;
import com.base.service.SeatQuery;
import com.base.service.TerminalQuery;
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
		Long pel = PelQuery.me().findConunt();
		Long pelG = PelGroupQuery.me().findConunt(null);
		Long ter = TerminalQuery.me().findConunt(null);
		Long med = MediatorQuery.me().findConunt(null);
		Long sea = SeatQuery.me().findConunt(null);
		Long gro = GroupQuery.me().findConunt(null);
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
		JUser user = UserQuery.me().findById(new Integer(userId));
		if (null != user) {
			List<Map<String, Object>> um = CacheKit.get("menu", "userMenu_"+user.getUsername());
			if(null == um){
				List<MenuDto> m = CacheKit.get("menu", user.getUsername());
				um=new ArrayList<Map<String,Object>>();
				for (MenuDto m_:m) {
					JMenu m__ = m_.getM();
					Map<String, Object> mp = new HashMap<String, Object>();
					mp.put("id", m__.getId());
					mp.put("pid", m__.getParent());
					mp.put("title", m__.getName());
					mp.put("icon", m__.getIco());
					mp.put("url", m__.getUrl());
					mp.put("spread", false);
					if(m_.getN()>0){
						mp.put("children",getChild(m_.getChildren()));
					}
					
					um.add(mp);
				}
				CacheKit.put("menu", "userMenu_"+user.getUsername(),um);
			}
			renderJson(um);
			return;
		}
		redirect("/admin/login");
	}
	
	private List<Map<String, Object>> getChild(List<MenuDto> ms){
		List<Map<String, Object>> mts = new ArrayList<Map<String, Object>>();
		for (MenuDto m:ms) {
			Map<String, Object> mp = new HashMap<String, Object>();
			mp.put("id", m.getM().getId());
			mp.put("pid", m.getM().getParent());
			mp.put("title", m.getM().getName());
			mp.put("icon", m.getM().getIco());
			mp.put("url", m.getM().getUrl());
			mp.put("spread", false);
			if(m.getN()>0){
				mp.put("children",getChild(m.getChildren()));
			}
			mts.add(mp);
		}
		return mts;
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
		redirect("/admin/login");
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
		System.out.println(temp+"common\\ueditor\\dialogs\\template\\config.js");
	}
	public static void main(String[] args) {
		try {
			ChannelContext<Object, IMPacket, Object> cc = Aio.getChannelContextByUserid(IMServerStarter.serverGroupContext, "1234");
			IMPacket resppacket = new IMPacket();
			resppacket.setBody("推送消息".getBytes(IMPacket.CHARSET));
			Aio.send(cc, resppacket);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}
