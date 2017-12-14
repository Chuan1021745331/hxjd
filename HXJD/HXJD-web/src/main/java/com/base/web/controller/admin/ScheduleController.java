package com.base.web.controller.admin;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.cron.QuartzJob;
import com.base.cron.QuartzJobFactory;
import com.base.cron.QuartzManager;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.tx.Tx;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: pelController.java
 * @包名: com.base.web.controller.admin
 * @描述: 用户配置参数的相关操作
 * @所属: 华夏九鼎
 * @日期: 2017年5月15日 上午9:36:05
 * @版本: V1.0
 * @创建人：z
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/schedule", viewPath = "/view/admin/schedule")
@RouterNotAllowConvert
public class ScheduleController extends BaseController {
    public static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static Scheduler  scheduler ;
    QuartzManager quartzManager = new QuartzManager();
    QuartzJob qj = new QuartzJob();

    @Before(ButtonInterceptor.class)
    public void index() {
        render("schedule.html");
    }

    public void scheduleData() {
        Integer draw = getParaToInt("draw");
        Integer start = getParaToInt("start");
        Integer length = getParaToInt("length");
        Integer column = getParaToInt("order[0][column]");
        String order = getPara("order[0][dir]");
        String search = getPara("search[value]");
        if(search.equals("关闭")){
            search = "0";
        }
        else if (search.equals("开启")){
            search = "1";
        }
        List<JJob> list = JobQuery.me().findList(start, length,column,order,search);
        for (JJob job:list){
            job.setCronExp(convertChinese(job.getCronExp()));
        }
        long count = JobQuery.me().findConunt(search);
        renderPageResult(draw, (int)count, (int)count, list);
    }

    public void details() {
        Integer id = getParaToInt("id");
        JJob job = JobQuery.me().findById(id);
        job.setCronExp(convertChinese(job.getCronExp()));
        setAttr("job", job);
        render("details.html");
    }
    public void add() {
        render("add.html");
    }

    public void addSave() {
        JJob job = getModel(JJob.class);
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
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }

    public void Schedule(JJob job) {
        try {
            scheduler = gSchedulerFactory.getScheduler();
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            if (job.getJobStatus() == 0){
                if ( trigger != null){
                    quartzManager.deleteJob(triggerKey);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void edit() {
        Integer id = getParaToInt("id");
        JJob job = JobQuery.me().findById(id);
        setAttr("job", job);
        render("edit.html");
    }
    @Before(Tx.class)
    public void editSave() {
        JJob job = getModel(JJob.class);
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
        Boolean a = job.update();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    public void del() {
        Integer id = getParaToInt("id");
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
        boolean a = job.delete();
        if(a){
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_RELIEVE_SEAT);

    }

    /**
     * cron解析
     * @param str
     * @return
     */
    public String coreJonit(String str){
        String [] arr = str.split(" ");
        String b = "0 ";
        if (arr[0].equals("*")){
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
        if (analy[1].equals("0/1")){
            return  crons[0]+crons[1]+"钟"+crons[6];
        }
        int j = 0;
        String h = "";
        for(int i = analy.length-1; 0 < i; i--){
            System.out.println(analy[i]);
            if (!analy[i].equals("*")&&!analy[i].equals("?")){
                if (j==0){
                    h = crons[i+1];
                    System.out.println("h"+h);
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
