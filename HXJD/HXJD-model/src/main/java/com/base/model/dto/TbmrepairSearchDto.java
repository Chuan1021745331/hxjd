package com.base.model.dto;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmrepairSearchDto
 * @包名: com.base.model.dto
 * @描述: 查询维保记录的参数
 * @所属: 华夏九鼎
 * @日期: 2018/1/26 14:52
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TbmrepairSearchDto {
    //盾构机名称
    private String tbmname;
    //维保开始时间
    private String repairtimeStart;
    //维保结束时间
    private String repairtimeEnd;
    //下次维保开始时间
    private String cycleStart;
    //下次维保结束时间
    private String cycleEnd;
    //维保人
    private String repairman;
    //填写人
    private String writer;

    public String getTbmname() {
        return tbmname;
    }

    public void setTbmname(String tbmname) {
        this.tbmname = tbmname;
    }

    public String getRepairtimeStart() {
        return repairtimeStart;
    }

    public void setRepairtimeStart(String repairtimeStart) {
        this.repairtimeStart = repairtimeStart;
    }

    public String getRepairtimeEnd() {
        return repairtimeEnd;
    }

    public void setRepairtimeEnd(String repairtimeEnd) {
        this.repairtimeEnd = repairtimeEnd;
    }

    public String getCycleStart() {
        return cycleStart;
    }

    public void setCycleStart(String cycleStart) {
        this.cycleStart = cycleStart;
    }

    public String getCycleEnd() {
        return cycleEnd;
    }

    public void setCycleEnd(String cycleEnd) {
        this.cycleEnd = cycleEnd;
    }

    public String getRepairman() {
        return repairman;
    }

    public void setRepairman(String repairman) {
        this.repairman = repairman;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
