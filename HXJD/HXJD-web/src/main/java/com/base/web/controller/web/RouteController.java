package com.base.web.controller.web;

import com.alibaba.fastjson.JSON;
import com.base.core.BaseController;
import com.base.model.JCamera;
import com.base.model.dto.CircuitWorksiteDto;
import com.base.model.dto.WorkSiteDto2;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.CameraService;
import com.base.service.CircuitService;
import com.base.service.WorkSiteService;

import java.util.List;

/**
 * @类名: RouteController.class
 * @包名: com.base.web.controller.web
 * @描述: 线路管理控制器
 * @日期: 2018/1/26 11:07
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
@RouterMapping(url = "/route", viewPath = "/view/web/route")
@RouterNotAllowConvert
public class RouteController extends BaseController {
    /**
     * 左侧菜单线路
     */
    public void index(){
        int cid = getParaToInt("cid");
        List<CircuitWorksiteDto> circuitWorksiteDtos = CircuitService.me().getCircuitWorksiteDtos();
        String json = JSON.toJSONString(circuitWorksiteDtos);
        CircuitWorksiteDto circuitWorksiteDto = getByList(cid,circuitWorksiteDtos);
        setAttr("circuitWorksiteDto",circuitWorksiteDto);
        setAttr("dtos",circuitWorksiteDtos);
        setAttr("circuitWorksiteDtos",json);
        render("circuitSel.html");
    }

    /**
     * 通过id从线路集合中获取线路
     * @param id
     * @param circuitWorksiteDtos
     * @return
     */
    private CircuitWorksiteDto getByList(int id,List<CircuitWorksiteDto> circuitWorksiteDtos){
        for(CircuitWorksiteDto circuitWorksiteDto:circuitWorksiteDtos){
            if(circuitWorksiteDto.getId() == id){
                return circuitWorksiteDto;
            }
        }
        return null;
    }

    /**
     * 进入工点
     */
    public void workSiteSel(){
        int wid = getParaToInt("wid");
        WorkSiteDto2 workSiteDto2 = WorkSiteService.me().getWorkSiteAndTbmAndCamera(wid);
        setAttr("worksite",workSiteDto2);
        render("worksiteSel.html");
    }
    /**
     * 进入盾构机页面
     */
    public void main2(){
        Integer tid = getParaToInt("tid");
        setAttr("tbmid",tid);
        render("main2.html");
    }

    /**
     * 摄像头
     */
    public void camera(){
        Integer tid = getParaToInt("tid");
        List<JCamera> cameras = CameraService.me().findCamerasByTbmId(tid);
        String json = JSON.toJSONString(cameras);

        setAttr("tid",tid);
        setAttr("cameras",cameras);
        setAttr("camerasjson",json);
        render("camera.html");
    }
}
