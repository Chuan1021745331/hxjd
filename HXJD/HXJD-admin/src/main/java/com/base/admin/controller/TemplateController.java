package com.base.admin.controller;

import com.base.constants.TemplateConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JTemplate;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.TemplateService;
import com.jfinal.aop.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: TemplateController
 * @包名: com.base.admin.controller
 * @描述: (用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2018/3/20 13:53
 * @版本: V1.0
 * @创建人：JC
 * @修改人：JC
 * @版权: 2018 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/template", viewPath = "/view/admin/template/")
@RouterNotAllowConvert
public class TemplateController extends BaseController{
    private static final Logger log= LoggerFactory.getLogger(TemplateController.class);
    @Before(NewButtonInterceptor.class)
    public void index(){
        renderTable("template.html");
    }

    /**
     * 获取模板数据
     */
    public void templateData(){
        Integer page=1;
        Integer limit=0;
        try{
            page = getParaToInt("page");
        }catch (NumberFormatException e){
            log.error("参数转数字错误");
        }
        try{
            limit = getParaToInt("limit");
        }catch (NumberFormatException e){
            log.error("参数转数字错误");
            limit=10;
        }
        String where = getPara("where");
        long count = TemplateService.me().findTemplateWhere(where);
        List<JTemplate> templateList = TemplateService.me().findAllTemplateWherePage(page, limit, where, count);
        renderPageResult(0,"SUCCESS",(int)count,templateList);
    }

    /**
     * 进入添加页面
     */
    public void add(){
        render("add.html");
    }

    /**
     * 新增模板保存
     */
    public void addSave(){
        JTemplate template = getModel(JTemplate.class);
        boolean isSave = template.save();
        if(isSave){
            renderAjaxResultForSuccess(TemplateConstants.SUCCESS_ADDTEMPLATE);
        }else{
            renderAjaxResultForError(TemplateConstants.ERROR_ADDTEMPLATE);
        }
    }

    /**
     *模板修改
     */
    public void edit(){
        try{
            Integer id = getParaToInt("id");
            JTemplate template = TemplateService.me().findTemplateById(id);
            setAttr("template",template);
        }catch (NumberFormatException e){
            renderAjaxResultForError("");
            return ;
        }
        render("edit.html");
    }

    /**
     * 模板修改保存
     */
    public void editSave(){
        JTemplate template = getModel(JTemplate.class);
        boolean isEdit = template.saveOrUpdate();
        if(isEdit){
            renderAjaxResultForSuccess(TemplateConstants.SUCCESS_EDITTEMPLATE);
        }else{
            renderAjaxResultForError(TemplateConstants.ERROR_EDITTEMPLATE);
        }
    }

    /**
     * 模板详情
     */
    public void sel(){
        try{
            Integer id = getParaToInt("id");
            JTemplate template = TemplateService.me().findTemplateById(id);
            setAttr("template",template);
        }catch (NumberFormatException e){
            renderAjaxResultForError(TemplateConstants.ERROR_PARAM);
            return ;
        }
        render("sel.html");
    }

    public void del(){
        try{
            Integer id = getParaToInt("id");
            boolean isDelete = TemplateService.me().deleteById(id);
            if(isDelete){
                renderAjaxResultForSuccess(TemplateConstants.SUCCESS_DELETETEMPLATE);
            }else{
                renderAjaxResultForError(TemplateConstants.ERROR_DELETETEMPLATE);
            }
        }catch (NumberFormatException e){
            renderAjaxResultForError(TemplateConstants.ERROR_PARAM);
        }
    }

    /**
     * 根据id获取模板数据
     */
    public void getTemplate(){
        Integer id = getParaToInt("id");
        JTemplate template = TemplateService.me().findTemplateById(id);
        setAttr("template",template);
        render("page.html");
    }

    /**
     * 获取所有模板
     */
    public void getAllTemplates(){
        List<JTemplate> allTemplate = TemplateService.me().findAllTemplate();
        renderAjaxResult("",0,allTemplate);
    }
}
