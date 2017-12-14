package com.base.admin;

import com.base.core.JBase;
import com.base.core.JBaseConfig;
import com.base.core.db.DbDialectFactory;
import com.base.utils.StringUtils;
import com.jfinal.config.Plugins;
import com.jfinal.kit.PropKit;
import com.jfinal.log.Log;
import com.jfinal.template.Engine;

public class Config extends JBaseConfig {
	@SuppressWarnings("unused")
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
		// TODO Auto-generated method stub
		
	}


//	@Override
//	public void configEngine(Engine arg0) {
//		// TODO Auto-generated method stub
//		
//	}

}
