package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.core.BaseController;
import com.base.im.common.SenMsg;
import com.base.interceptor.NewButtonInterceptor;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.jfinal.aop.Before;
import org.tio.core.Aio;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @类名: ${CLASS_NAME}
 * @包名: com.base.admin.controller
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @日期: 2018/1/10 10:31
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 */
@RouterMapping(url = "/admin/camera", viewPath = "/view/admin/camera")
@RouterNotAllowConvert
public class CameraController extends BaseController {
    @Before(NewButtonInterceptor.class)
    public void index() {
        /*try {
            SenMsg.sendMsg(1,"1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        List<String> rtmpList = new ArrayList<>();
        rtmpList.add("rtmp://120.78.49.241:1935/live/test1");
        rtmpList.add("rtmp://120.78.49.241:1935/live/test2");
        String rtmps = JSON.toJSONString(rtmpList);
        setAttr("rtmps",rtmps);
        renderTable("camera.html");
    }
}
