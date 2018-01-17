package com.base.service;

import com.base.model.JCircuit;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.service
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/17 16:50
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class CircuitService {
    private static final CircuitService SERVICE = new CircuitService();
    public static CircuitService me() {
        return SERVICE;
    }

    public  JCircuit saveAndUpdateCircuit(JCircuit circuit){
        boolean b = circuit.saveOrUpdate();
        if(b){
            return circuit;
        }
        return null;
    }
}
