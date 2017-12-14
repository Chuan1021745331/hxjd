package com.base.interceptor;

import com.alibaba.fastjson.JSON;
import com.base.model.JButton;
import com.base.model.JMenu;
import com.base.model.JRolemenubutton;
import com.base.model.JUser;
import com.base.service.ButtonQuery;
import com.base.service.MenuQuery;
import com.base.service.RoleMenuButtonQuery;
import java.util.ArrayList;
import java.util.List;

import com.base.utils.StringUtils;
import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

/**
 * 权限按钮拦截器
 * All rights Reserved, Designed By hxjd
 * @类名: ButtonInterceptor.java   
 * @包名: com.base.interceptor   
 * @描述: TODO(用一句话描述该文件做什么)   
 * @所属: 华夏九鼎     
 * @日期: 2017年5月24日 下午3:17:52   
 * @版本: V1.0 
 * @创建人：z 
 * @修改人：z
 * @版权: 2017 hxjd Inc. All rights reserved. 
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class ButtonInterceptor implements Interceptor {

	@Override
	public void intercept(Invocation inv) {
		Controller controller = inv.getController();
		String target = controller.getRequest().getRequestURI();
		String cpath = controller.getRequest().getContextPath();
		
		if (!target.startsWith(cpath + "/admin")) {
			inv.invoke();
			return;
		}
		JUser user = InterUtils.tryToGetUser(inv);
		
		List<JMenu> menuAll = MenuQuery.me().getAll();
		Integer menuId=0;
		for (JMenu m : menuAll) {
			if(StringUtils.isNotEmpty(m.getUrl()) && target.endsWith(m.getUrl())){
				menuId=m.getId();
				continue;
			}
		}
		List<JButton> buttons=new ArrayList<JButton>();
		if(StringUtils.isNotEmpty(user.getRole())){
			buttons = ButtonQuery.me().findByMenuId(menuId);
		}else{
			Integer userId = user.getId();
			JRolemenubutton rolemenubutton = RoleMenuButtonQuery.me().findByUserId(userId, menuId);
			
			if(StringUtils.isNotEmpty(rolemenubutton.getButtons())){
				String[] str = rolemenubutton.getButtons().split("\\|");
				String newStr = "";  
				for (int i = 0; i < str.length; i++) {
					JButton b = ButtonQuery.me().findByCode(menuId,str[i]);
					if(null!=b){
						buttons.add(b);
						newStr+=str[i]+"|";
					}
				}
				if(!newStr.equals(rolemenubutton.getButtons())){
					rolemenubutton.setButtons(newStr);
					rolemenubutton.update();
				}
			}		
		}
		
		List<JButton> topButton = new ArrayList<JButton>();
		List<JButton> tableButton = new ArrayList<JButton>();
		
		for (JButton j : buttons) {
			if (j.getCode().equals("del") || j.getCode().equals("edit") || j.getCode().equals("sel")) {
				tableButton.add(j);
			}else{
				topButton.add(j);
			}
		}
		controller.setAttr("topButton", JSON.toJSON(topButton));
		controller.setAttr("tableButton", JSON.toJSON(tableButton));
		
		inv.invoke();
		return;
	}
}
