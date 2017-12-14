package com.base.admin.controller;

import com.alibaba.fastjson.JSON;
import com.base.core.BaseController;
import com.base.interceptor.ButtonInterceptor;
import com.base.model.JDrillcode;
import com.base.model.JGrademark;
import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.DrillcodeQuery;
import com.base.service.GradeMarkQuery;
import com.base.utils.ChartData;
import com.jfinal.aop.Before;
import com.jfinal.log.Log;

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
@RouterMapping(url = "/admin/gradeMark", viewPath = "/view/admin/kpi")
@RouterNotAllowConvert
public class GradeMarkController extends BaseController {
	private Log log = Log.getLog(GradeMarkController.class);
	List<Integer> endChildNode = new ArrayList<>();

	@Before(ButtonInterceptor.class)
	public void index() {
		List<JDrillcode> jDrillcodes = DrillcodeQuery.me().getAll();
		for (JDrillcode jd : jDrillcodes){
			jd.setName(jd.getName()+"（"+jd.getTime()+"）");
		}
		setAttr("jDrillcodes",jDrillcodes);
		render("chart.html");
	}

	/**
	 *根据训练代号查询评分详情
	 */
	public void chooseDrill() {
		Double redTotal= 0.00;
		Double blueTotal = 0.00;
		List<Integer> id;
		ChartData chartData = new ChartData();
		String drillName = getPara("drill");
		Integer parentId = getParaToInt("parentId");
		if (parentId == null){
			id = GradeMarkQuery.me().findIdByparentId(0);
		}else {
			id = GradeMarkQuery.me().findIdByparentId(parentId);
		}
		if (id != null){
			for (Integer in: id){
				String name = GradeMarkQuery.me().findById(in).getName();
				redTotal = totalScore(in,"0",drillName);
				blueTotal = totalScore(in,"1",drillName);
				chartData.getName().add(name);
				chartData.getRedScore().add(redTotal);
				chartData.getBlueScore().add(blueTotal);
				chartData.getId().add(in);
			}
		}
		renderJson(chartData);
	}

	/**
	 * 根据ID获取全部叶子节点的分数
	 * @param nodeId
	 * @param camp
	 * @param drillName
	 * @return
	 */
	public Double totalScore(Integer nodeId,String camp,String drillName) {
		Double total = 0.00;
//		List<Integer> allEndNodeId = getEndChildNode(nodeId);
		endChildNode.clear();
		getEndChildNode(nodeId);
		List<Integer> allEndNodeId = endChildNode;
		for (Integer in: allEndNodeId){
			JGrademark existDrill = GradeMarkQuery.me().findByCampAndDrillName(in,camp,drillName);
			if (existDrill != null){
				total += Double.valueOf( existDrill.getScore());
			}
		}
		return total;
	}

	/**
	 * 获取节点下的全部子叶节点
	 * @param nodeId
	 * @return
	 */
		public void  getEndChildNode(Integer nodeId){
		List<Integer> listId = GradeMarkQuery.me().findIdByparentId(nodeId);
		if (listId != null){
			for (Integer in: listId){
				List<Integer> listIdSon = GradeMarkQuery.me().findIdByparentId(in);
				if (listIdSon != null){
					getEndChildNode(in);
				}else {
					endChildNode.add(in);
				}
			}
		}else {
			endChildNode.add(nodeId);
		}
	}

	/**
	 * 判断下一子叶是否为用户
	 */
	public void isNode(){
		Boolean isNode = false;
		Integer id = getParaToInt("id");
		List<Integer> listChild = GradeMarkQuery.me().findIdByparentId(id);
		for (Integer in:listChild){
			JGrademark jGrademark = GradeMarkQuery.me().findById(in);
			String score = jGrademark.getScore();
			String drill = jGrademark.getDrillname();
			if (score != null&&drill != null){
				isNode = true;
				break;
			}else {
				isNode = false;
			}
		}
		renderJson(JSON.toJSON(isNode));
	}

	/**
	 * 页面跳转
	 */
	public void skipDetails(){
		String title = getPara("title");
		String endTitle = title.replaceAll(",",">>");
		Integer id = getParaToInt("id");
		setAttr("id",id);
		setAttr("title",endTitle);
		render("scoreDetails.html");
	}

	/**
	 * 分数详情
	 */
	public void selectDetails() {
		Integer parentId = getParaToInt("id");
		Integer draw = getParaToInt("draw");
		Integer start = getParaToInt("start");
		Integer length = getParaToInt("length");
		Integer column = getParaToInt("order[0][column]");
		String order = getPara("order[0][dir]");
		String search = getPara("search[value]");
		String parentName = GradeMarkQuery.me().findById(parentId).getName();
		List<JGrademark> jGrademarks = GradeMarkQuery.me().findScoreDetails(parentId);
		for (JGrademark jg: jGrademarks){
			jg.setMark(parentName);
		}
		long count = jGrademarks.size();
		renderPageResult(draw, (int)count, (int)count, jGrademarks);
	}

	/**
	 *
	 *查父类的信息
	 */
	public void selectParent(){
		Integer id = getParaToInt("id");
		JGrademark parent;
		Integer parentId = GradeMarkQuery.me().findById(id).getGradeParentId();
		if (!parentId.equals(0)){
			parent = GradeMarkQuery.me().findById(parentId);
		}else {
			parent = null;
		}
		renderJson(JSON.toJSON(parent));
	}

}
