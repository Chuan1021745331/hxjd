package com.base.admin.controller;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JTbmrepair;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.TbmrepairService;
import com.base.utils.CookieUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TbmrepairController
 * @包名: com.base.admin.controller
 * @描述: (盾构机维修)
 * @所属: 华夏九鼎
 * @日期: 2018/1/22 10:40
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/tbmrepair", viewPath = "/view/admin/tbmrepair")
@RouterNotAllowConvert
public class TbmrepairController extends BaseController {
    @Before(NewButtonInterceptor.class)
    public void index(){
        renderTable("tbmrepair.html");
    }

    public void tbmrepairData(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        String where = getPara("id");
        long count = TbmrepairService.me().findCountTbmrepairTbmUser();
        List<Record> list = TbmrepairService.me().findListTbmrepairTbmUser(page, limit, where, count);
        renderPageResult(0,"",(int)count,list);
    }

    public void del(){
        Integer id = getParaToInt("id");
        boolean b = TbmrepairService.me().delByTbmrepairId(id);
        if(b){
            renderAjaxResultForSuccess();
        }else{
            renderAjaxResultForError();
        }
    }

    public void add(){
        render("add.html");
    }

    public void edit(){
        Integer id = getParaToInt("id");
        JTbmrepair tbmrepair = TbmrepairService.me().findTbmrepairByTbmrepairId(id);
        setAttr("tbmrepair",tbmrepair);
        render("edit.html");
    }

    public void addSave(){
        JTbmrepair tbmrepair = getModel(JTbmrepair.class);
        boolean b = tbmrepair.save();
        if(b){
            renderAjaxResultForSuccess("添加成功");
        }else{
            renderAjaxResultForError("添加失败");
        }
    }

    public void editSave(){
        JTbmrepair tbmrepair = getModel(JTbmrepair.class);
        boolean b = TbmrepairService.me().editTbmrepairSave(tbmrepair);
        if(b){
            renderAjaxResultForSuccess();
        }else{
            renderAjaxResultForError();
        }
    }

}