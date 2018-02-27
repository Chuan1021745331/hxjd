package com.base.web.controller.web;

import com.base.core.BaseController;
import com.base.model.JCircuit;
import com.base.query.CircuitQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.TbmService;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmMonitorController
 * @包名: com.base.web.controller.web
 * @描述: (用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2018/2/27 10:02
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/tbmmonitor", viewPath = "/view/web/tbmmonitor")
@RouterNotAllowConvert
public class TbmMonitorController extends BaseController {
    public void index(){
        List<JCircuit> circuitList = CircuitQuery.me().getAllCircuit();
        setAttr("circuitList",circuitList);
        render("tbmmonitor.html");
    }
    
    public void getAllTbm(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        long allTbmCount = TbmService.me().findAllTbmCount();
        List<Record> tbmList = TbmService.me().findAllTbmWorksite(page, limit, allTbmCount);
        renderAjaxResult("ok",0,tbmList);
    }

    public void getTbmByCircuitId(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Integer circuitId = getParaToInt("id");
        long count = TbmService.me().findTbmCountByCircuitId(circuitId);
        List<Record> tbmList = TbmService.me().findTbmTbmrepairByCircuitId(page, limit, circuitId, count);
        renderAjaxResult("ok",0,tbmList);
    }

    public void tbmMain(){
        render("tbmMain.html");
    }
}
