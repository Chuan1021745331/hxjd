package com.base.code.generator;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: Generator.java   
 * @包名: com.base.code.generator   
 * @描述: 代码生成器  
 * @所属: 华夏九鼎     
 * @日期: 2018年1月29日 上午9:34:52   
 * @版本: V1.0 
 * @创建人：Administrator 
 * @修改人：Administrator
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * @注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class Generator {

	public static void main(String[] args) {
		
		String modelPackage = "com.base";
		
		String dbHost = "192.168.0.99";
		String dbName = "hxjd";
		String dbUser = "root";
		String dbPassword = "123456";
		
		new JGenerator(modelPackage, dbHost, dbName, dbUser, dbPassword).doGenerate();

	}

}
