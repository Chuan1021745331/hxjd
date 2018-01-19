package com.base.model.dto;

import com.base.model.JWorksite;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.model.dto
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/19 16:51
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class CircuitWorksiteDto {
    private int id;
    private String name;
    private List<JWorksite> worksites;

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

    public List<JWorksite> getWorksites() {
        return worksites;
    }

    public void setWorksites(List<JWorksite> worksites) {
        this.worksites = worksites;
    }
}
