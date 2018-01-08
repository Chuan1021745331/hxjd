package com.base.admin.controller;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.cron.QuartzJob;
import com.base.cron.QuartzManager;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.*;
import com.base.query.JobQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: pelController.java
 * @包名: com.base.web.controller.admin
 * @描述: 定时任务的相关操作
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

    @Before(NewButtonInterceptor.class)
    public void index() {
        renderTable("schedule.html");
    }

    public void scheduleData() {
    	Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		long count =  ScheduleService.me().findConunt(where);
		List<Record> list = ScheduleService.me().findJobList(page, limit, where, count);
		renderPageResult(0, "", (int)count, list);
    }

    public void details() {
        Integer id = getParaToInt("id");
        JJob job = ScheduleService.me().findById(id, true);
        setAttr("job", job);
        render("details.html");
    }
    public void add() {
        render("add.html");
    }

    public void addSave() {
        JJob job = getModel(JJob.class);
        Boolean a = ScheduleService.me().addSave(job);
        
        if(a){
            renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
    }

/*    public void schedule(JJob job) {
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
    }*/

    public void edit() {
        Integer id = getParaToInt("id");
        JJob job = ScheduleService.me().findById(id, false);
        
        setAttr("job", job);
        render("edit.html");
    }
    
    
    public void editSave() {
        JJob job = getModel(JJob.class);
        
        boolean a = ScheduleService.me().editSave(job);
        if(a){
            renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
    }

    public void del() {
        Integer id = getParaToInt("id");
        boolean a = ScheduleService.me().del(id);
         
        if(a){
            renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
            return ;
        }
        renderAjaxResultForError(MessageConstants.DEL_RELIEVE_SEAT);

    }


}
