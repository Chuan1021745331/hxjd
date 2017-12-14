package com.base.im.client;

import com.base.model.JMediator;

import java.util.List;


/**
 * Created by 104 on 2017/6/9.
 */
public class TestBean {
    private int id;
    private String mediatorName;
    private List<JMediator> mediators;


    public List<JMediator> getMediators() {
        return mediators;
    }

    public void setMediators(List<JMediator> mediators) {
        this.mediators = mediators;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMediatorName() {
        return mediatorName;
    }

    public void setMediatorName(String mediatorName) {
        this.mediatorName = mediatorName;
    }
}

