package com.base.cron;

/** 
 * 任务调度类 
 * @author Joyce.Luo 
 * @date 2015-3-31 下午03:32:04 
 * @version V3.0 
 * @since Tomcat6.0,Jdk1.6 
 * @copyright Copyright (c) 2015 
 */  
public class QuartzJob {  
    /** 
     * 任务ID 
     */  
    private Integer jobId;  
  
    /** 
     * 任务名称 
     */  
    private String jobName;  
  
    /** 
     * 任务分组 
     */  
    private String jobGroup;  
  
    /** 
     * 任务状态 0禁用 1启用 2删除 
     */  
    private Integer jobStatus;  
  
    /** 
     * 任务运行时间表达式 
     */  
    private String cronExpression;  
  
    /** 
     * @return the jobId 
     */  
    public Integer getJobId() {  
        return jobId;  
    }  
  
    /** 
     * @param jobId 
     *            the jobId to set 
     */  
    public void setJobId(Integer jobId) {  
        this.jobId = jobId;  
    }  
  
    /** 
     * @return the jobName 
     */  
    public String getJobName() {  
        return jobName;  
    }  
  
    /** 
     * @param jobName 
     *            the jobName to set 
     */  
    public void setJobName(String jobName) {  
        this.jobName = jobName;  
    }  
  
    /** 
     * @return the jobGroup 
     */  
    public String getJobGroup() {  
        return jobGroup;  
    }  
  
    /** 
     * @param jobGroup 
     *            the jobGroup to set 
     */  
    public void setJobGroup(String jobGroup) {  
        this.jobGroup = jobGroup;  
    }  
  
    /** 
     * @return the jobStatus 
     */  
    public Integer getJobStatus() {  
        return jobStatus;  
    }  
  
    /** 
     * @param jobStatus 
     *            the jobStatus to set 
     */  
    public void setJobStatus(Integer jobStatus) {  
        this.jobStatus = jobStatus;  
    }  
  
    /** 
     * @return the cronExpression 
     */  
    public String getCronExpression() {  
        return cronExpression;  
    }  
  
    /** 
     * @param cronExpression 
     *            the cronExpression to set 
     */  
    public void setCronExpression(String cronExpression) {  
        this.cronExpression = cronExpression;  
    }  
}  
