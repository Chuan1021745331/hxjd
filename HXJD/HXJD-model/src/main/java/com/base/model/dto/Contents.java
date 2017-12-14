package com.base.model.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/6/1.
 */
public class Contents {
    private String name;
    private Double score;
    private Integer n = 0;
    private Integer endNode = 0;
    private Integer parentId;
    private List<Contents> children = new ArrayList<>();

    public List<Contents> getChildren() {
        return children;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public void setChildren(List<Contents> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Integer getN() {
        return n;
    }

    public void setN(Integer n) {
        this.n = n;
    }

    public Integer getEndNode() {
        return endNode;
    }

    public void setEndNode(Integer endNode) {
        this.endNode = endNode;
    }
}
