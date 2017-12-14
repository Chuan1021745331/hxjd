package com.base.web.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

import com.jfinal.kit.JsonKit;

public class DwrService {
	public String pushMessage(String message) {
		if(Integer.parseInt(message)==1){
			Map<String, String> map = new HashMap<String, String>();
			long totalMemory = Runtime.getRuntime().totalMemory();		//虚拟机内存总量
			long freeMemory = Runtime.getRuntime().freeMemory();		//虚拟机空闲内存量
			BigDecimal t = new BigDecimal(totalMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),3,RoundingMode.HALF_DOWN);
			BigDecimal f = new BigDecimal(freeMemory).divide(new BigDecimal(1024)).divide(new BigDecimal(1024),3,RoundingMode.HALF_DOWN);
			BigDecimal c = new BigDecimal(1).subtract(f.divide(t,3,RoundingMode.HALF_DOWN)).multiply(new BigDecimal(100));
			
			map.put("t", t.doubleValue()+"");
			map.put("f", f.doubleValue()+"");
			map.put("c", c.doubleValue()+"");
			
			
			
			return JsonKit.toJson(map);
		}
		return message;
	}
}