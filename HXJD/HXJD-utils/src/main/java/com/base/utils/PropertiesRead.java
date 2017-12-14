package com.base.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesRead {
	static String profilepath = "socket.properties";
	
	private static Properties properties = new Properties();
	static{
		try {
			properties.load(PropertiesRead.class.getClassLoader().getResourceAsStream(profilepath));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			System.exit(-1);
		}
	}
	
	public static String getKeyValue(String key){
		return properties.getProperty(key);
	}
	
	public static String readValue(String filePath,String key){
		Properties props = new Properties();
		try {
			InputStream in = new BufferedInputStream(new FileInputStream(filePath));
			props.load(in);
			String value = props.getProperty(key);
			return value;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void writeProperties(String keyName,String keyValue){
		try {
			properties.load(PropertiesRead.class.getClassLoader().getResourceAsStream(profilepath));
			String path = PropertiesRead.class.getResource(profilepath).getPath();  
            FileOutputStream fos = new FileOutputStream(path);
//			OutputStream fos = new FileOutputStream(profilepath);
			properties.setProperty(keyName, keyValue);
			properties.store(fos, null);
			fos.flush();
			fos.close();
		} catch (Exception e) {
			System.out.println("no");
		}
	}
}
