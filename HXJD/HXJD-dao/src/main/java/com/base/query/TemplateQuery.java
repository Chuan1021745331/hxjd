package com.base.query;

import com.base.model.JTemplate;
import com.base.utils.StringUtils;
import com.jfinal.plugin.activerecord.Db;

import java.util.LinkedList;
import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TemplateQuery
 * @包名: com.base.query
 * @描述: (kindeditor模板)
 * @所属: 华夏九鼎
 * @日期: 2018/3/20 13:37
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class TemplateQuery {
    private final static JTemplate DAO=new JTemplate();
    private final static TemplateQuery QUERY=new TemplateQuery();
    public static TemplateQuery me(){
        return QUERY;
    }

    /**
     * 查询所有模板
     * @return
     */
    public List<JTemplate> findAllTemplate(){
        String sql="select * from j_template order by id asc";
        return DAO.find(sql);
    }

    /**
     * 条件查询所有记录总数
     * @param where
     * @return
     */
    public long findTemplateWhere(String where){
        StringBuilder sql = new StringBuilder("select count(*) from j_template where 1=1");
        if(StringUtils.isNotBlank(where)){
            sql.append(" and CONCAT( IFNULL(name, ''),IFNULL(content, ''),IFNULL(descript, '')) LIKE '%"+where+"%' ");
        }
        return Db.queryLong(sql.toString());
    }

    /**
     * 条件查询并分页
     * @param page
     * @param limit
     * @param where
     * @return
     */
    public List<JTemplate> findTemplateWherePage(int page,int limit,String where){
        StringBuilder sql = new StringBuilder("select * from j_template where 1=1 ");
        if(StringUtils.isNotBlank(where)){
            sql.append(" and CONCAT( IFNULL(name, ''),IFNULL(content, ''),IFNULL(descript, '')) LIKE '%"+where+"%' ");
        }
        sql.append(" order by id asc limit ?,?");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(limit*page-limit);
        params.add(limit);
        return DAO.find(sql.toString(),params.toArray());
    }

    public JTemplate findById(int id){
        StringBuilder sql=new StringBuilder("select * from j_template ");
        sql.append(" where id="+id);
        return DAO.findFirst(sql.toString());
    }

    public boolean deleteByID(int id){
       return DAO.deleteById(id);
    }
}
