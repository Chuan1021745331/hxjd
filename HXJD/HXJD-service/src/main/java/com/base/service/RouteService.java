package com.base.service;

import com.base.model.JCircuit;
import com.base.model.JRoute;
import com.base.model.JWorksite;
import com.base.model.dto.MenuSimpDto;
import com.base.model.dto.TreeSimpDto;
import com.base.query.CircuitQuery;
import com.base.query.RouteQuery;
import com.base.query.WorkSiteQuery;

import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.service
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/17 10:40
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
public class RouteService {
    private static final RouteService SERVICE = new RouteService();
    public static RouteService me() {
        return SERVICE;
    }

    public JRoute findById(int id){
       return RouteQuery.me().findById(id);
    }

    public JRoute saveAndUpdateRoute(JRoute route){
        boolean b = saveOrUpdate(route);
        if(b){
            return route;
        }else{
            return null;
        }
    }

    public boolean saveOrUpdate(JRoute route){
        //父节点为根节点，直接添加
        if(route.getParent()== 0){
            route.setType(JRoute.LINE_WAY);
            return route.saveOrUpdate();
        }
        JRoute parent = this.findById(route.getParent());
        //如果上一级线路，则此级是工点
        if(parent.getType()==JRoute.LINE_WAY){
            route.setType(JRoute.WORK_SITE);
        }
        //如果上级是工点，则此级是盾构机
        if(parent.getType()==JRoute.WORK_SITE){
            route.setType(JRoute.MACHINE);
        }
        return route.saveOrUpdate();
    }

    public List<TreeSimpDto> getRouteSimp() {
        /*
        * 1、获取所有节点
        * 线路节点
        * 工点节点
        * */
        List<JCircuit> circuits = CircuitQuery.me().findAllCircuit();
        List<JWorksite> worksites = WorkSiteQuery.me().findAllWorksite();

//        List<JRoute> menus = RouteQuery.me().getAllRoute();
        List<TreeSimpDto> fmts = new ArrayList<TreeSimpDto>();
        List<TreeSimpDto> mts = new ArrayList<TreeSimpDto>();
        TreeSimpDto msd = new TreeSimpDto();
        msd.setId("0");
        msd.setName("根节点");
        for (JCircuit c : circuits) {
            TreeSimpDto md = new TreeSimpDto();
            md.setId(c.getId() + "");
            md.setName(c.getName());
            List<TreeSimpDto> cmts = new ArrayList<TreeSimpDto>();
            for (JWorksite worksite : worksites) {
                if (worksite.getCircuitid().intValue() == c.getId().intValue()) {
                    TreeSimpDto cmd = new TreeSimpDto();
                    cmd.setId(worksite.getId() + "");
                    cmd.setName(worksite.getName());
                    cmts.add(cmd);
                }
            }
            md.setChildren(cmts);
            mts.add(md);
        }
        msd.setChildren(mts);
        fmts.add(msd);
        return fmts;
    }
    public List<TreeSimpDto> getChildSimp(JRoute pm, List<JRoute> ms){
        List<TreeSimpDto> mts = new ArrayList<TreeSimpDto>();
        for (JRoute m:ms) {
            if(m.getParent().intValue()==pm.getId().intValue()){
                TreeSimpDto md = new TreeSimpDto();
                md.setId(m.getId()+"");
                md.setName(m.getName());
                Integer n = 0 ;
                for (JRoute m_ : ms) {
                    if(m.getId().intValue()==m_.getParent().intValue()){
                        n++;
                    }
                }
                if(n>0){
                    md.setChildren(getChildSimp(m,ms));
                }
                mts.add(md);
            }
        }

        return mts;
    }

    /**
     * 获得盾构机的数量
     * @return
     */
    public long findMachineCountByType(int parent){
        return RouteQuery.me().findeCountByparentAndType(parent,JRoute.MACHINE);
    }
    //获取工点下的盾构机
    public List<JRoute> findRoutesByParent(Integer page, Integer limit, Integer parent, long count){
        List<JRoute> routeList=new ArrayList<>();
        if(count!=0){
            page = (page>count/limit && count%limit==0)?page-1:page ;
            routeList = RouteQuery.me().findRoutesByParent(page, limit, parent);
        }
        return routeList;
    }
}
