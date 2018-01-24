package com.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.MenuDto;
import com.jfinal.plugin.ehcache.CacheKit;

public class IndexService {
	private static final IndexService SERVICE = new IndexService();
	public static IndexService me() {
		return SERVICE;
	}
	
	public List<Map<String, Object>> getMenuss(List<Map<String, Object>> um, JUser user){
		List<MenuDto> m = CacheKit.get("menu", user.getUsername());
		um = new ArrayList<Map<String,Object>>();
		for (MenuDto m1:m) {
			JMenu m11 = m1.getM();
			Map<String, Object> mp = new HashMap<String, Object>(6);
			mp.put("id", m11.getId());
			mp.put("pid", m11.getParent());
			mp.put("title", m11.getName());
			mp.put("icon", m11.getIco());
			mp.put("url", m11.getUrl());
			mp.put("spread", false);
			if(m1.getN()>0){
				mp.put("children",getChild(m1.getChildren()));
			}
			
			um.add(mp);
		}
		CacheKit.put("menu", "userMenu_"+user.getUsername(),um);
		return um;
	}
	
	private List<Map<String, Object>> getChild(List<MenuDto> ms){
		List<Map<String, Object>> mts = new ArrayList<Map<String, Object>>();
		for (MenuDto m:ms) {
			Map<String, Object> mp = new HashMap<String, Object>(6);
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
	

}
