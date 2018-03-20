package com.base.service;

import com.base.model.JTemplate;
import com.base.query.TbmrepairQuery;
import com.base.query.TemplateQuery;
import com.jfinal.plugin.activerecord.Record;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TemplateService
 * @包名: com.base.service
 * @描述: kindeditor模板
 * @所属: 华夏九鼎
 * @日期: 2018/3/20 13:44
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TemplateService {
    private static final TemplateService SERVICE=new TemplateService();
    public static TemplateService me(){
        return SERVICE;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public JTemplate findTemplateById(int id){
        return TemplateQuery.me().findById(id);
    }

    /**
     * 条件查询所有记录数量
     * @param where
     * @return
     */
    public long findTemplateWhere(String where){
        return TemplateQuery.me().findTemplateWhere(where);
    }

    /**
     * 查询所有模板
     * @return
     */
    public List<JTemplate> findAllTemplate(){
        return TemplateQuery.me().findAllTemplate();
    }

    /**
     * 条件查询并分页
     * @param page
     * @param limit
     * @param where
     * @param count
     * @return
     */
    public List<JTemplate> findAllTemplateWherePage(int page,int limit,String where,long count){
        List<JTemplate> list = new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            list = TemplateQuery.me().findTemplateWherePage(page, limit, where);
        }
        return list;
    }

    public boolean deleteById(int id){
        return TemplateQuery.me().deleteByID(id);
    }
}
