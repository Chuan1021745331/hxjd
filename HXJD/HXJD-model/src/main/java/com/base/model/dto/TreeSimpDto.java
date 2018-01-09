package com.base.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: DepartmentSimpDto
 * @包名: com.base.model.dto
 * @描述: 树形控件数据格式
 * @所属: 华夏九鼎
 * @日期: 2018/1/9 11:49
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TreeSimpDto implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String score;
    private List<TreeSimpDto> children= new ArrayList<>();

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<TreeSimpDto> getChildren() {
        return children;
    }

    public void setChildren(List<TreeSimpDto> children) {
        this.children = children;
    }
}
