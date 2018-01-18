package com.base.query;

import com.base.model.JCamera;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.query
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/18 14:57
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class CameraQuery {
    protected static final JCamera DAO = new JCamera();
    private static final CameraQuery QUERY = new CameraQuery();

    public static CameraQuery me() {
        return QUERY;
    }

    /**
     * 通过盾构机查询摄像头
     * @param id
     * @return
     */
    public List<JCamera> findCamerasByTbmId(int id){
        return DAO.find("select * from j_camera where tbmid="+id);
    }
}
