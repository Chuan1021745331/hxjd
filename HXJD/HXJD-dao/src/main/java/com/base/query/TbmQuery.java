package com.base.query;

import com.base.model.JTbm;
import com.jfinal.plugin.activerecord.Db;

import java.util.LinkedList;
import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.query
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/17 16:21
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class TbmQuery {
    protected static final JTbm DAO = new JTbm();
    private static final TbmQuery QUERY = new TbmQuery();

    public static TbmQuery me() {
        return QUERY;
    }

    public JTbm findTbmById(int id){
        return DAO.findById(id);
    }
    public long findTbmCountByWorkSiteId(int workSiteId){
        return Db.queryLong("select count(*) from j_tbm");
    }

    public List<JTbm> findTbmByWorkSiteId(int page, int limit, int workSiteId){
        StringBuilder sql=new StringBuilder("select * from j_tbm");
        sql.append(" where worksiteid = ? order by id asc limit ?,?");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(workSiteId);
        params.add(limit*page-limit);
        params.add(limit);
        return DAO.find(sql.toString(),params.toArray());
    }

    /**
     * 通过工点id查询盾构机
     * @param id
     * @return
     */
    public List<JTbm> findByWorkSiteId(int id){
        return DAO.find("select * from j_tbm where worksiteid="+id);
    }
}
