package com.base.service;

import com.base.model.JCircuit;
import com.base.model.JWorksite;
import com.base.model.dto.CircuitWorksiteDto;
import com.base.query.CameraQuery;
import com.base.query.CircuitQuery;
import com.base.query.WorkSiteQuery;

import java.util.ArrayList;
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

    /**
     * 查询所有线路
     * @return
     */
    public List<JCircuit> getAllCircuit(){
       return CircuitQuery.me().findAllCircuit();
    }

    /**
     * 获取路线以及路线下的工点
     * @return
     */
    public List<CircuitWorksiteDto> getCircuitWorksiteDtos(){
        List<JWorksite> worksites = WorkSiteQuery.me().findAllWorksite();
        List<JCircuit> circuits = getAllCircuit();
        List<CircuitWorksiteDto> circuitWorksiteDtos = new ArrayList<>();
        for(JCircuit circuit:circuits){
            CircuitWorksiteDto circuitWorksiteDto = new CircuitWorksiteDto();
            circuitWorksiteDto.setId(circuit.getId());
            circuitWorksiteDto.setName(circuit.getName());
            List<JWorksite> cworksites = new ArrayList<>();
            for(JWorksite worksite:worksites){
                if(circuit.getId().intValue() == worksite.getCircuitid().intValue()){
                    cworksites.add(worksite);
                }
            }
            circuitWorksiteDto.setWorksites(cworksites);
            circuitWorksiteDtos.add(circuitWorksiteDto);
        }
        return circuitWorksiteDtos;
    }
}
