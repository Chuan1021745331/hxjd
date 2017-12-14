package com.base.utils;

import java.io.IOException;
import java.util.Properties;


public class ConfigParamsUtil {
	private static Properties properties;
	
	public static final String CONFIG_NAME = "config.properties";
	
	public static final String IMGPATH = "imgPath";
	public static final String IMGURL = "imgUrl";
	public static final String PROJECTPATH = "projectPath";
	
	static{
		try {
			properties = new Properties();
			properties.load(ConfigParamsUtil.class.getClassLoader().getResourceAsStream(CONFIG_NAME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ConfigParamsUtil() {
	}

	private static ConfigParamsUtil config = null;

	public static synchronized ConfigParamsUtil getInstance() {
		if (config == null) {
			config = new ConfigParamsUtil();
		}
		return config;
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
