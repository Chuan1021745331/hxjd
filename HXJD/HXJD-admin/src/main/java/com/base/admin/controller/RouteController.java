package com.base.admin.controller;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JCircuit;
import com.base.model.JRoute;
import com.base.model.JWorksite;
import com.base.model.dto.MenuSimpDto;
import com.base.model.dto.TreeSimpDto;
import com.base.query.CircuitQuery;
import com.base.query.RouteQuery;
import com.base.query.WorkSiteQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.CircuitService;
import com.base.service.MenuService;
import com.base.service.RouteService;
import com.base.service.WorkSiteService;
import com.jfinal.aop.Before;

import java.util.List;

/**
 * @类名: RouteController
 * @包名: com.base.admin.controller
 * @描述: 线路管理
 * @日期: 2018/1/17 9:53
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
@RouterMapping(url = "/admin/route", viewPath = "/view/admin/route")
@RouterNotAllowConvert
public class RouteController extends BaseController {
    @Before(NewButtonInterceptor.class)
    public void index(){
        renderTable("route.html");
    }
    public void tree(){
        List<TreeSimpDto> m = RouteService.me().getRouteSimp();
        renderJson(m);
    }

    /**
     * 新增线路
     */
    public void addCircuit(){
        String pid = getPara("pId");

        render("circuitAdd.html");
    }
    public void saveAndUpdateCicuit(){
        JCircuit model = getModel(JCircuit.class);
        JCircuit circuit = CircuitService.me().saveAndUpdateCircuit(model);
        if(null != circuit){
            renderAjaxResult("",0,circuit);
        }else{
            renderAjaxResultForError();
        }
    }
    /*
    * 修改线路
    * */
    public void editCicuit(){
        String id = getPara("id");
        JCircuit circuit = CircuitQuery.me().findById(Integer.parseInt(id));
        setAttr("route",circuit);
        renderTable("circuitEdit.html");
    }

    /**
     * 新增工点
     */
    public void addWorkSite(){
        String pid = getPara("pId");
        JCircuit circuit = CircuitQuery.me().findById(Integer.parseInt(pid));
        this.setAttr("route",circuit);
        render("workSiteAdd.html");
    }
    public void saveAndUpdateWorksite(){
        JWorksite model = getModel(JWorksite.class);
        JWorksite worksite = WorkSiteService.me().saveAndUpdateWorksite(model);
        if(null != worksite){
            renderAjaxResult("",0,worksite);
        }else{
            renderAjaxResultForError();
        }
    }

    /**
     * 修改工点
     */
    public void editWorksite(){
        String id = getPara("id");
        JWorksite worksite = WorkSiteQuery.me().findById(Integer.parseInt(id));
        JCircuit circuit = CircuitQuery.me().findById(worksite.getCircuitid());
        setAttr("route",worksite);
        setAttr("circuit",circuit);
        renderTable("worksiteEdit.html");
    }


    public void addD(){
        String pid = getPara("pId");
        JRoute route = RouteQuery.me().findById(Integer.parseInt(pid));
        if(null == route){
            route=new JRoute();
            route.setType(-1);
            route.setId(0);
            route.setName("根节点");
        }
        this.setAttr("route",route);
        render("routeAdd.html");
    }
    public void saveAndUpdateRoute(){
        JRoute model = getModel(JRoute.class);
        JRoute route = RouteService.me().saveAndUpdateRoute(model);
        if(null != route){
            renderAjaxResult("",0,route);
        }else{
            renderAjaxResultForError();
        }
    }
    public void editD(){
        String id = getPara("id");
        JRoute route = RouteService.me().findById(Integer.parseInt(id));
        JRoute proute = RouteService.me().findById(route.getParent());
        if(proute == null){
            proute = new JRoute();
            proute.setId(0);
            proute.setName("根节点");
        }
        setAttr("route",route);
        setAttr("proute",proute);
        renderTable("routeEdit.html");
    }
    public void machineData(){
        Integer routeId = getParaToInt("menuId");
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        //默认为根节点
        if(null == routeId){
            routeId = new Integer(0);
        }

        long count = RouteService.me().findMachineCountByType(routeId);
        List<JRoute> routeList = RouteService.me().findRoutesByParent(page, limit, routeId, count);
        renderPageResult(0, "", (int)count, routeList);
    }
}
