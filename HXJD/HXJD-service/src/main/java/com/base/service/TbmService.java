package com.base.service;

import com.base.model.JCamera;
import com.base.model.JTbm;
import com.base.query.CameraQuery;
import com.base.query.TbmQuery;
import com.jfinal.plugin.activerecord.Record;
import org.quartz.ee.jta.JTAAnnotationAwareJobRunShellFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    public long findAllTbmCount(){
        return TbmQuery.me().findAllTbmCount();
    }
    /**
     * 查询该线路下所有盾构机数量
     * @param circuitid
     * @return
     */
    public long findTbmCountByCircuitId(int circuitid){
        return TbmQuery.me().findTbmCountByCircuitId(circuitid);
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
     * 分页查询盾构机外加最近的一次维修记录
     * @param page
     * @param limit
     * @param workSiteId
     * @param count
     * @return
     */
    public List<Record> findTbmTbmrepairByworkSiteId(int page, int limit, int workSiteId, long count){
        List<Record> routeList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            routeList = TbmQuery.me().findTbmTbmrepairByWorkSiteId(page, limit, workSiteId);
        }
        for(Record record:routeList){
            checkRecordData(record);
        }
        return routeList;
    }
    /**
     * 分页查询盾构机外加最近的一次维修记录
     * @param page
     * @param limit
     * @param circuitid
     * @param count
     * @return
     */
    public List<Record> findTbmTbmrepairByCircuitId(int page, int limit, int circuitid, long count){
        List<Record> routeList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            routeList = TbmQuery.me().findTbmTbmrepairByCircuitId(page, limit, circuitid);
        }
        for(Record record:routeList){
            checkRecordData(record);
        }
        return routeList;
    }

    /**
     * 分页查询所有盾构机
     * @param page
     * @param limit
     * @param count
     * @return
     */
    public List<Record> findAllTbmTbmrepair(int page, int limit, long count){
        List<Record> routeList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            routeList = TbmQuery.me().findAllTbmTbmrepair(page, limit);
        }
        for(Record record:routeList){
            checkRecordData(record);
        }
        return routeList;
    }

    /**
     * 分页查询所有盾构机加工点信息
     * @param page
     * @param limit
     * @param count
     * @return
     */
    public List<Record> findAllTbmWorksite(int page, int limit, long count){
        List<Record> routeList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            routeList = TbmQuery.me().findAllTbmWorksite(page, limit);
        }
        for(Record record:routeList){
            checkRecordData(record);
        }
        return routeList;
    }

    public void checkRecordData(Record record){
        Map<String, Object> columns = record.getColumns();
        for(Map.Entry<String, Object> entry:columns.entrySet()){
            if(entry.getValue()==null){
                columns.put(entry.getKey(),"暂无记录");
            }
        }
    }

    /**
     * 查询所有盾构机
     * @return
     */
    public List<JTbm> findAllTbm(){
        return TbmQuery.me().findAllTbm();
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
    /**
     * 判断盾构机是否能删除盾构机
     * @param id
     * @return
     */
    public boolean isCanDel(int id){
        List<JCamera> cameras = CameraQuery.me().findCamerasByTbmId(id);
        if(cameras != null && cameras.size() != 0){
            return false;
        }
        return true;
    }

    public List<JTbm> findTbmByWorkSiteId(int workSiteid){
        return TbmQuery.me().findByWorkSiteId(workSiteid);
    }
    public JTbm findTbmById(int tbmId){
        return TbmQuery.me().findTbmById(tbmId);
    }
}
