package com.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.base.constants.MessageConstants;
import com.base.model.JButton;
import com.base.model.JRole;
import com.base.model.JRolemenubutton;
import com.base.model.dto.MenusButtonsDto;
import com.base.query.ButtonQuery;
import com.base.query.MenuQuery;
import com.base.query.RoleMenuButtonQuery;
import com.base.query.RoleQuery;
import com.base.utils.StringUtils;
import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.tx.Tx;
import com.jfinal.plugin.ehcache.CacheKit;

public class RoleService {
	private static final RoleService SERVICE = new RoleService();
	public static RoleService me() {
		return SERVICE;
	}
	
	public JRole findById(final Integer roleId) {
		return RoleQuery.me().findById(roleId);
	}
	
	public List<JRole> getAll(){
		return RoleQuery.me().getAll();
	}
	
	public JRole findByUserId(int id){
		return RoleQuery.me().findByUserId(id);
	}
	
	@Before(Tx.class)
	public boolean addSave(JRole jr, String roleMenus, String[] buttons){
		boolean a = jr.save();
		if(a){		
			if(roleMenus.length()>1){
				String[] rms = roleMenus.split(",");
				for (int i = 0; i < rms.length; i++) {
					StringBuffer sb = new StringBuffer();					
					if(null !=buttons){
						for (int j = 0; j < buttons.length; j++) {
							sb.append(buttons[j]+"|");
						}
					}					
					JRolemenubutton jmb= new JRolemenubutton();
					jmb.setRoleId(jr.getId());
					jmb.setMenuId(new Integer(rms[i]));
					if(sb.length()>0){
						jmb.setButtons(sb.toString().substring(0, sb.toString().length()-1));
					}
					jmb.save();					
				}
			}
			return true;
		}
		return false;
	}
	
	@Before(Tx.class)
	public boolean del(Integer id){
		JRole option = RoleQuery.me().findById(id);
		boolean a = option.delete();
		CacheKit.remove("role", id);
		if(a){
			RoleMenuButtonQuery.me().delbyRoleId(id);
			return true;
		}
		return false;
	}
	
	
	
	public Map<String, Object> getTree(Integer roleId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<JRolemenubutton> rmb = RoleMenuButtonQuery.me().findListByRoleId(roleId);
		map.put("tree", MenuService.me().getMenusSimp());
		map.put("tree_", rmb);
		return map;
	}
	
	public List<JButton> getButton(Integer id){
		List<JButton> buttons = ButtonQuery.me().findByMenuId(id);
		return buttons;
	}
	
	public List<MenusButtonsDto> getCheckButton(Integer mid, Integer rid){
		List<JButton> buttons = ButtonQuery.me().findByMenuId(mid);
		JRolemenubutton mb = RoleMenuButtonQuery.me().findListByRoleIdAndMenuId(rid, mid);
		List<MenusButtonsDto> mbs = new ArrayList<MenusButtonsDto>();
		String[] mArray=null;
		if(null!=mb){
			mArray = StringUtils.isNotEmpty(mb.getButtons())?mb.getButtons().split("\\|"):null;
		}
		for (JButton b : buttons) {
			MenusButtonsDto dto = new MenusButtonsDto();
			dto.setId(b.getId());
			dto.setName(b.getName());
			dto.setCode(b.getCode());
			dto.setTf(false);
			if(null!=mArray){
				List<String> list=Arrays.asList(mArray);
				if(list.contains(b.getCode())){
					dto.setTf(true);
				}
			}
			mbs.add(dto);
		}
		return mbs;
	}
}
