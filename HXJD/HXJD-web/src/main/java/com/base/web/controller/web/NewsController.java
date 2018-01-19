package com.base.web.controller.web;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.base.core.BaseController;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.NewsService;
import com.base.service.UserService;
import com.jfinal.json.FastJson;
import com.jfinal.plugin.activerecord.Record;


/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: NewsController.java   
 * @包名: com.base.web.controller.web   
 * @描述: 新闻/公告Controller 
 * @所属: 华夏九鼎     
 * @日期: 2018年1月18日 下午1:56:51   
 * @版本: V1.0 
 * @创建人：kevin 
 * @修改人：kevin
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/news", viewPath = "/view/news")
@RouterNotAllowConvert
public class NewsController extends BaseController {

	public void index() {
		
		render("news.html");
	}
	
	public void newsData(){
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		
		long count = NewsService.me().findConunt(where);
		List<Record> list = NewsService.me().findListNews(page, limit, where, count);

		renderPageResult(0, "", (int)count, list);
	}
	
	public void getIndexNews(){
		List<Record> list =  NewsService.me().getIndexNews();
		String str = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
		renderText(str);
		
	}
	
}
