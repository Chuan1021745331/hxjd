package com.base.service.app.AppHandle.AppBean;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${FILE_NAME}
 * @包名: com.base.service.app.AppHandle.AppBean
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/5/31 16:36
 * @版本: V1.0
 * @创建人：hxjd
 * @修改人：hxjd
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class MediatorBeanDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String terminalName;
    private String terminalNum;
    private String sdNum;
    private String terminalStauts;
    private String mediatorName;
    private String camp;
    private String mediatorGrade;
    private String x;
    private String y;

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public String getTerminalNum() {
        return terminalNum;
    }

    public void setTerminalNum(String terminalNum) {
        this.terminalNum = terminalNum;
    }

    public String getSdNum() {
        return sdNum;
    }

    public void setSdNum(String sdNum) {
        this.sdNum = sdNum;
    }

    public String getTerminalStauts() {
        return terminalStauts;
    }

    public void setTerminalStauts(String terminalStauts) {
        this.terminalStauts = terminalStauts;
    }

    public String getMediatorName() {
        return mediatorName;
    }

    public void setMediatorName(String mediatorName) {
        this.mediatorName = mediatorName;
    }

    public String getCamp() {
        return camp;
    }

    public void setCamp(String camp) {
        this.camp = camp;
    }

    public String getMediatorGrade() {
        return mediatorGrade;
    }

    public void setMediatorGrade(String mediatorGrade) {
        this.mediatorGrade = mediatorGrade;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }
}
