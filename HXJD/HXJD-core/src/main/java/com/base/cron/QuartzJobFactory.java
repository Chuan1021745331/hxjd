package com.base.cron;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;

import com.base.service.JobQuery;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.jfinal.log.Log;


/**
 * 任务调度工厂类
 * 
 * @author Joyce.Luo
 * @date 2015-3-31 下午03:38:35
 * @version V3.0
 * @since Tomcat6.0,Jdk1.6
 * @copyright Copyright (c) 2015
 */
public class QuartzJobFactory implements Job {
	private static Log logger = Log.getLog(QuartzManager.class);

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 JobDetail data = context.getJobDetail();

		logger.info("定时任务开始执行，任务名称[" + data.getKey().getName() + "]");
		Date previousFireTime = context.getPreviousFireTime();
		if (null != previousFireTime) {
			logger.info("定时任务上次调度时间：" +  previousFireTime.toString());
		}
		logger.info("定时任务下次调度时间：" + context.getNextFireTime().toString());
		// 执行业务逻辑
		//获取类
		Class<?> clz = null;
		Object obj = null;
		try {
			clz = Class.forName(data.getKey().getGroup());
			obj = clz.newInstance();
		} catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
		try {
			Method method = clz.getMethod(data.getKey().getName());
			method.invoke(obj,null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}