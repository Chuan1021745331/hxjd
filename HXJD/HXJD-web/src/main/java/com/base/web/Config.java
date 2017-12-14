package com.base.web;

import com.base.core.JBase;
import com.base.core.JBaseConfig;
import com.base.core.db.DbDialectFactory;
import com.base.im.client.IMClientStarter2;
import com.base.im.server.IMServerStarter;
import com.base.utils.StringUtils;
import com.jfinal.config.Plugins;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
//import com.jfinal.template.Engine;
import com.jfinal.template.Engine;

public class Config extends JBaseConfig {
	private Log log = Log.getLog(Config.class);

	@Override
	public void onJfinalStartBefore() {
		dbDialectConfig();
	}

	@Override
	public void onJfinalStartAfter() {
		searcherConfig();
	}

	private void searcherConfig() {
		if (!JBase.isInstalled()) {
			return;
		}
	}

	private void dbDialectConfig() {
		String dialect = PropKit.get("jpress_db_dialect");
		if (StringUtils.isNotBlank(dialect)) {
			DbDialectFactory.use(dialect);
		}
	}

	@Override
	public void configEngine(Engine me) {
		// TODO Auto-generated method stub

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
		log.info("连接C平台。。。");
		try {
			plugins.add(new IMClientStarter2());
			log.info("C平台连接成功。。。");
		} catch (Exception e) {
			log.debug("C平台连接失败。。。");
		}
	}

	// @Override
	// public void configEngine(Engine arg0) {
	// // TODO Auto-generated method stub
	//
	// }
}
