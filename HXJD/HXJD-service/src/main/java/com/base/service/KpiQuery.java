package com.base.service;

import com.alibaba.druid.util.StringUtils;
import com.base.model.JKpi;
import com.base.model.JMenu;
import com.base.model.JUser;
import com.base.model.dto.JkpiDto;
import com.base.model.dto.MenuDto;
import com.base.model.dto.MenuSimpDto;
import com.base.utils.IsIntUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KpiQuery extends JBaseQuery {
	protected static final JKpi DAO = new JKpi();
	private static final KpiQuery QUERY = new KpiQuery();

	public static KpiQuery me() {
		return QUERY;
	}

	public long findCount() {
		return DAO.doFindCount();
	}
	
	public List<JKpi> findList(int page, int pagesize, int column, String order, String search) {
		StringBuilder sqlBuilder = new StringBuilder("select * from j_kpi u ");
		String c=column==0?"id":column==1?"parentId":column==2?"name":column==3?"score":"details";
		sqlBuilder.append(" WHERE CONCAT( IFNULL(id, ''),IFNULL(parentId, ''),IFNULL(`name`, ''),IFNULL(score, ''),IFNULL(details, '')) LIKE '%"+search+"%' ");
		sqlBuilder.append(" order by "+c+" "+order+" LIMIT ?, ? ");
		LinkedList<Object> params = new LinkedList<Object>();
		params.add(page);
		params.add(pagesize);

		if (params.isEmpty()) {
			return DAO.find(sqlBuilder.toString());
		} else {
			return DAO.find(sqlBuilder.toString(), params.toArray());
		}

	}
	public Long findConunt(String search) {
		if(StringUtils.isEmpty(search)){
			return DAO.doFindCount();
		}else{
			StringBuilder sqlBuilder = new StringBuilder();
			sqlBuilder.append(" where CONCAT( IFNULL(id, ''),IFNULL(parentId, ''),IFNULL(`name`, ''),IFNULL(score, ''),IFNULL(details, '')) LIKE '%"+search+"%' ");

			return DAO.doFindCount(sqlBuilder.toString());
		}
	}
	
	public List<JkpiDto> getMenus(JUser user){
		List<JKpi> menus=null;
		if("administrator".equals(user.getRole())){
			menus = this.getAll();
		}else{
//			menus = DAO.find("SELECT m.* FROM j_userrole u INNER JOIN j_rolemenubutton r ON r.roleId=u.roleId INNER JOIN  j_menu m ON m.id=r.menuId WHERE u.userId=?",user.getId());
		}
		
		List<JkpiDto> mts = new ArrayList<JkpiDto>();
		for (JKpi m : menus) {
			if (m.getParentId() == 0) {
				JkpiDto md = new JkpiDto();
				md.setM(m);
				Integer n = 0 ;
				for (JKpi m_ : menus) {
					if(m.getId().intValue()==m_.getParentId().intValue()){
						n++;
					}
				}
				md.setN(n);
				if(n>0){
					md.setChildren(getChild(m,menus));
				}
				mts.add(md);
			}
		}
		return mts;
	}
	public List<MenuSimpDto> getMenusSimp(){
		List<JKpi> menus = this.getAll();
		List<MenuSimpDto> fmts = new ArrayList<MenuSimpDto>();
		List<MenuSimpDto> mts = new ArrayList<MenuSimpDto>();
		MenuSimpDto msd = new MenuSimpDto();
		msd.setId("0");
		msd.setName("评分系统");
		for (JKpi m : menus) {
			if (m.getParentId() == 0) {
				MenuSimpDto md = new MenuSimpDto();
				md.setId(m.getId()+"");
				Integer n = 0 ;
				md.setName(m.getName());
				for (JKpi m_ : menus) {
					if(m.getId().intValue()==m_.getParentId().intValue()){
						n++;
					}
				}
				if(n>0){
					md.setChildren(getChildSimp(m,menus));
				}
				mts.add(md);
			}
		}
		msd.setChildren(mts);
		fmts.add(msd);
		return fmts;
	}
	
	public List<JkpiDto> getChild(JKpi pm,List<JKpi> ms){
//		List<Menu> menus = this.findByParent(pm.getId());
		List<JkpiDto> mts = new ArrayList<JkpiDto>();
		for (JKpi m:ms) {
			if(m.getParentId().intValue()==pm.getId().intValue()){
				JkpiDto md = new JkpiDto();
				md.setM(m);
				Integer n = 0 ;
				for (JKpi m_ : ms) {
					if(m.getId().intValue()==m_.getParentId().intValue()){
						n++;
					}
				}
				md.setN(n);
				if(n>0){
					md.setChildren(getChild(m,ms));
				}
				mts.add(md);
			}
		}
		return mts;
	}

	private static int count;

	public List<MenuSimpDto> getChildSimp(JKpi pm,List<JKpi> ms){
		List<MenuSimpDto> mts = new ArrayList<MenuSimpDto>();
		for (JKpi m:ms) {
			if(m.getParentId().intValue()==pm.getId().intValue()) {
				MenuSimpDto md = new MenuSimpDto();
				md.setId(m.getId() + "");
				String str = m.getName();
				Integer n = 0;
				boolean isInt = IsIntUtils.isNumeric(str);
				if (isInt){
					String name = ObjectsQuery.me().findById(Integer.valueOf(m.getName())).getName();
					md.setName(name);
				}else {
					md.setName(m.getName());
				}
				for (JKpi m_ : ms) {
					if(m.getId().intValue()==m_.getParentId().intValue()){
						n++;
					}
				}
				if(n>0){
					md.setChildren(getChildSimp(m,ms));
				}
				mts.add(md);
			}
		}
		
		return mts;
	}
	
	public List<JKpi> getAll(){
		return DAO.find("select * from j_kpi order by sort asc");
	}

	public List<JKpi> findByParent(final Integer parent){
		return DAO.find("select * from j_kpi where parentId="+parent+"");
	}

	public List<Integer> findByScore(final Integer id){
		List<JKpi> jkpi = DAO.find("select * from j_kpi where parentId="+id+"");
		List<Integer> listId = new ArrayList<>();
		if (jkpi.isEmpty()){
			return null;
		}else {
			for (JKpi score: jkpi){
				listId.add(score.getId());
			}
			return listId;
		}
	}

	public List<JKpi> findByParentid(final Integer id){
		return DAO.find("select * from j_kpi where parentId="+id+"");
	}

	/*public List<JKpi> findByid(final Integer id){
		List<JKpi> jKpis = DAO.find("select * from j_kpi where id="+id+"");
		List<Integer> listId = new ArrayList<>();
		if (jKpis.isEmpty()){
			return null;
		}else {
			for (JKpi score: jKpis){
				listId.add(score.getParentId());
			}
			return listId;
		}
	}*/


	public JKpi findById(final Integer kpiId) {
		return DAO.findById(kpiId);
	}

}
