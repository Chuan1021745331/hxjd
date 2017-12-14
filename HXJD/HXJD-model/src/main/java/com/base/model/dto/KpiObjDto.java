package com.base.model.dto;

import com.base.model.JKpi;
import com.base.model.JObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/28.
 */
public class KpiObjDto implements Serializable {
    private static final long serialVersionUID = 1L;

    List<JObject> objects = new ArrayList<JObject>();

    private JKpi m;


    public List<JObject> getObjects() {
        return objects;
    }

    public void setObjects(List<JObject> objects) {
        this.objects = objects;
    }

    public JKpi getM() {
        return m;
    }

    public void setM(JKpi m) {
        this.m = m;
    }
}
