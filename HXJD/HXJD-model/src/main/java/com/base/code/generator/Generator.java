package com.base.code.generator;

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
