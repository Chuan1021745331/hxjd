package com.base.web.controller.web;

import com.base.core.BaseController;
import com.base.model.JCircuit;
import com.base.model.dto.CircuitWorksiteDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.CircuitService;

import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.web.controller.web
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/19 16:14
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
@RouterMapping(url = "/camera", viewPath = "/view/")
@RouterNotAllowConvert
public class CameraController extends BaseController {

    public void index(){
        List<CircuitWorksiteDto> circuitWorksiteDtos = CircuitService.me().getCircuitWorksiteDtos();
        setAttr("circuitWorksiteDtos",circuitWorksiteDtos);
        render("index.html");
    }
}
