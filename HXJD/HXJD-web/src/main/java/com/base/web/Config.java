package com.base.web;

import com.base.core.JBase;
import com.base.core.JBaseConfig;
import com.base.core.db.DbDialectFactory;
import com.base.im.server.IMServerStarter;
import com.base.interceptor.WebInterceptor;
import com.base.utils.StringUtils;
import com.jfinal.config.Interceptors;
import com.jfinal.config.Plugins;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
//import com.jfinal.template.Engine;
import com.jfinal.template.Engine;
/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: Config.java   
 * @包名: com.base.admin   
 * @描述: 启动文件，继承与基础启动文件，可根据项目需求配置   
 * @所属: 华夏九鼎     
 * @日期: 2018年1月9日 上午10:49:04   
 * @版本: V1.0 
 * @创建人：Administrator 
 * @修改人：Administrator
 * @版权: 2018 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class Config extends JBaseConfig {
	private Log log = Log.getLog(Config.class);
	/**
	 * 启动前加载
	 */
	@Override
	public void onJfinalStartBefore() {
		dbDialectConfig();
	}
	/**
	 * 启动后加载
	 */
	@Override
	public void onJfinalStartAfter() {
		searcherConfig();
	}

	private void searcherConfig() {
		if (!JBase.isInstalled()) {
			return;
		}
	}
	/**
	 * 数据源
	 * dbDialectConfig(这里用一句话描述这个方法的作用)  
	 * (这里描述这个方法适用条件 – 可选)     
	 *void  
	 * @exception   
	 * @since  1.0.0
	 */
	private void dbDialectConfig() {
		String dialect = PropKit.get("hxjd_db_dialect");
		if (StringUtils.isNotBlank(dialect)) {
			DbDialectFactory.use(dialect);
		}
	}

	@Override
	public void configEngine(Engine me) {
	}

	@Override
	public void onJfinalLoadElement(Plugins plugins) {
		//加载IM服务
		log.info("加载IM服务。。。");
		try {
			plugins.add(new IMServerStarter());
			log.info("IM服务加载成功。。。");
		} catch (Exception e) {
			log.debug("IM服务加载失败。。。");
		}
	}
	/**
	 * 拦截器
	 */
	@Override
	public void configInterceptor4Module(Interceptors interceptors) {
		/**
		 * admin页面统一过滤器
		 */
		interceptors.add(new WebInterceptor());
	}
}
