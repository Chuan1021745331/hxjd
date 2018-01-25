package com.base.model.dto;

import com.base.model.JTbm;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.model.dto
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/25 9:59
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class WorkSiteDto {
    private int id;
    private String name;
    private String general;
    private int circuitid;
    private String coord;
    private String color;
    private List<JTbm> tbms;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String general) {
        this.general = general;
    }

    public int getCircuitid() {
        return circuitid;
    }

    public void setCircuitid(int circuitid) {
        this.circuitid = circuitid;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<JTbm> getTbms() {
        return tbms;
    }

    public void setTbms(List<JTbm> tbms) {
        this.tbms = tbms;
    }
}
