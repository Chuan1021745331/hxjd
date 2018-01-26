package com.base.model.dto;

import com.base.model.JCamera;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.model.dto
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/26 16:16
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class TbmDto {
    private int id;
    private String name;
    private String code;
    private String mac;
    private String coord;
    private int worksiteid;
    private List<JCamera> cameras;

    public List<JCamera> getCameras() {
        return cameras;
    }

    public void setCameras(List<JCamera> cameras) {
        this.cameras = cameras;
    }

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getCoord() {
        return coord;
    }

    public void setCoord(String coord) {
        this.coord = coord;
    }

    public int getWorksiteid() {
        return worksiteid;
    }

    public void setWorksiteid(int worksiteid) {
        this.worksiteid = worksiteid;
    }
}
