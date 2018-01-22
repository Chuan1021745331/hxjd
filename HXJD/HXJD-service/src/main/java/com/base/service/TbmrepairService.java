package com.base.service;

import com.base.model.JTbmrepair;
import com.base.query.TbmrepairQuery;
import com.jfinal.plugin.activerecord.Record;

import java.util.ArrayList;
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
        return TbmrepairQuery.me().findByTbmrepairId(tbmrepairId);
    }

    public List<JTbmrepair> findTbmrepairByTbmId(Integer page,Integer limit,Integer tbmId,long count){
        List<JTbmrepair> list = new ArrayList<>();
        if(count!=0) {
            page = (page > count / limit && count % limit == 0) ? page - 1 : page;
            list=TbmrepairQuery.me().findByTbmId(page,limit,tbmId);
        }
        return list;
    }

    public long findCountTbmrepairTbmUser(){
        return TbmrepairQuery.me().findCountTbmrepairTbmUser();
    }

    public List<Record> findListTbmrepairTbmUser(Integer page, Integer limit, String where, long count){
        List<Record> list = new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            list = TbmrepairQuery.me().findListTbmrepairTbmUser(page, limit, where);
        }
        return list;
    }

    public boolean editTbmrepairSave(JTbmrepair tbmrepair){
        JTbmrepair tempTbmrepair = TbmrepairQuery.me().findByTbmrepairId(tbmrepair.getId());
        tempTbmrepair.setTbmId(tbmrepair.getTbmId());
        tempTbmrepair.setRepairman(tbmrepair.getRepairman());
        tempTbmrepair.setRepairtime(tbmrepair.getRepairtime());
        tempTbmrepair.setContent(tbmrepair.getContent());
        tempTbmrepair.setCycle(tbmrepair.getCycle());

        return tempTbmrepair.saveOrUpdate();
    }
}
