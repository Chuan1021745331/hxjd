package com.base.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class SystemUtils {
	private final static Logger logger= LoggerFactory.getLogger(SystemUtils.class);

	public static Properties getSystem(){
		return System.getProperties();
	}
	public static void main(String[] args) {
		Properties sysProperty = System.getProperties(); // 系统属性
		logger.info("Java的运行环境版本：" + sysProperty.getProperty("java.version"));
		logger.info("Java的运行环境供应商：" + sysProperty.getProperty("java.vendor"));
		logger.info("Java供应商的URL：" + sysProperty.getProperty("java.vendor.url"));
		logger.info("Java的安装路径：" + sysProperty.getProperty("java.home"));
		logger.info("Java的虚拟机规范版本：" + sysProperty.getProperty("java.vm.specification.version"));
		logger.info("Java的虚拟机规范供应商：" + sysProperty.getProperty("java.vm.specification.vendor"));
		logger.info("Java的虚拟机规范名称：" + sysProperty.getProperty("java.vm.specification.name"));
		logger.info("Java的虚拟机实现版本：" + sysProperty.getProperty("java.vm.version"));
		logger.info("Java的虚拟机实现供应商：" + sysProperty.getProperty("java.vm.vendor"));
		logger.info("Java的虚拟机实现名称：" + sysProperty.getProperty("java.vm.name"));
		logger.info("Java运行时环境规范版本：" + sysProperty.getProperty("java.specification.version"));
		logger.info("Java运行时环境规范供应商：" + sysProperty.getProperty("java.specification.vender"));
		logger.info("Java运行时环境规范名称：" + sysProperty.getProperty("java.specification.name"));
		logger.info("Java的类格式版本号：" + sysProperty.getProperty("java.class.version"));
		logger.info("Java的类路径：" + sysProperty.getProperty("java.class.path"));
		logger.info("加载库时搜索的路径列表：" + sysProperty.getProperty("java.library.path"));
		logger.info("默认的临时文件路径：" + sysProperty.getProperty("java.io.tmpdir"));
		logger.info("一个或多个扩展目录的路径：" + sysProperty.getProperty("java.ext.dirs"));
		logger.info("操作系统的名称：" + sysProperty.getProperty("os.name"));
		logger.info("操作系统的构架：" + sysProperty.getProperty("os.arch"));
		logger.info("操作系统的版本：" + sysProperty.getProperty("os.version"));
		logger.info("文件分隔符：" + sysProperty.getProperty("file.separator")); 	// 在unix系统中是＂／＂
		logger.info("路径分隔符：" + sysProperty.getProperty("path.separator")); 	// 在unix系统中是＂:＂
		logger.info("行分隔符：" + sysProperty.getProperty("line.separator")); 	// 在unix系统中是＂/n＂
		logger.info("用户的账户名称：" + sysProperty.getProperty("user.name"));
		logger.info("用户的主目录：" + sysProperty.getProperty("user.home"));
		logger.info("用户的当前工作目录：" + sysProperty.getProperty("user.dir"));
	}
}
