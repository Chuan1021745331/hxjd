package com.base.web.controller.admin;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.*;
import com.base.model.dto.JkpiDto;
import com.base.model.dto.KpiObjDto;
import com.base.model.dto.MenuSimpDto;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.ButtonQuery;
import com.base.service.KpiQuery;
import com.base.service.MenuQuery;
import com.base.service.ObjectsQuery;
import com.base.utils.IsIntUtils;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.log.Log;
import com.jfinal.plugin.ehcache.CacheKit;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * All rights Reserved, Designed By hxjd
 * @类名: MenuController.java   
 * @包名: com.base.web.controller.admin   
 * @描述: 评分管理
 * @所属: 华夏九鼎     
 * @日期: 2017年5月127日 上午9:37:27
 * @版本: V1.0 
 * @创建人：ziv
 * @修改人：ziv
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
@RouterMapping(url = "/admin/kpi", viewPath = "/view/admin/kpi")
@RouterNotAllowConvert
public class KpiController extends BaseController {
	private Log log = Log.getLog(KpiController.class);
	List<Integer> scoreid = new ArrayList<>();

	@Before(ButtonInterceptor.class)
	public void index() {
		render("kpi.html");
	}
	
	public void tree() {
		List<MenuSimpDto> b = KpiQuery.me().getMenusSimp();
		renderJson(KpiQuery.me().getMenusSimp());
	}
	
	public void addTree() throws UnsupportedEncodingException {
		Integer pId = getParaToInt("pId");
		String name = getPara("name");
		String rename = new String(name.getBytes("ISO-8859-1"),"utf-8");

		JKpi menu = new JKpi();
		menu.setParentId(pId);
		menu.setName(rename);
		boolean b = menu.save();
	/*	CacheKit.removeAll("kpi");*/
		if(b){
			/*this.JButtonE(menu.getId(), "新增", "add");
			this.JButtonE(menu.getId(), "删除", "del");
			this.JButtonE(menu.getId(), "修改", "edit");
			this.JButtonE(menu.getId(), "详情", "sel");*/
			renderJson(menu);
		}else{
			renderAjaxResultForError();
		}
	}

	public void editTree() {
		try {
			Integer id = getParaToInt("id");
			Integer parent = getParaToInt("parentId");
			String name = getPara("name");
			JKpi menu = KpiQuery.me().findById(id);
			if (getPara("score") != null){
				String score = getPara("score");
				menu.setScore(Double.valueOf(score));
			}
			menu.setParentId(parent);
			menu.setName(name);

			boolean b = menu.saveOrUpdate();
			if(b){
				renderAjaxResult("", 0, menu);
			}else{
				renderAjaxResultForError(MessageConstants.EDIT_TREE_DEFEAT);
			}
		} catch (Exception e) {
			renderAjaxResultForError(MessageConstants.EDIT_TREE_NONE_PARENT);
		}
		
	}

	public void delTree() {
		Integer id = getParaToInt("id");
		JKpi menu = KpiQuery.me().findById(id);
		List<JKpi> menuList = KpiQuery.me().findByParent(id);
		if(menuList.size()>0){
			for (JKpi m:menuList) {
				m.delete();
			}
		}
		boolean b = menu.delete();
		if(b){
			renderAjaxResultForSuccess();
		}else{
			renderAjaxResultForError();
		}
	}

	//递归算总分
	public List<Integer> totalScore(Integer id){
		List<Integer> listId = KpiQuery.me().findByScore(id);
		if (listId != null){
			for(Integer integer : listId){
				if (KpiQuery.me().findByScore(integer) == null){
					scoreid.add(integer);
				}
				else {
					totalScore(integer);
				}
			}
		}
		return listId;
	}

	//获取单个评分的详情
	public void getMenu(){
		Double total = 0.00;
		Integer id = getParaToInt("id");
		JKpi jKpi = KpiQuery.me().findById(id);
		KpiObjDto kpiObjDto = new KpiObjDto();
		if (jKpi != null){
			String str = jKpi.getName();
			boolean isInt;
			isInt = IsIntUtils.isNumeric(str);
			if (isInt){
				JObject object2 = ObjectsQuery.me().findById(Integer.valueOf(jKpi.getName()));
				String name = ObjectsQuery.me().findById(Integer.valueOf(jKpi.getName())).getName();
				jKpi.setName(name);
			}
			totalScore(id);
			//获取总分
			for(Integer i : scoreid){
				if ( KpiQuery.me().findById(i).getScore() != null)
					total += KpiQuery.me().findById(i).getScore();
			}
			jKpi.setTotal(String.valueOf(total));
			kpiObjDto = new KpiObjDto();
			kpiObjDto.setM(jKpi);
			//获取全部对象
			List<JObject> jObject = ObjectsQuery.me().getAll();
			//获取父类的名称
			if (jKpi.getParentId()==0){
				jKpi.setParentname("评分系统");
			}else {
				String parentName = KpiQuery.me().findById(jKpi.getParentId()).getName();
				boolean isInts = false;
				isInts = IsIntUtils.isNumeric(parentName);
				if (isInts){
					JObject object = ObjectsQuery.me().findById(Integer.valueOf(parentName));
					parentName = /*object.getGrade()+"--"+*/object.getName();
				}
				jKpi.setParentname(parentName);
			}
			kpiObjDto.setObjects(jObject);
		}
		renderJson(kpiObjDto);
	}
}
