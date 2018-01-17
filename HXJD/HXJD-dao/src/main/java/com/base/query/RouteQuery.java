package com.base.query;

import com.base.model.JRoute;
import com.jfinal.plugin.activerecord.Db;

import java.util.LinkedList;
import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.query
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/17 10:44
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class RouteQuery {
    protected static final JRoute DAO = new JRoute();
    private static final RouteQuery QUERY = new RouteQuery();

    public static RouteQuery me() {
        return QUERY;
    }

    public JRoute findById(int id){
       return DAO.findById(id);
    }

    public List<JRoute> getAllRoute(){
        return DAO.find("select * from j_route");
    }

    public long findeCountByparentAndType(int parent,int type){
        return Db.queryLong("select count(*) from j_rount where  parent="+parent+" type =" + type);
    }
    public List<JRoute> findRoutesByParent(int page,int limit,int parent){
        StringBuilder sql=new StringBuilder("select * from j_route");
        sql.append(" where parent=? and type=?");
        sql.append(" order by id asc limit ?,?");
        LinkedList<Object> params = new LinkedList<Object>();
        params.add(parent);
        params.add(JRoute.MACHINE);
        params.add(limit*page-limit);
        params.add(limit);
        return DAO.find(sql.toString(),params.toArray());
    }
}
