package com.base.utils;

public class PositionUtil {
	private static double pi = 3.1415926535897932384626D;
	private static double a = 6378245.0D;
	private static double ee = 0.00669342162296594323D;

	/**
	 * 地球坐标系转火星坐标系
	 * 
	 * @param wgLat
	 * @param wgLon
	 * @return
	 */
	public static double[] gps84_To_Gcj02(double wgLat, double wgLon) {
		double mgLat = 0;
		double mgLon = 0;
		if (outOfChina(wgLat, wgLon)) {
			mgLat = wgLat;
			mgLon = wgLon;
		} else {
			double dLat = transformLat(wgLon - 105.0, wgLat - 35.0);
			double dLon = transformLon(wgLon - 105.0, wgLat - 35.0);

			double redLat = wgLat / 180.0 * pi;
			double magic = Math.sin(redLat);
			magic = 1 - ee * magic * magic;
			double sqrtMagic = Math.sqrt(magic);
			dLat = (dLat * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
			dLon = (dLon * 180.0) / (a / sqrtMagic * Math.cos(redLat) * pi);
			mgLat = wgLat + dLat;
			mgLon = wgLon + dLon;
		}
		double[] point = { mgLat, mgLon };
		return point;
	}

	/**
	 * 火星坐标系转百度地图坐标系
	 * 
	 * @param gg_lat
	 * @param gg_lon
	 * @return
	 */
	public static double[] gcj02_To_Bd09(double gg_lat, double gg_lon) {
		double[] tr = gps84_To_Gcj02(gg_lat, gg_lon);
		gg_lat = tr[0];
		gg_lon = tr[1];
		double x = gg_lon, y = gg_lat;
		double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * pi);
		double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * pi);
		double bd_lon = z * Math.cos(theta) + 0.0065;
		double bd_lat = z * Math.sin(theta) + 0.006;
		double[] point = { bd_lat, bd_lon };
		return point;
	}

	private static boolean outOfChina(double lat, double lon) {
		if (lon < 72.004 || lon > 137.8347) {
			return true;
		}
		if (lat < 0.8293 || lat > 55.8271) {
			return true;
		}
		return false;
	}

	private static double transformLat(double x, double y) {
		double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y + 0.2 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
		ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
		return ret;
	}

	private static double transformLon(double x, double y) {
		double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1 * Math.sqrt(Math.abs(x));
		ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
		ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
		ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0 * pi)) * 2.0 / 3.0;
		return ret;
	}

}
