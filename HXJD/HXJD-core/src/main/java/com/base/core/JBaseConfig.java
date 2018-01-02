package com.base.core;

import java.io.File;
import java.util.List;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import com.base.cache.JCachePlugin;
import com.base.constants.Consts;
import com.base.core.db.DbDialectFactory;
import com.base.cron.QuartzJob;
import com.base.cron.QuartzJobFactory;
import com.base.cron.QuartzManager;
import com.base.interceptor.AdminInterceptor;
import com.base.model.JJob;
import com.base.model.core.JModelMapping;
import com.base.model.core.Table;
import com.base.router.RouterMapping;
import com.base.service.JobQuery;
import com.base.service.OptionQuery;
import com.base.utils.ClassScaner;
import com.base.utils.StringUtils;
import com.cybermkd.mongo.plugin.MongoJFinalPlugin;
import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.PathKit;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.druid.DruidStatViewHandler;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;

import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.ConfigurationFactory;
import net.sf.ehcache.config.DiskStoreConfiguration;

public abstract class JBaseConfig extends JFinalConfig {
	private Log log = Log.getLog(JBaseConfig.class);

	@Override
	public void configConstant(Constants constants) {
		PropKit.use("jbase.properties");
		onJfinalStartBefore();

		constants.setEncoding(Consts.CHARTSET_UTF8);
		constants.setMaxPostSize(1024 * 1024 * 2000);
		constants.setDevMode(false);
		constants.setViewType(ViewType.FREE_MARKER);
		constants.setBaseUploadPath("attachment");

		constants.setError403View("/errorPage/page_403.html");
		constants.setError404View("/errorPage/page_404.html");
		constants.setError500View("/errorPage/page_500.html");
	}

	// 路由设置
	@SuppressWarnings("unchecked")
	@Override
	public void configRoute(Routes routes) {
		List<Class<Controller>> controllerClassList = ClassScaner.scanSubClass(Controller.class);
		if (controllerClassList != null) {
			for (Class<?> clazz : controllerClassList) {
				RouterMapping urlMapping = clazz.getAnnotation(RouterMapping.class);
				if (null != urlMapping && StringUtils.isNotBlank(urlMapping.url())) {
					if (StrKit.notBlank(urlMapping.viewPath())) {
						routes.add(urlMapping.url(), (Class<? extends Controller>) clazz,
								"/WEB-INF" + urlMapping.viewPath());
					} else {
						routes.add(urlMapping.url(), (Class<? extends Controller>) clazz);
					}
				}
			}
		}
	}

	@Override
	public void configPlugin(Plugins plugins) {
		plugins.add(createEhCachePlugin());

		// 加载缓存服务
		JCachePlugin leCachePlugin = new JCachePlugin();
		plugins.add(leCachePlugin);

		DruidPlugin druidPlugin = createDruidPlugin();
		plugins.add(druidPlugin);

		ActiveRecordPlugin activeRecordPlugin = createRecordPlugin(druidPlugin);
		activeRecordPlugin.setCache(leCachePlugin.getCache());
		activeRecordPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
		plugins.add(activeRecordPlugin);
		onJfinalLoadElement(plugins);
		
		//加载MongoDB
		MongoJFinalPlugin jFinalPlugin = new MongoJFinalPlugin();
		jFinalPlugin.add("127.0.0.1", 27017);
		jFinalPlugin.setDatabase("tbm");
		jFinalPlugin.setDebug(false);
		
		plugins.add(jFinalPlugin);
	}

	/**
	 * 缓存配置 createEhCachePlugin(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @return EhCachePlugin
	 * @exception @since
	 *                1.0.0
	 */
	private EhCachePlugin createEhCachePlugin() {
		String ehcacheDiskStorePath = PathKit.getRootClassPath();
		File pathFile = new File(ehcacheDiskStorePath, ".ehcache");

		Configuration cfg = ConfigurationFactory.parseConfiguration();
		cfg.addDiskStore(new DiskStoreConfiguration().path(pathFile.getAbsolutePath()));
		return new EhCachePlugin(cfg);
	}

