package com.base.service;

import com.base.model.JCircuit;
import com.base.model.JTbm;
import com.base.model.JTbmrepair;
import com.base.model.JWorksite;
import com.base.model.dto.TbmrepairSearchDto;
import com.base.query.CircuitQuery;
import com.base.query.TbmrepairQuery;
import com.base.query.WorkSiteQuery;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmrepairService
 * @包名: com.base.service
 * @描述: (盾构机维修记录服务)
 * @所属: 华夏九鼎
 * @日期: 2018/1/22 10:26
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TbmrepairService {
    private final static TbmrepairService SERVICE=new TbmrepairService();
    public static TbmrepairService me(){
        return SERVICE;
    }

    public boolean delByTbmrepairId(Integer id){
        JTbmrepair byTbmrepairId = TbmrepairQuery.me().findByTbmrepairId(id);
        return byTbmrepairId.delete();
    }

    public JTbmrepair findTbmrepairByTbmrepairId(Integer tbmrepairId){
        JTbmrepair tbmrepair = TbmrepairQuery.me().findByTbmrepairId(tbmrepairId);

        //盾构机详情
        JTbm tbm = TbmService.me().findTbmById(tbmrepair.getTbmId());
        addTbmInfo(tbmrepair,tbm);
        return tbmrepair;
    }

    public List<Record> findListTbmrepairByTbmId(Integer page,Integer limit,Integer tbmId,long count){
        List<Record> list = new ArrayList<>();
        if(count!=0) {
            page = (page > count / limit && count % limit == 0) ? page - 1 : page;
            list=TbmrepairQuery.me().findListTbmrepairTbmUser(page,limit,tbmId);
        }
        return list;
    }

    public long findCountTbmrepairByTbmId(Integer tbmId){
        return TbmrepairQuery.me().findCountByTbmId(tbmId);
    }

    /**
     * 关键字查询结果数
     * @param search
     * @return
     */
    public long findCountTbmrepairTbmUser(String search){
        return TbmrepairQuery.me().findCountTbmrepairTbmUser(search);
    }

    /**
     * 关键字分页查询
     * @param page
     * @param limit
     * @param where
     * @param count
     * @return
     */
    public List<Record> findListTbmrepairTbmUser(Integer page, Integer limit, String where, long count){
        List<Record> list = new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            list = TbmrepairQuery.me().findListTbmrepairTbmUser(page, limit, where);
        }
        for(Record record:list){
            if(StringUtils.isEmpty(record.getStr("tbmName"))){
                record.set("tbmName",record.getStr("tbmname"));
            }
        }
        return list;
    }

    /**
     * 多条件查询结果数
     * @param searchDto
     * @return
     */
    public long findCountTbmrepairTbmUser(TbmrepairSearchDto searchDto){
        return TbmrepairQuery.me().findCountTbmrepairTbmUser(searchDto);
    }

    /**
     * 多条件分页查询结果
     * @param page
     * @param limit
     * @param searchDto
     * @param count
     * @return
     */
    public List<Record> findListTbmrepairTbmUser(Integer page, Integer limit, TbmrepairSearchDto searchDto, long count){
        List<Record> list = new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            list = TbmrepairQuery.me().findListTbmrepairTbmUser(page, limit, searchDto);
        }
        for(Record record:list){
            if(StringUtils.isEmpty(record.getStr("tbmName"))){
                record.set("tbmName",record.getStr("tbmname"));
            }
        }
        return list;
    }

    public boolean editTbmrepairSave(JTbmrepair tbmrepair){
        JTbmrepair tempTbmrepair = TbmrepairQuery.me().findByTbmrepairId(tbmrepair.getId());

        //补全盾构机信息
        JTbm tbm = TbmService.me().findTbmById(tbmrepair.getTbmId());
        addTbmInfo(tbmrepair,tbm);

        tempTbmrepair.setTbmId(tbmrepair.getTbmId());
        tempTbmrepair.setRepairman(tbmrepair.getRepairman());
        tempTbmrepair.setRepairtime(tbmrepair.getRepairtime());
        tempTbmrepair.setContent(tbmrepair.getContent());
        tempTbmrepair.setCycle(tbmrepair.getCycle());

        return tempTbmrepair.saveOrUpdate();
    }

    /**
     * @MethodName: findLatestByTbmId
     * @Description: 根据盾构机id查询最近一次维修记录
     * @param tbmId
     * @return: com.base.model.JTbmrepair
     */
    public JTbmrepair findLatestByTbmId(Integer tbmId){
        return TbmrepairQuery.me().findLatestByTbmId(tbmId);
    }

    /**
     * @MethodName: addTbmInfo
     * @Description: (将盾构机信息添加到维保记录里面)
     * @param tbmrepair
     * @param tbm
     * @return: void
     */
    public void addTbmInfo(JTbmrepair tbmrepair,JTbm tbm){
        JWorksite worksite=null;
        JCircuit circuit=null;
        if(tbm!=null){
            tbmrepair.setTbmname(tbm.getName());
            worksite = WorkSiteQuery.me().findById(tbm.getWorksiteid());
            circuit = CircuitQuery.me().findById(worksite.getCircuitid());
        }
        if(null!=worksite) {
            tbmrepair.setWorksitename(worksite.getName());
        }
        if(null!=circuit) {
            tbmrepair.setCircuitname(circuit.getName());
        }
    }


    public List<Record> findTbmrepairltTime(String date,Integer limit){
        return TbmrepairQuery.me().findListLtTime(date,limit);
    }

}
