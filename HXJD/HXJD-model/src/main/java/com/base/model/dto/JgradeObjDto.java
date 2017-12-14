package com.base.model.dto;

import com.base.model.JGrademark;
import com.base.model.JKpi;
import com.base.model.JObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hxjd009 on 2017/5/28.
 */
public class JgradeObjDto implements Serializable {
    private static final long serialVersionUID = 1L;

    List<JObject> objects = new ArrayList<JObject>();

    private JGrademark m;


    public List<JObject> getObjects() {
        return objects;
    }

    public void setObjects(List<JObject> objects) {
        this.objects = objects;
    }

    public JGrademark getM() {
        return m;
    }

    public void setM(JGrademark m) {
        this.m = m;
    }
}
