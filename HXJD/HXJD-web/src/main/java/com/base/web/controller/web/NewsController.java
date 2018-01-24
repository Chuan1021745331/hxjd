package com.base.web.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.base.core.BaseController;
import com.base.model.JNewstype;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.NewsService;
import com.base.service.UserService;
import com.base.utils.StringUtils;
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
@RouterMapping(url = "/news", viewPath = "/view/web/news")
@RouterNotAllowConvert
public class NewsController extends BaseController {

	public void index() {
		List<Record> list =  NewsService.me().getIndexNews();
		
		List<JNewstype> newsType = NewsService.me().getNewsType();
		for (Record news : list) {
			String content =  news.getStr("content");
			if(StringUtils.isNotEmpty(content)){
				String con = subStringHTML(content, 140, "……");
				news.set("con", con);
			}			
		}
		setAttr("newsType", newsType);
		setAttr("news",list);
		render("news.html");
	}
	
/*	public void newsData(){
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		
		long count = NewsService.me().findConunt(where);
		List<Record> list = NewsService.me().findListNews(page, limit, where, count);

		renderPageResult(0, "", (int)count, list);
	}*/
	
	public void getIndexNews(){
		List<Record> list =  NewsService.me().getIndexNews();
		String str = JSON.toJSONStringWithDateFormat(list, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
		renderText(str);
		
	}
	
	public void getNewsById(){
		int id = getParaToInt("id");
		Record news = NewsService.me().getNewsById(id);
		
		//news.getStr(column)
/*		String content =  news.getStr("content");
		String con = subStringHTML(content, 20, "");
		news.set("con", con);*/
		setAttr("news", news);
		render("sel.html");
	}
	
	/** 按字节长度截取字符串(支持截取带HTML代码样式的字符串)
	* @param param 将要截取的字符串参数
	* @param length 截取的字节长度
	* @param end 字符串末尾补上的字符串
	* @return 返回截取后的字符串
	*/
	 public String subStringHTML(String param,int length,String end) {
         StringBuffer result = new StringBuffer();
         int n = 0;
         char temp;
         boolean isCode = false; //是不是HTML代码
         boolean isHTML = false; //是不是HTML特殊字符,如 
         for (int i = 0; i < param.length(); i++) {
           temp = param.charAt(i);
           if (temp == '<') {
             isCode = true;
           }
           else if (temp == '&') {
             isHTML = true;
           }
           else if (temp == '>' && isCode) {
             n = n - 1;
             isCode = false;
           }
           else if (temp == ';' && isHTML) {
             isHTML = false;
           }
           if (!isCode && !isHTML) {
             n = n + 1;
             //UNICODE码字符占两个字节
             if ( (temp + "").getBytes().length > 1) {
               n = n + 1;
             }
           }
           result.append(temp);
           if (n >= length) {
             break;
           }
         }
         result.append(end);
         //取出截取字符串中的HTML标记
         String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
         //去掉不需要结素标记的HTML标记
         temp_result = temp_result.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>",
                                              "");
         //去掉成对的HTML标记
         temp_result=temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>","$2");
         //用正则表达式取出标记
         Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
         Matcher m = p.matcher(temp_result);
         List endHTML = new ArrayList();
         while (m.find()) {
           endHTML.add(m.group(1));
         }
         //补全不成对的HTML标记
         for (int i = endHTML.size() - 1; i >= 0; i--) {
           result.append("</");
           result.append(endHTML.get(i));
           result.append(">");
         }
         return result.toString();
       }
	
}
