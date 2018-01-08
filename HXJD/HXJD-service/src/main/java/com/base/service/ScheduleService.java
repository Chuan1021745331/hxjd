package com.base.service;

import org.quartz.CronTrigger;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.base.constants.MessageConstants;
import com.base.cron.QuartzJob;
import com.base.cron.QuartzJobFactory;
import com.base.cron.QuartzManager;
import com.base.model.JJob;
import com.base.query.JobQuery;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduleService {
    private final static Logger logger= LoggerFactory.getLogger(ScheduleService.class);

    public static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static Scheduler  scheduler ;
    QuartzManager quartzManager = new QuartzManager();
    QuartzJob qj = new QuartzJob();
    
	private static final ScheduleService SERVICE = new ScheduleService();
	public static ScheduleService me() {
		return SERVICE;
	}
	
	/**
	 * 
	 * findById(这里用一句话描述这个方法的作用)  
	 * (这里描述这个方法适用条件 – 可选)  
	 * @param id
	 * @param flag  true-->转换cronExp表达式为中文
	 * @return   
	 *JJob  
	 * @exception   
	 * @since  1.0.0
	 */
	public JJob findById(final Integer id, boolean flag){
		JJob job = JobQuery.me().findById(id);
		if(flag){
			job.setCronExp(convertChinese(job.getCronExp()));
		}		
		return job;
	}
	
	@Before(Tx.class)
	public boolean addSave(JJob job){
		job.setJobStatus(null==job.getJobStatus()?0:1);
        job.setCronExp(coreJonit(job.getCronExp()));
        Boolean a = job.save();
        if(a){
            qj.setJobId(Integer.parseInt(job.getId() + ""));
            qj.setJobName(job.getJobName());
            qj.setJobGroup(job.getJobGroup());
            qj.setJobStatus(job.getJobStatus());
            qj.setCronExpression(job.getCronExp());
            try {
                scheduler = gSchedulerFactory.getScheduler();
                TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
                CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
                if (job.getJobStatus() == 1){
                    quartzManager.addQuartzJob(qj, trigger, QuartzJobFactory.class);
                }
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
            return true;
        }       
		return false;
	}
	
	@Before(Tx.class)
	public boolean editSave(JJob job){
		job.setJobStatus(null==job.getJobStatus()?0:1);
        //清除定时
        TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
        quartzManager.deleteJob(triggerKey);

        job.setCronExp(coreJonit(job.getCronExp()));
        qj.setJobId(Integer.parseInt(job.getId() + ""));
        qj.setJobName(job.getJobName());
        qj.setJobGroup(job.getJobGroup());
        qj.setJobStatus(job.getJobStatus());
        qj.setCronExpression(job.getCronExp());
        //添加定时
        try {
            scheduler = gSchedulerFactory.getScheduler();
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (job.getJobStatus() == 1){
                quartzManager.addQuartzJob(qj, trigger, QuartzJobFactory.class);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return job.update();
	}
	
	public boolean del(Integer id){
		JJob job = JobQuery.me().findById(id);
		//清除定时方法
        try {
            scheduler = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if ( trigger != null){
                quartzManager.deleteJob(triggerKey);
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
		return job.delete();
	}
	
    /**
     * cron解析
     * @param str
     * @return
     */
    public String coreJonit(String str){
        String [] arr = str.split(" ");
        String b = "0 ";
        if ("*".equals(arr[0])){
            arr[0] = "0/1";
        }
        b += arr[0].concat(" ").concat(arr[1]).concat(" ").concat(arr[2]).concat(" ").concat(arr[3]).concat(" ").concat("?").concat(" ").concat(arr[4]);
        return  b;
    }

    /**
     * 转成中文
     * @param str
     * @return
     */
    public String convertChinese(String str){
        String [] analy = str.split(" ");
        String[] crons = {"每","分","时","日","月","年","运行一次"};
        String join = "每";
        if ("0/1".equals(analy[1])){
            return  crons[0]+crons[1]+"钟"+crons[6];
        }
        int j = 0;
        String h = "";
        for(int i = analy.length-1; 0 < i; i--){
            logger.info(analy[i]);
            if (!"*".equals(analy[i])&&!"?".equals(analy[i])){
                if (j==0){
                    h = crons[i+1];
                    logger.info("h"+h);
                    j++;
                }
                join += analy[i]+crons[i];
            }
        }
        join += crons[6];
        StringBuffer a = new StringBuffer(join);
        a.insert(1,h);
        return String.valueOf(a);
    }
	
}
