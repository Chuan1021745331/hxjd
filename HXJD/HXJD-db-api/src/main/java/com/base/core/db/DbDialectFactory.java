package com.base.core.db;

public class DbDialectFactory {

	static DbDialect dialect;

	public static DbDialect getDbDialect() {

		return dialect;
	}

	public static void use(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			dialect = (DbDialect) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
