package com.base.admin.controller;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.OptionService;
import com.base.query.OptionQuery;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.ArrayList;
import java.util.List;

import com.base.constants.MagicValueConstants;
import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.NewButtonInterceptor;
import com.base.model.JOption;
/**
 *
 * All rights Reserved, Designed By hxjd
 * @类名: OptionController.java
 * @包名: com.base.web.controller.admin
 * @描述: 用户配置参数的相关操作
 * @所属: 华夏九鼎
 * @日期: 2017年5月15日 上午9:36:05
 * @版本: V1.0
 * @创建人：z
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/option", viewPath = "/view/admin/option")
@RouterNotAllowConvert
public class OptionController extends BaseController {

	@Before(NewButtonInterceptor.class)
	public void index() {
		renderTable("option.html");
	}

	public void optionData() {
		Integer page = getParaToInt("page");
		Integer limit = getParaToInt("limit");
		String where = getPara("id");
		
		long count = OptionService.me().findConunt(where);
		List<Record> list = OptionService.me().findListOptions(page, limit, where, count);
		renderPageResult(0, "", (int)count, list);
		
	}

	public void details() {
		Integer id = getParaToInt("id");
		JOption option = OptionService.me().findById(id);
		if(MagicValueConstants.MAP_CENTER.equals(option.getOptionKey())){
			String optionXY =option.getOptionValue();
			String[] optionXYS = optionXY.split("\\|");
			setAttr("optionX", optionXYS[0]);
			setAttr("optionY", optionXYS[1]);
		}

		setAttr("option", option);
		render("details.html");
	}
	public void add() {
		render("add.html");
	}
	public void addSave() {
		JOption jo = getModel(JOption.class);
		boolean a = OptionService.me().addSave(jo);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		JOption option = OptionService.me().findById(id);
		if(MagicValueConstants.MAP_CENTER.equals(option.getOptionKey())){
			String optionXY =option.getOptionValue();
			String[] optionXYS = optionXY.split("\\|");
			setAttr("optionX", optionXYS[0]);
			setAttr("optionY", optionXYS[1]);
		}

		setAttr("option", option);

		render("edit.html");
	}
	public void editSave() {
		JOption jo = getModel(JOption.class);
		String optionX = getPara("optionX");
		String optionY = getPara("optionY");		
		boolean a = OptionService.me().editSave(jo, optionX, optionY);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
	}
	public void del() {
		Integer id = getParaToInt("id");
		boolean a = OptionService.me().del(id);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
	}
}
