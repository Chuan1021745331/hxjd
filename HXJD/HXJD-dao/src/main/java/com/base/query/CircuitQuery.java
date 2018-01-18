package com.base.query;

import com.base.model.JCircuit;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.query
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/17 16:19
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class CircuitQuery {
    protected static final JCircuit DAO = new JCircuit();
    private static final CircuitQuery QUERY = new CircuitQuery();

    public static CircuitQuery me() {
        return QUERY;
    }

    public JCircuit findById(int id){
        return DAO.findById(id);
    }

    public List<JCircuit> findAllCircuit(){
        return DAO.find("SELECT * FROM j_circuit");
    }

    public boolean delCircuitById(int id){
        return DAO.deleteById(id);
    }
}
