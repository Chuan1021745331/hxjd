package com.base.service;

import com.base.model.JCamera;
import com.base.query.CameraQuery;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.service
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/18 14:58
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class CameraService {
    private static final CameraService SERVICE = new CameraService();
    public static CameraService me() {
        return SERVICE;
    }

    /**
     * 添加或者更改摄像头信息
     * @return
     */
    public JCamera saveAndUpdateCamera(JCamera camera){
        boolean b = camera.saveOrUpdate();
        if(b){
            return camera;
        }
        return null;
    }

    /**
     * 通过盾构机id查询摄像头
     * @param id
     * @return
     */
    public List<JCamera> findCamerasByTbmId(Integer id){
        return CameraQuery.me().findCamerasByTbmId(id);
    }

    public boolean delCameraById(Integer id){
        return CameraQuery.me().delCameraById(id);
    }
}
