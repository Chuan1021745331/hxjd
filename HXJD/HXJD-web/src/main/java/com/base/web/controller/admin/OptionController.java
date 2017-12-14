package com.base.web.controller.admin;

import com.base.router.RouterMapping;
import com.base.router.RouterNotAllowConvert;
import com.base.service.OptionQuery;

import com.jfinal.aop.Before;
import com.jfinal.plugin.ehcache.CacheKit;

import java.util.List;

import com.base.constants.MessageConstants;
import com.base.core.BaseController;
import com.base.im.client.IMClientStarter2;
import com.base.interceptor.ButtonInterceptor;
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

	@Before(ButtonInterceptor.class)
	public void index() {
		render("option.html");
	}

	public void optionData() {
		Integer draw = getParaToInt("draw");
		Integer start = getParaToInt("start");
		Integer length = getParaToInt("length");
		Integer column = getParaToInt("order[0][column]");
		String order = getPara("order[0][dir]");
		String search = getPara("search[value]");

		List<JOption> list = OptionQuery.me().findList(start, length,column,order,search);
		long count = OptionQuery.me().findConunt(search);
		renderPageResult(draw, (int)count, (int)count, list);
	}

	public void details() {
		Integer id = getParaToInt("id");
		JOption option = OptionQuery.me().findById(id);
		if(option.getOptionKey().equals("map_center")){
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
		boolean a = jo.save();
		if(a){
			renderAjaxResultForSuccess(MessageConstants.ADD_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.ADD_DEFEAT);
	}

	public void edit() {
		Integer id = getParaToInt("id");
		JOption option = OptionQuery.me().findById(id);
		if(option.getOptionKey().equals("map_center")){
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
		if(jo.getOptionKey().equals("map_center")){
			String optionX=getPara("optionX");
			String optionY = getPara("optionY");
			String oXY=optionX+"|"+optionY;
			jo.setOptionValue(oXY);
		}
		boolean a = jo.update();
		jo.removeCache(jo.getId());
		jo.removeCache(jo.getOptionKey());
//		jo.putCache(jo.getOptionKey(), jo.getOptionValue(o));

		if(a){
			
			if(jo.getOptionKey().equals("c_serverConfig")){//修改了C平台地址,重启连接
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						// TODO Auto-generated method stub
						IMClientStarter2 client = new IMClientStarter2();
						boolean b = client.stop();
						if(b){
							client.start();
						}
					}
				});
				

			}
			
			
			renderAjaxResultForSuccess(MessageConstants.EDIT_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.EDIT_DEFEAT);
	}
	public void del() {
		Integer id = getParaToInt("id");
		JOption option = OptionQuery.me().findById(id);
		boolean a = option.delete();
		CacheKit.remove("option", id);
		if(a){
			renderAjaxResultForSuccess(MessageConstants.DEL_SUCCESS);
			return ;
		}
		renderAjaxResultForError(MessageConstants.DEL_DEFEAT);
	}
}
