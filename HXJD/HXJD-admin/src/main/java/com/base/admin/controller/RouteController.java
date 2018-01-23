package com.base.admin.controller;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.CircuitWorksiteDto;
import com.base.model.dto.MenuSimpDto;
import com.base.model.dto.TreeSimpDto;
import com.base.query.CircuitQuery;
import com.base.query.RouteQuery;
import com.base.query.TbmQuery;
import com.base.query.WorkSiteQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.*;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;

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

    /**
     * 分页查询盾构机
     */
    public void tbmData(){
        Integer workSiteId = getParaToInt("menuId");
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        //默认为根节点
        if(null == workSiteId){
            workSiteId = new Integer(0);
        }

        long count = TbmService.me().findTbmCountByWorkSiteId(workSiteId);
        List<Record> tbmList = TbmService.me().findTbmTbmrepairByworkSiteId(page, limit, workSiteId, count);
        renderPageResult(0, "", (int)count, tbmList);
    }

    /**
     * 添加盾构机
     */
    public void addTbm(){
        String pid = getPara("workSiteId");
        JWorksite worksite = WorkSiteQuery.me().findById(Integer.parseInt(pid));
        this.setAttr("worksite",worksite);
        render("machineAdd.html");
    }

    /**
     * 保存或更新盾构机
     */
    public void saveAndUpdateTbm(){
        JTbm model = getModel(JTbm.class);
        JTbm tbm = TbmService.me().saveAndUpdateTbm(model);
        if(null != tbm){
            renderAjaxResult("",0,tbm);
        }else{
            renderAjaxResultForError();
        }
    }

    /**
     * 编辑盾构机信息
     */
    public void tbmEdit(){
        String id = getPara("id");
        int tid = Integer.parseInt(id);
        JTbm tbm = TbmQuery.me().findTbmById(tid);
        JWorksite worksite = WorkSiteQuery.me().findById(tbm.getWorksiteid());
        setAttr("worksite",worksite);
        setAttr("tbm",tbm);
        renderTable("machineEdit.html");
    }

    /**
     * 查看盾构机详细信息
     */
    public void selTbm(){
        Integer id = getParaToInt("id");
        JTbm tbm = TbmQuery.me().findTbmById(id);
        JWorksite worksite = WorkSiteQuery.me().findById(tbm.getWorksiteid());
        JCircuit circuit = CircuitQuery.me().findById(worksite.getCircuitid());
        List<JCamera> cameras = CameraService.me().findCamerasByTbmId(id);
        JTbmrepair tbmrepair = TbmrepairService.me().findLatestByTbmId(id);
        if(null==tbmrepair)
            tbmrepair=new JTbmrepair();
        setAttr("worksite",worksite);
        setAttr("circuit",circuit);
        setAttr("tbm",tbm);
        setAttr("cameras",cameras);
        setAttr("tbmrepair",tbmrepair);

        renderTable("machineSel.html");
    }

    /**
     * 删除盾构机信息
     */
    public void delTbm(){
        Integer id = getParaToInt("id");
        JTbm tbm = TbmQuery.me().findTbmById(id);
        boolean canDel = TbmService.me().isCanDel(id);
        //如果不能删除
        if(!canDel){
            renderAjaxResultForError(MessageConstants.TBM__DEL_DEFEAT);
            return ;
        }
        boolean flag = tbm.delete();
        if(flag){
            renderAjaxResultForSuccess("删除成功");
        }else{
            renderAjaxResultForError("删除失败");
        }
    }

    /**
     * 删除节点
     */
    public void delTreeNode(){
        Integer id = getParaToInt("id");
        Integer level = getParaToInt("level");
        boolean canDel = false;
        boolean isDel = false;
        if(level == 1){
            //判断线路能不能删除
            canDel = CircuitService.me().isCanDel(id);
            if (canDel) {
                isDel = CircuitService.me().delCircuitById(id);
            }
        }
        if(level == 2){
            //判断工点能不能删除
            canDel = WorkSiteService.me().isCanDel(id);
            if (canDel) {
                isDel = WorkSiteService.me().delWorkSiteById(id);
            }
        }

        //不能删除
        if(!canDel){
            renderAjaxResultForError(MessageConstants.ROUTE__DEL_DEFEAT);
            return;
        }
        if(isDel){
            renderAjaxResultForSuccess();
        }else{
            renderAjaxResultForError();
        }
    }

    /**
     * 添加或者修改摄像头
     */
    public void addAndUpdateCamera(){
        JCamera model = getModel(JCamera.class);
        JCamera camera = CameraService.me().saveAndUpdateCamera(model);
        if(null != camera){
            renderAjaxResult("",0,camera);
        }else{
            renderAjaxResultForError();
        }
    }

    /**
     * 删除摄像头
     */
    public void delCamera(){
        Integer cid = getParaToInt("cid");
        boolean isDel = CameraService.me().delCameraById(cid);
        if(isDel){
            renderAjaxResultForSuccess();
        }else {
            renderAjaxResultForError();
        }
    }

    public void getAllCircuit(){
        List<CircuitWorksiteDto> circuitWorksiteDtos = CircuitService.me().getCircuitWorksiteDtos();
        renderAjaxResult("",0,circuitWorksiteDtos);
    }

    public void getTbmByWorkSite(){
        Integer worksiteId = getParaToInt("worksiteId");
        List<JTbm> tbms = TbmService.me().findTbmByWorkSiteId(worksiteId);
        renderAjaxResult("",0,tbms);
    }

    /**
     * 查看盾构机维修记录
     */
    @Before(NewButtonInterceptor.class)
    public void tbmrepair(){
        Integer id = getParaToInt("id");
        JTbm tbm = TbmQuery.me().findTbmById(id);
        setAttr("tbm",tbm);
        renderTable("tbmrepair.html");
    }
}