	/**
	 * 数据库配置 createDruidPlugin(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @return DruidPlugin
	 * @exception @since
	 *                1.0.0
	 */
	private DruidPlugin createDruidPlugin() {
		Prop dbProp = PropKit.use("db.properties");
		String db_host = dbProp.get("db_host").trim();

		String db_host_port = dbProp.get("db_host_port");
		db_host_port = StringUtils.isNotBlank(db_host_port) ? db_host_port.trim() : "3306";

		String db_name = dbProp.get("db_name").trim();
		String db_user = dbProp.get("db_user").trim();
		String db_password = dbProp.get("db_password").trim();

		return DbDialectFactory.getDbDialect().createDuidPlugin(db_host, db_host_port, db_name, db_user, db_password);
	}

	/**
	 * 对象配置 createRecordPlugin(这里用一句话描述这个方法的作用) (这里描述这个方法适用条件 – 可选)
	 * 
	 * @param dsp
	 * @return ActiveRecordPlugin
	 * @exception @since
	 *                1.0.0
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private ActiveRecordPlugin createRecordPlugin(IDataSourceProvider dsp) {
		ActiveRecordPlugin arPlugin = new ActiveRecordPlugin(dsp);
		List<Class<Model>> modelClassList = ClassScaner.scanSubClass(Model.class);
		if (modelClassList != null) {
			String tablePrefix = PropKit.use("db.properties").get("db_tablePrefix");
			tablePrefix = (StrKit.isBlank(tablePrefix)) ? "" : (tablePrefix.trim());
			for (Class<?> clazz : modelClassList) {
				Table tb = clazz.getAnnotation(Table.class);
				if (tb == null) {
					continue;
				}
				String tname = tablePrefix + tb.tableName();
				if (StringUtils.isNotBlank(tb.primaryKey())) {
					arPlugin.addMapping(tname, tb.primaryKey(), (Class<? extends Model<?>>) clazz);
				} else {
					arPlugin.addMapping(tname, (Class<? extends Model<?>>) clazz);
				}

				JModelMapping.me().mapping(clazz.getSimpleName().toLowerCase(), tname);
			}
		}

		arPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
		return arPlugin;
	}

	@Override
	public void configInterceptor(Interceptors interceptors) {
		/**
		 * admin页面统一过滤器
		 */
		interceptors.add(new AdminInterceptor());
	}

	@Override
	public void configHandler(Handlers handlers) {
		handlers.add(new JHandler());// 添加项目contextPath,以便在页面直接获取该值
										// ${base?if_exists}
		DruidStatViewHandler druidViewHandler = new DruidStatViewHandler("/admin/druid");
		handlers.add(druidViewHandler);
	}

	@Override
	public void afterJFinalStart() {
		if (JBase.isInstalled()) {
			JBase.loadFinished();
		}

		JBase.renderImmediately();
		log.info("载入计划任务。。。");
		try {
			List<JJob> jobs = JobQuery.me().findAll();
			for (JJob j : jobs) {
				if (j.getJobStatus() == 1) {
					QuartzJob qj = new QuartzJob();
					qj.setJobId(Integer.parseInt(j.getId() + ""));
					qj.setJobName(j.getJobName());
					qj.setJobGroup(j.getJobGroup());
					qj.setJobStatus(j.getJobStatus());
					qj.setCronExpression(j.getCronExp());
					QuartzManager qm = new QuartzManager();
					qm.initJob(qj, QuartzJobFactory.class);
					SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
					Scheduler sched;
					try {
						sched = gSchedulerFactory.getScheduler();
						sched.start();
						log.info("计划任务载入成功。。。");
					} catch (SchedulerException e) {
						log.debug("计划任务载入失败。。。");
						e.printStackTrace();
					}
				}
			}
			log.info("共载入计划任务【" + jobs.size() + "】个");
		} catch (Exception e) {
			log.debug("计划任务载入失败。。。");
		}

		log.info("载入配置缓存。。。");
		try {
			OptionQuery.me().optionCacheAll();
			log.info("配置缓存载入成功。。。");
		} catch (Exception e) {
			log.debug("配置缓存载入失败。。。");
		}

		onJfinalStartAfter();
	}

	public abstract void onJfinalStartAfter();

	public abstract void onJfinalStartBefore();

	public abstract void onJfinalLoadElement(Plugins plugins);
}
