package com.base.service;

import com.base.model.JWorksite;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.service
 * @描述: ${TODO}(用一句话描述该文件做什么)
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
}
