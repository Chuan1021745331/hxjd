package com.base.admin.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.constants.NewsConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JNews;
import com.base.model.JNewstype;
import com.base.model.JUser;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.NewsService;
import com.base.service.UserService;
import com.base.utils.AttachmentUtils;
import com.base.utils.CookieUtils;
import com.base.utils.DateUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.core.JFinal;
import com.jfinal.json.FastJson;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: NewsController.java   
 * @包名: com.base.web.controller.web   
 * @描述: admin-新闻/公告Controller 
 * @所属: 华夏九鼎     
 * @日期: 2018年1月18日 下午1:56:51   
 * @版本: V1.0 
 * @创建人：kevin 
 * @修改人：kevin
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/news", viewPath = "/view/admin/news")
@RouterNotAllowConvert
public class NewsController extends BaseController {
	private static final Logger log= LoggerFactory.getLogger(NewsController.class);
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Before(NewButtonInterceptor.class)
	public void index() {
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void projectdoc(){
		setAttr("type",4);
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void inspection(){
		setAttr("type",5);
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void handleinfo(){
		setAttr("type",6);
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void checkreport(){
		setAttr("type",7);
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void safeinfo(){
		setAttr("type",8);
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void copyfile(){
		setAttr("type",9);
		renderTable("news.html");
	}

	@Before(NewButtonInterceptor.class)
	public void other(){
		setAttr("type",NewsConstants.OTHER_TYPE);
		renderTable("news.html");
	}

	public void newsData(){
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		int  type=1;
		try{
			type = getParaToInt("type");
		}catch (Exception e){
			type=NewsConstants.OTHER_TYPE;
			log.error(e.toString());
		}

		long count = NewsService.me().findConuntByType(type);
		List<Record> list = NewsService.me().findListNewsByType(page, limit, type, count);

		renderPageResult(0, "", (int)count, list);
	}
	
	public void add(){		
		List<JNewstype> list = NewsService.me().getNewsType();
		setAttr("newsType", list);
		render("add.html");
	}

	/**
	 * 获取普通信息类型
	 */
	public void getCommonNewsType(){
		List<JNewstype> newstypes = NewsService.me().getNewsTypeByType(NewsConstants.NEWS_TYPE_COMMON);
		renderAjaxResult("",0,newstypes);
	}

	public void addSave(){
		UploadFile upfile = getFile("attachment");
		//upfile.getFile();
		JNews news = getBean(JNews.class);
		if(null != upfile){
			String prefix = "news";
			String path = AttachmentUtils.moveFile(upfile,prefix);
			news.setAttachment(path);
			
			String fileName = upfile.getFileName();
			if(StringUtils.isNotEmpty(fileName)){
				news.setAttachmentName(fileName);
			}			
		}
		
		
		
		String typeName = getPara("typeName");
		if(StringUtils.isNotEmpty(typeName)){			
			JNewstype newsType =  NewsService.me().saveNewsType(typeName, new Integer(1));
			if(null != newsType){
				news.setType(newsType.getId());
			} else {
				renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
				return;
			}
		}
		
		String userId = CookieUtils.get(this, Consts.COOKIE_LOGINED_USER);
		JUser user = UserService.me().findById(new Integer(userId));
		if(StringUtils.isNotEmpty(user.getRelname())){
			news.setPostMan(user.getRelname());
		}
		news.setPostTime(new Date());
		boolean a =  NewsService.me().addSave(news);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
			return ;
		} else{
			renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
		}		
	}
	
	public void edit(){
		Integer id =  getParaToInt("id");
		Record news = NewsService.me().getById(id);
		List<JNewstype> type =  NewsService.me().getNewsType();
		setAttr("news", news);
		setAttr("type", type);
		render("edit.html");
	}
	
	public void editSave(){
		//UploadFile upfile = getFile("attachment");
		JNews news = getBean(JNews.class);
		String userId = CookieUtils.get(this, Consts.COOKIE_LOGINED_USER);
		JUser user = UserService.me().findById(new Integer(userId));
		if(StringUtils.isNotEmpty(user.getRelname())){
			news.setPostMan(user.getRelname());
		}
		news.setPostTime(new Date());
		boolean a = NewsService.me().editSave(news);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
			return ;
		} else {
			renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
		}
		
	}
	
	public void sel(){
		Integer id = getParaToInt("id");
		Record news = NewsService.me().getById(id);
		setAttr("news", news);
		render("sel.html");
	}
	
	public void del(){
		Integer id =  getParaToInt("id");
		boolean a = NewsService.me().delById(id);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
			return ;
		} else{
			renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
		}
	}
	
	/**
	 * 
	 * getAttachment(这里用一句话描述这个方法的作用)  
	 * 下载附件   
	 *void  
	 * @exception   
	 * @since  1.0.0
	 */
	public void getAttachment(){
		Integer id = getParaToInt("id");
		Record news = NewsService.me().getById(id);
		String project = JFinal.me().getContextPath();
		String str = JFinal.me().getServletContext().getRealPath("");
		int i = str.lastIndexOf("\\");
		String disk = str.substring(0,i);
		String path = disk + project.replaceAll("web", "admin") + news.get("attachment");
		String name = news.get("attachmentName");
		File f = new File(path);
		
		if(!f.exists()){
			renderAjaxResult("文件已失效！！！", 0);
			return;
		} else {
			//renderFile(f, name);
			renderAjaxResult("附件已下载！！！", 1);
			return;
		}		
	}
	
	public void downAttachment(){
		Integer id = getParaToInt("id");
		Record news = NewsService.me().getById(id);
		String project = JFinal.me().getContextPath();
		String str = JFinal.me().getServletContext().getRealPath("");
		int i = str.lastIndexOf("\\");
		String disk = str.substring(0,i);
		String path = disk + project.replaceAll("web", "admin") + news.get("attachment");
		String name = news.get("attachmentName");
		File f = new File(path);
		renderFile(f, name);
	
	}
		
}

