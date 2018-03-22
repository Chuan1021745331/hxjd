package com.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.base.model.JNews;
import com.base.model.JNewstype;
import com.base.query.NewsQuery;
import com.base.query.NewsTypeQuery;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Db;
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

	/**
	 * 根据信息类型获取信息数量
	 * @param type
	 * @return
	 */
	public long findConuntByType(int type){
		return NewsQuery.me().findCountByTypeId(type);
	}

	/**
	 * 根据信息类型分页查询
	 * @param page
	 * @param limit
	 * @param type
	 * @param count
	 * @return
	 */
	public List<Record> findListNewsByType(Integer page, Integer limit, int type, long count){
		List<Record> list = new ArrayList<Record>();
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
			list = NewsQuery.me().findListNewsByType(page, limit, type);
		}
		return list;
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

	/**
	 * 查询发布时间小于time的limit条数据
	 * @param time
	 * @param limit
	 * @return
	 */
	public List<Record> getLtTimeNews(String time,int limit){
		return NewsQuery.me().getLtTimeNews(time,limit);
	}
	
	public Record getNewsById(int id){
		return NewsQuery.me().getById(id);
	}

	public JNews getJNewsById(int id){
		return NewsQuery.me().getNewsById(id);
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
		
	public List<JNewstype> getNewsType(){
		return NewsTypeQuery.me().getAll();
	}

	/**
	 * 根据类型获取信息类型
	 * @param type
	 * @return
	 */
	public List<JNewstype> getNewsTypeByType(int type){
		return  NewsTypeQuery.me().getByType(type);
	}


	public List<Record> getNewsByPageTag(Integer page, Integer tag){
		return NewsQuery.me().getNewsByPageTag((page-1)*10, tag);
	}
	
	public List<Record> getNewsAll(){
		return NewsQuery.me().getAll();
	}
	
	public List<Record> getByTag(Integer id){
		return NewsQuery.me().getByTag(id);
	}
	public JNewstype saveNewsType(String name, Integer type){		
		return NewsTypeQuery.me().saveByName(name, type);
	}
	

	/**
	 * 将新闻集合和维保记录集合进行排序,并不超过10条记录
	 * @param newlist
	 * @param trlist
	 * @param size
	 * @return
	 */
	public List<Record> getSortResult(List<Record> newlist,List<Record> trlist,Integer size){
		List<Record> results = new ArrayList<>();

		while(true){
			if(results.size()>=size){
				break;
			}
			if(newlist.size()==0){
				if(trlist.size()>0){
					results.add(trlist.get(0));
					trlist.remove(0);
					continue;
				}else{
					break;
				}
			}
			if(trlist.size()==0){
				if(newlist.size()>0){
					results.add(newlist.get(0));
					newlist.remove(0);
					continue;
				}else{
					break;
				}
			}
			Date postTime = newlist.get(0).getDate("postTime");
			Date createtime = trlist.get(0).getDate("createtime");
			//选择时间大的
			if(postTime.compareTo(createtime)==1){
				results.add(newlist.get(0));
				newlist.remove(0);
			}else{
				results.add(trlist.get(0));
				trlist.remove(0);
			}
		}
		return results;
	}

	/**
	 * 取到选取的最后新闻
	 * @param recordList
	 * @return
	 */
	public Date getLastChooseNews(List<Record> recordList){
		for(int i=recordList.size()-1;i>=0;i--){
			Record record = recordList.get(i);
			Date postTime = record.getDate("postTime");
			if(postTime!=null){
				return postTime;
			}
		}
		return null;
	}

	/**
	 * 取到选取的最后维保记录
	 * @param recordList
	 * @return
	 */
	public Date getLastChooseTbmrepair(List<Record> recordList){
		for(int i=recordList.size()-1;i>=0;i--){
			Record record = recordList.get(i);
			Date createtime = record.getDate("createtime");
			if(createtime!=null){
				return createtime;
			}
		}
		return null;
	}


}
