package com.base.service;

import java.util.ArrayList;
import java.util.List;

import com.base.model.JOption;
import com.base.query.OptionQuery;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

public class OptionService {
	private static final OptionService SERVICE = new OptionService();
	public static OptionService me() {
		return SERVICE;
	}
	
	public JOption findById(Integer id){
		return OptionQuery.me().findById(id);
	}
	
	@Before(Tx.class)
	public boolean addSave(JOption jo){
		return jo.save();
	}
	
	@Before(Tx.class)
	public boolean editSave(JOption jo, String optionX, String optionY){
		if("map_center".equals(jo.getOptionKey())){
			
			String oXY=optionX+"|"+optionY;
			jo.setOptionValue(oXY);
		}
		boolean a = jo.update();
		jo.removeCache(jo.getId());
		jo.removeCache(jo.getOptionKey());
		if(a){
			return true;
		}
		return false;
	}
	
	@Before(Tx.class)
	public boolean del(Integer id){
		JOption option = OptionQuery.me().findById(id);
		boolean a = option.delete();
		if(a){
			CacheKit.remove("option", id);
			return true;
		}
		return false;
	}
	
	public long findConunt(String where){
		return OptionQuery.me().findConunt(where);
	}
	
	public List<Record> findListOptions(Integer page, Integer limit, String where, long count){
		List<Record> list = new ArrayList<Record>();
		if(count!=0){
			page = (page>count/limit && count%limit==0)?page-1:page ;
			list = OptionQuery.me().findListOptions(page, limit,where);
		}
		return list;
	}
}
