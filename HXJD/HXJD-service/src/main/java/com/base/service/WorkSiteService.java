package com.base.service;

import com.base.model.JCamera;
import com.base.model.JTbm;
import com.base.model.JWorksite;
import com.base.model.dto.TbmDto;
import com.base.model.dto.WorkSiteDto;
import com.base.model.dto.WorkSiteDto2;
import com.base.query.CameraQuery;
import com.base.query.TbmQuery;
import com.base.query.WorkSiteQuery;
import javafx.scene.Camera;

import java.util.ArrayList;
import java.util.List;

/**
 * @类名: WorkSiteService.class
 * @包名: com.base.service
 * @描述: 工点服务层
 * @日期: 2018/1/17 17:13
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class WorkSiteService {
    private static final WorkSiteService SERVICE = new WorkSiteService();
    public static WorkSiteService me() {
        return SERVICE;
    }

    public JWorksite saveAndUpdateWorksite(JWorksite worksite){
        boolean b = worksite.saveOrUpdate();
        if(b){
            return worksite;
        }
        return null;
    }

    /**
     * 通过工点id获得其下盾构机和摄像头信息
     * @param id
     * @return
     */
    public WorkSiteDto2 getWorkSiteAndTbmAndCamera(int id){
        JWorksite worksite = WorkSiteQuery.me().findById(id);
        List<JTbm> tbms = TbmService.me().findTbmByWorkSiteId(id);
        List<JCamera> cameras = CameraQuery.me().getAllCameras();
        WorkSiteDto2 workSiteDto = new WorkSiteDto2();
        if(worksite == null){
            return workSiteDto;
        }
        workSiteDto.setId(worksite.getId());
        workSiteDto.setName(worksite.getName());
        workSiteDto.setGeneral(worksite.getGeneral());
        workSiteDto.setCoord(worksite.getCoord());
        workSiteDto.setCircuitid(worksite.getCircuitid());
        workSiteDto.setColor(worksite.getColor());
        List<TbmDto> tbmDtos = new ArrayList<>();
        for(JTbm tbm : tbms){
            TbmDto tbmDto = new TbmDto();
            tbmDto.setCode(tbm.getCode());
            tbmDto.setCoord(tbm.getCoord());
            tbmDto.setName(tbm.getName());
            tbmDto.setId(tbm.getId());
            tbmDto.setMac(tbm.getMac());
            tbmDto.setWorksiteid(tbm.getWorksiteid());
            List<JCamera> wcameras = new ArrayList<>();
            for(JCamera camera:cameras){
                if(tbm.getId().intValue() == camera.getTbmid().intValue()){
                    wcameras.add(camera);
                }
            }
            tbmDto.setCameras(wcameras);
            tbmDtos.add(tbmDto);
        }
        workSiteDto.setTbmDtos(tbmDtos);
        return workSiteDto;

    }
    /**
     * 判断能不能删除工点
     * @param id
     * @return
     */
    public boolean isCanDel(int id){
        List<JTbm> tbms = TbmQuery.me().findByWorkSiteId(id);
        if(tbms == null || tbms.size() == 0){
            return true;
        }
        return false;
    }

    /**
     * 删除工点
     * @param id
     * @return
     */
    public boolean delWorkSiteById(int id){
        return WorkSiteQuery.me().delWorksiteById(id);
    }
}
