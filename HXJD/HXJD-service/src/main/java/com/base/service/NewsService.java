package com.base.service;

import java.util.ArrayList;
import java.util.List;

import com.base.model.JNews;
import com.base.query.NewsQuery;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: NewsService.java   
 * @包名: com.base.service   
 * @描述: admin后台新闻Service   
 * @所属: 华夏九鼎     
 * @日期: 2018年1月19日 下午4:47:51   
 * @版本: V1.0 
 * @创建人：kevin 
 * @修改人：kevin
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class NewsService {
	private static final NewsService SERVICE = new NewsService();
	public static NewsService me() {
		return SERVICE;
	}
	
	public long findConunt(String where){
		return NewsQuery.me().findCount(where);
	}
	
	public List<Record> findListNews(Integer page, Integer limit, String where, long count){
		List<Record> list = new ArrayList<Record>();
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
			list = NewsQuery.me().findListNews(page, limit, where);
		}
		return list;
	}
	
	public List<Record> getIndexNews(){
		return NewsQuery.me().getIndexNews();
	}
	
	@Before(Tx.class)
	public boolean addSave(JNews news){
		return news.save();
	}
	
	@Before(Tx.class)
	public boolean delById(Integer id){
		return NewsQuery.me().deleteById(id);
	}
	public Record getById(Integer id){
		return NewsQuery.me().getById(id);
	}
	
	@Before(Tx.class)
	public boolean editSave(JNews news){
		return news.saveOrUpdate(); 
	}
		
}
