package com.base.code.generator;

import java.util.List;

import javax.sql.DataSource;

import com.jfinal.kit.PathKit;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.activerecord.generator.TableMeta;
import com.jfinal.plugin.druid.DruidPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: JGenerator.java   
 * @包名: com.base.code.generator   
 * @描述: 类-数据库连接
 * @所属: 华夏九鼎     
 * @日期: 2018年1月29日 上午9:37:13   
 * @版本: V1.0 
 * @创建人：Administrator 
 * @修改人：Administrator
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * @注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class JGenerator {
	private final static Logger logger= LoggerFactory.getLogger(JGenerator.class);

	private final String basePackage;
	private final String dbHost;
	private final String dbName;
	private final String dbUser;
	private final String dbPassword;
	
	
	public JGenerator(String basePackage, String dbHost, String dbName,
			String dbUser, String dbPassword) {
		
		this.basePackage = basePackage;
		this.dbHost = dbHost;
		this.dbName = dbName;
		this.dbUser = dbUser;
		this.dbPassword = dbPassword;
	}



	public void doGenerate(){

		String modelPackage = basePackage+".model";
		String baseModelPackage = basePackage+".model.base";
//		String adminControllerPackage = basePackage+".controller.admin";
		
		String modelDir = PathKit.getWebRootPath() + "/src/main/java/"+modelPackage.replace(".", "/");
		String baseModelDir = PathKit.getWebRootPath() + "/src/main/java/"+baseModelPackage.replace(".", "/");
//		String adminControllerDir = PathKit.getWebRootPath() + "/src/main/java/"+adminControllerPackage.replace(".", "/");
		
		logger.info("start generate...");
		logger.info("Generate dir:"+modelDir);
		
		
		List<TableMeta> tableMetaList = new MetaBuilder(getDataSource()).build();
		
		new JBaseModelGenerator(baseModelPackage, baseModelDir).generate(tableMetaList);
		new JModelGenerator(modelPackage, baseModelPackage, modelDir).generate(tableMetaList);
//		new JControllerGenerator(adminControllerPackage, baseModelPackage, adminControllerDir).generate(tableMetaList);
		
		logger.info("Generate finished !!!");
		
	}
	
	
	
	public DataSource getDataSource() {

		String jdbcUrl = "jdbc:mysql://" + dbHost + "/" + dbName;
		
		DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, dbUser,dbPassword);
		druidPlugin.start();
		return druidPlugin.getDataSource();
	}

}
