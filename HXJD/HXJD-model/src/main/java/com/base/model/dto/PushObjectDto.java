package com.base.model.dto;

import java.util.List;

/**
 * Created by hxjd009 on 2017/6/1.
 */
public class PushObjectDto {
    private Integer id;
    private String drillname;//训练代号
//    private String pushTime;//时间
    private String mediator;//阶段
    private String project;//项目
    private String role;//角色
    private String camp;//0为红军，1为蓝军
    private List<Contents> content;//内容

    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }

    public String getDrillname() {
        return drillname;
    }

    public void setDrillname(String drillname) {
        this.drillname = drillname;
    }

 /*   public String getPushTime() {
        return pushTime;
    }

    public void setPushTime(String pushTime) {
        this.pushTime = pushTime;
    }*/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getMediator() {
        return mediator;
    }

    public void setMediator(String mediator) {
        this.mediator = mediator;
    }

    public List<Contents> getContent() {
        return content;
    }

    public void setContent(List<Contents> content) {
        this.content = content;
    }
}
