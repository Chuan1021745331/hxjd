package com.base.cron;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.jfinal.log.Log;

/**
 * 任务调度管理类
 */
public class QuartzManager {
	private static Log logger = Log.getLog(QuartzManager.class);
	
	public static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();  
	private static Scheduler  scheduler ;
	/**
	 * @param scheduler
	 *            the scheduler to set
	 */
	@SuppressWarnings("static-access")
	public void setScheduler(Scheduler  scheduler) {
		this.scheduler = scheduler;
	}

	/**
	 * 初始化任务调度
	 * 
	 * @author Joyce.Luo
	 * @date 2015-3-31 下午03:48:55
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	@SuppressWarnings("rawtypes")
	public void initJob(QuartzJob job, Class cls) {
		logger.info("初始化任务调度");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
			CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
			if (null == trigger) {
				addQuartzJob(job, trigger, cls);
			}
		} catch (Exception e) {
			logger.error("初始化任务调度异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 向任务调度中添加定时任务
	 * 
	 * @param job
	 *            定时任务信息
	 * @param trigger
	 *            定时调度触发器
	 * @author Joyce.Luo
	 * @date 2015-3-31 下午04:04:58
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void addQuartzJob(QuartzJob job, CronTrigger trigger, Class cls) {
		logger.info("向任务调度中添加定时任务");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			JobDetail jobDetail = JobBuilder.newJob(cls).withIdentity(job.getJobName(), job.getJobGroup()).build();
			jobDetail.getJobDataMap().put(job.getJobName(), job);
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = TriggerBuilder.newTrigger().withIdentity(job.getJobName(), job.getJobGroup())
					.withSchedule(scheduleBuilder).build();
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			logger.error("向任务调度中添加定时任务异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 立即运行定时任务
	 * 
	 * @param job
	 *            定时任务信息
	 * @author Joyce.Luo
	 * @date 2015-4-20 下午02:08:41
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	public void runJob(QuartzJob job) {
		logger.info("立即运行任务调度中的定时任务");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			if (null == job) {
				logger.info("定时任务信息为空，无法立即运行");
				return;
			}
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			if (null == jobKey) {
				logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予立即运行！");
				return;
			}
			System.out.println(jobKey);
			scheduler.triggerJob(jobKey);
		} catch (Exception e) {
			logger.error("立即运行任务调度中的定时任务异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 修改任务调度中的定时任务
	 * 
	 * @param job
	 *            定时任务信息
	 * @param triggerKey
	 *            定时调度触发键
	 * @param trigger
	 *            定时调度触发器
	 * @author Joyce.Luo
	 * @date 2015-3-31 下午04:16:54
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	public void updateQuartzJob(QuartzJob job, TriggerKey triggerKey, CronTrigger trigger) {
		logger.info("修改任务调度中的定时任务");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			if (null == job || null == triggerKey || null == trigger) {
				logger.info("修改调度任务参数不正常！");
				return;
			}
			logger.info("原始任务表达式:" + trigger.getCronExpression() + "，现在任务表达式:" + job.getCronExpression());
			if (trigger.getCronExpression().equals(job.getCronExpression())) {
				logger.info("任务调度表达式一致，不予进行修改！");
				return;
			}
			logger.info("任务调度表达式不一致，进行修改");
			CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(job.getCronExpression());
			trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();
			scheduler.rescheduleJob(triggerKey, trigger);
		} catch (Exception e) {
			logger.error("修改任务调度中的定时任务异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 暂停任务调度中的定时任务
	 * 
	 * @param job
	 *            定时任务信息
	 * @author Joyce.Luo
	 * @date 2015-4-20 下午02:22:53
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	public void pauseJob(QuartzJob job) {
		logger.info("暂停任务调度中的定时任务");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			if (null == job) {
				logger.info("暂停调度任务参数不正常！");
				return;
			}
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			if (null == jobKey) {
				logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予进行暂停！");
				return;
			}
			scheduler.pauseJob(jobKey);
		} catch (Exception e) {
			logger.error("暂停任务调度中的定时任务异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 恢复任务调度中的定时任务
	 * 
	 * @param job
	 *            定时任务信息
	 * @author Joyce.Luo
	 * @date 2015-4-20 下午02:26:08
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	public void resumeJob(QuartzJob job) {
		logger.info("恢复任务调度中的定时任务");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			if (null == job) {
				logger.info("恢复调度任务参数不正常！");
				return;
			}
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			if (null == jobKey) {
				logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予进行恢复！");
				return;
			}
			scheduler.resumeJob(jobKey);
		} catch (Exception e) {
			logger.error("恢复任务调度中的定时任务异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 删除任务调度中的定时任务
	 * 
	 * @param job
	 *            定时任务信息
	 * @author Joyce.Luo
	 * @date 2015-3-31 下午04:30:03
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	public void deleteJob(QuartzJob job) {
		logger.info("删除任务调度中的定时任务");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			if (null == job) {
				logger.info("删除调度任务参数不正常！");
				return;
			}
			JobKey jobKey = JobKey.jobKey(job.getJobName(), job.getJobGroup());
			if (null == jobKey) {
				logger.info("任务调度中不存在[" + job.getJobName() + "]定时任务，不予进行删除！");
				return;
			}
			scheduler.deleteJob(jobKey);
		} catch (Exception e) {
			logger.error("删除任务调度中的定时任务异常！" + e.getMessage(), e);
		}
	}

	/**
	 * 删除任务调度定时器
	 * 
	 * @param triggerKey
	 * @author Joyce.Luo
	 * @date 2015-3-31 下午04:35:33
	 * @version V3.0
	 * @since Tomcat6.0,Jdk1.6
	 * @copyright Copyright (c) 2015
	 */
	public void deleteJob(TriggerKey triggerKey) {
		logger.info("删除任务调度定时器");
		try {
			scheduler = gSchedulerFactory.getScheduler(); 
			if (null == triggerKey) {
				logger.info("停止任务定时器参数不正常，不予进行停止！");
				return;
			}
			logger.info("停止任务定时器");
			scheduler.pauseTrigger(triggerKey);
			scheduler.unscheduleJob(triggerKey);
		} catch (Exception e) {
			logger.info("删除任务调度定时器异常！" + e.getMessage(), e);
		}
	}
}