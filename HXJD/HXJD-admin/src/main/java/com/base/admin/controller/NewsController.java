package com.base.admin.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.base.constants.Consts;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JNews;
import com.base.model.JNewstype;
import com.base.model.JUser;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.NewsService;
import com.base.service.UserService;
import com.base.utils.CookieUtils;
import com.base.utils.DateUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.json.FastJson;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.upload.UploadFile;

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
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Before(NewButtonInterceptor.class)
	public void index() {
		
		renderTable("news.html");
	}
	
	public void newsData(){
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		
		long count = NewsService.me().findConunt(where);
		List<Record> list = NewsService.me().findListNews(page, limit, where, count);

		renderPageResult(0, "", (int)count, list);
	}
	
	public void add(){		
		List<JNewstype> list = NewsService.me().getNewsType();
		setAttr("newsType", list);
		render("add.html");
	}
	
	public void addSave(){
		//UploadFile upfile = getFile("attachment");
		//upfile.getFile();
		JNews news = getBean(JNews.class);
		
		String typeName = getPara("typeName");
		if(StringUtils.isNotEmpty(typeName)){			
			JNewstype newsType =  NewsService.me().saveNewsType(typeName);
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
		setAttr("news", news);
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
}

