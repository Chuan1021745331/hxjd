package com.base.service;

import com.base.model.JCircuit;
import com.base.model.JTbm;
import com.base.model.JWorksite;
import com.base.model.dto.CircuitWorksiteDto;
import com.base.model.dto.WorkSiteDto;
import com.base.query.CameraQuery;
import com.base.query.CircuitQuery;
import com.base.query.WorkSiteQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * @类名: CircuitService.class
 * @包名: com.base.service
 * @描述: 线路逻辑层
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
     * 获取所有线路数量
     * @return
     */
    public long getAllCircuitCount(){
        return CircuitQuery.me().getAllCircuitCount();
    }

    /**
     * 获取路线以及路线下的工点
     * @return
     */
    public List<CircuitWorksiteDto> getCircuitWorksiteDtos(){
        List<JWorksite> worksites = WorkSiteQuery.me().findAllWorksite();
        List<JCircuit> circuits = getAllCircuit();
        List<JTbm> tbms = TbmService.me().findAllTbm();
        List<CircuitWorksiteDto> circuitWorksiteDtos = new ArrayList<>();
        for(JCircuit circuit:circuits){
            CircuitWorksiteDto circuitWorksiteDto = new CircuitWorksiteDto();
            circuitWorksiteDto.setId(circuit.getId());
            circuitWorksiteDto.setPoints(circuit.getPoints());
            circuitWorksiteDto.setColor(circuit.getColor());
            circuitWorksiteDto.setGeneral(circuit.getGeneral());
            circuitWorksiteDto.setName(circuit.getName());
            List<WorkSiteDto> workSiteDtos = getWorkSiteDtos(circuit,worksites,tbms);

            circuitWorksiteDto.setWorksites(workSiteDtos);
            circuitWorksiteDtos.add(circuitWorksiteDto);
        }
        return circuitWorksiteDtos;
    }

    /**
     * 获取WorkSiteDto
     * @param circuit
     * @param worksites
     * @param tbms
     * @return
     */
    public List<WorkSiteDto> getWorkSiteDtos(JCircuit circuit,List<JWorksite> worksites,List<JTbm> tbms){
        List<WorkSiteDto> workSiteDtos = new ArrayList<>();
        for(JWorksite worksite:worksites){
            if(circuit.getId().intValue() == worksite.getCircuitid().intValue()){
                WorkSiteDto workSiteDto = new WorkSiteDto();
                workSiteDto.setColor(worksite.getColor());
                workSiteDto.setId(worksite.getId());
                workSiteDto.setCoord(worksite.getCoord());
//                workSiteDto.setGeneral(worksite.getGeneral());
                workSiteDto.setName(worksite.getName());
                workSiteDto.setCircuitid(worksite.getCircuitid());
                List<JTbm> tbms1 = new ArrayList<>();
                for (JTbm tbm:tbms){
                    if(tbm.getWorksiteid().intValue() == worksite.getId()){
                        tbms1.add(tbm);
                    }
                }
                workSiteDto.setTbms(tbms1);
                workSiteDtos.add(workSiteDto);
            }
        }
        return workSiteDtos;
                /*for(JWorksite worksite:worksites){
                if(circuit.getId().intValue() == worksite.getCircuitid().intValue()){
                    WorkSiteDto workSiteDto = new WorkSiteDto();
                    workSiteDto.setColor(worksite.getColor());
                    workSiteDto.setId(worksite.getId());
                    workSiteDto.setCoord(worksite.getCoord());
                    workSiteDto.setGeneral(worksite.getGeneral());
                    workSiteDto.setName(worksite.getName());
                    workSiteDto.setCircuitid(worksite.getCircuitid());
                    List<JTbm> tbms1 = new ArrayList<>();
                    for (JTbm tbm:tbms){
                        if(tbm.getWorksiteid().intValue() == worksite.getId()){
                            tbms1.add(tbm);
                        }
                    }
                    workSiteDto.setTbms(tbms1);
                    workSiteDtos.add(workSiteDto);
                }
            }*/
    }
}
