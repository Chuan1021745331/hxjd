package com.base.admin.controller;

import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.TbmrepairSearchDto;
import com.base.query.CircuitQuery;
import com.base.query.WorkSiteQuery;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.CameraService;
import com.base.service.TbmService;
import com.base.service.TbmrepairService;
import com.base.service.UserService;
import com.base.utils.CookieUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.xiaoleilu.hutool.date.DateTime;

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
        String where = getPara("keyword");

        long count = TbmrepairService.me().findCountTbmrepairTbmUser(where);
        List<Record> list = TbmrepairService.me().findListTbmrepairTbmUser(page, limit, where, count);
        renderPageResult(0,"",(int)count,list);
    }

    public void tbmrepairDataByTbmId() {
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        Integer id=getParaToInt("tbmId");
        long count = TbmrepairService.me().findCountTbmrepairByTbmId(id);
        List<Record> tbmrepair = TbmrepairService.me().findListTbmrepairByTbmId(page, limit, id, count);
        renderPageResult(0,"",(int)count,tbmrepair);
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

        //盾构机详情
        JTbm tbm = TbmService.me().findTbmById(tbmrepair.getTbmId());
        JWorksite worksite=null;
        JCircuit circuit=null;
        if(tbm!=null){
            worksite = WorkSiteQuery.me().findById(tbm.getWorksiteid());
            circuit = CircuitQuery.me().findById(worksite.getCircuitid());
        }else{
            tbm=new JTbm();
        }
        if(worksite==null)
            worksite=new JWorksite();
        if(circuit==null)
            circuit=new JCircuit();

        setAttr("tbmrepair",tbmrepair);
        setAttr("tbm",tbm);
        setAttr("worksite",worksite);
        setAttr("circuit",circuit);
        render("edit.html");
    }

    public void addSave(){
        JTbmrepair tbmrepair = getModel(JTbmrepair.class);

        tbmrepair.setCreatetime(DateTime.now());
        //补全盾构机信息
        JTbm tbm = TbmService.me().findTbmById(tbmrepair.getTbmId());
        TbmrepairService.me().addTbmInfo(tbmrepair,tbm);

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

    public void search(){
        render("search.html");
    }

    public void searchData(){
        Integer page = getParaToInt("page");
        Integer limit = getParaToInt("limit");
        TbmrepairSearchDto searchDto = getBean(TbmrepairSearchDto.class,"tbmrepairSearchDto");

        long count = TbmrepairService.me().findCountTbmrepairTbmUser(searchDto);
        List<Record> list = TbmrepairService.me().findListTbmrepairTbmUser(page, limit, searchDto, count);
        renderPageResult(0,"",(int)count,list);
    }

}
