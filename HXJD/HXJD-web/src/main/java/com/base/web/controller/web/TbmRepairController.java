package com.base.web.controller.web;

import com.base.core.BaseController;
import com.base.model.JTbm;
import com.base.model.JTbmrepair;
import com.base.model.JUser;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.TbmService;
import com.base.service.TbmrepairService;
import com.base.service.UserService;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmRepairController
 * @包名: com.base.web.controller.web
 * @描述: 维保记录
 * @所属: 华夏九鼎
 * @日期: 2018/1/29 11:39
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */

@RouterMapping(url = "/web/tbmrepair", viewPath = "view/web/tbmrepair")
@RouterNotAllowConvert
public class TbmRepairController extends BaseController{
    public void tbmrepairData(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        String where = getPara("keyword");

        long count = TbmrepairService.me().findCountTbmrepairTbmUser(where);
        List<Record> list = TbmrepairService.me().findListTbmrepairTbmUser(page, limit, where, count);
        renderPageResult(0,"",(int)count,list);
    }

    public void sel(){
        Integer id = getParaToInt("id");
        JTbmrepair tbmrepair = TbmrepairService.me().findTbmrepairByTbmrepairId(id);
        JUser user= UserService.me().findById(tbmrepair.getUserId());

        //盾构机详情
        JTbm tbm = TbmService.me().findTbmById(tbmrepair.getTbmId());

        setAttr("tbmrepair",tbmrepair);
        setAttr("user",user);
        setAttr("tbm",tbm);
        render("sel.html");
    }
}
