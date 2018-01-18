package com.base.service;

import com.base.model.JTbm;
import com.base.query.TbmQuery;
import org.quartz.ee.jta.JTAAnnotationAwareJobRunShellFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @类名: TbmService
 * @包名: com.base.service
 * @描述: tbm的逻辑层
 * @日期: 2018/1/17 18:01
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class TbmService {
    private static final TbmService SERVICE = new TbmService();
    public static TbmService me() {
        return SERVICE;
    }
    /*
    * 获取工点下盾构机的数量*/
    public long findTbmCountByWorkSiteId(Integer workSiteId){
        int id = workSiteId.intValue();
        return TbmQuery.me().findTbmCountByWorkSiteId(id);
    }

    /**
     * 分页查询盾构机
     * @param page
     * @param limit
     * @param workSiteId
     * @param count
     * @return
     */
    public List<JTbm> findTbmsByworkSiteId(int page, int limit, int workSiteId, long count){
        List<JTbm> routeList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            routeList = TbmQuery.me().findTbmByWorkSiteId(page, limit, workSiteId);
        }
        return routeList;
    }

    /**
     * 保存或更新盾构机
     * @param tbm
     * @return
     */
    public JTbm saveAndUpdateTbm(JTbm tbm){
        boolean b = tbm.saveOrUpdate();
        if(b){
            return tbm;
        }
        return null;
    }
}
