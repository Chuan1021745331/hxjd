package com.base.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 坐标距离
 * 
 * @author z
 *
 */
public class CoordinateDistanceUtils {
	public static double getDistance(Map<String, String> a, Map<String, String> b) {
		double lat1 = (Math.PI / 180) * Double.parseDouble(a.get("y"));
		double lat2 = (Math.PI / 180) * Double.parseDouble(b.get("y"));

		double lon1 = (Math.PI / 180) * Double.parseDouble(a.get("x"));
		double lon2 = (Math.PI / 180) * Double.parseDouble(b.get("x"));

		// 地球半径
		double R = 6378137;

		// 两点间距离 km，如果想要米的话，结果*1000就可以了
		double d = Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R;

		return d * 1000;
	}

	public static void main(String[] args) {
		Map<String, String> a = new HashMap<String, String>();
		a.put("x", "106.5216028690");
		a.put("y", "29.5549144303");
		Map<String, String> b = new HashMap<String, String>();
		b.put("x", "106.5200686455");
		b.put("y", "29.5507892327");
		double c = CoordinateDistanceUtils.getDistance(a, b);
		System.out.println(c);
	}
}
