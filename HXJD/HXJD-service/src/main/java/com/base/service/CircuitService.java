package com.base.service;

import com.base.model.JCircuit;
import com.base.model.JWorksite;
import com.base.query.CircuitQuery;
import com.base.query.WorkSiteQuery;

import java.util.List;

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

    /**
     * 判断线路能不能删除
     * @param id
     * @return
     */
    public boolean isCanDel(int id){
        List<JWorksite> worksites = WorkSiteQuery.me().findByCirciuteId(id);
        if(worksites == null || worksites.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * 删除线路
     * @param id
     * @return
     */
    public boolean delCircuitById(int id){
        return CircuitQuery.me().delCircuitById(id);
    }
}
