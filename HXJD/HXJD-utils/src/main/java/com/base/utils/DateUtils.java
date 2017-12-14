package com.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

	static SimpleDateFormat pushData = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat dateSdf = new SimpleDateFormat("yyyyMMdd");

	public static String PushData() {
		return pushData.format(new Date());
	}

	public static String now() {
		return sdf.format(new Date());
	}

	public static String DateString() {
		return dateSdf.format(new Date());
	}

	public static String format(Date date) {
		if (null == date) {
			return null;
		}
		return sdf.format(date);
	}

}
