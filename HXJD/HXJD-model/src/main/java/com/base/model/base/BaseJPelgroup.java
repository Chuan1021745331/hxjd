package com.base.model.base;

import com.base.message.MessageKit;
import com.base.model.core.JModel;
import java.math.BigInteger;

import com.jfinal.plugin.activerecord.IBean;
import com.jfinal.plugin.ehcache.CacheKit;
import com.jfinal.plugin.ehcache.IDataLoader;

/**
 *  Auto generated by JPress, do not modify this file.
 */
@SuppressWarnings("serial")
public abstract class BaseJPelgroup<M extends BaseJPelgroup<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "pelgroup";
	public static final String METADATA_TYPE = "pelgroup";

	public static final String ACTION_ADD = "pelgroup:add";
	public static final String ACTION_DELETE = "pelgroup:delete";
	public static final String ACTION_UPDATE = "pelgroup:update";

	public void removeCache(Object key){
		if(key == null) {
			return;
		}
		CacheKit.remove(CACHE_NAME, key);
	}

	public void putCache(Object key,Object value){
		CacheKit.put(CACHE_NAME, key, value);
	}

	public M getCache(Object key){
		return CacheKit.get(CACHE_NAME, key);
	}

	public M getCache(Object key,IDataLoader dataloader){
		return CacheKit.get(CACHE_NAME, key, dataloader);
	}

	@Override
	public boolean equals(Object o) {
		if(o == null){ return false; }
		if(!(o instanceof BaseJPelgroup<?>)){return false;}

		BaseJPelgroup<?> m = (BaseJPelgroup<?>) o;
		if(m.getId() == null){return false;}

		return m.getId().compareTo(this.getId()) == 0;
	}

	@Override
	public boolean save() {
		boolean saved = super.save();
		if (saved) { MessageKit.sendMessage(ACTION_ADD, this); }
		return saved;
	}

	@Override
	public boolean delete() {
		boolean deleted = super.delete();
		if (deleted) { MessageKit.sendMessage(ACTION_DELETE, this); }
		return deleted;
	}

	@Override
	public boolean deleteById(Object idValue) {
		boolean deleted = super.deleteById(idValue);
		if (deleted) { MessageKit.sendMessage(ACTION_DELETE, this); }
		return deleted;
	}

	@Override
	public boolean update() {
		boolean update = super.update();
		if (update) { MessageKit.sendMessage(ACTION_UPDATE, this); }
		return update;
	}

	public void setId(java.lang.Integer id) {
		set("id", id);
	}

	public java.lang.Integer getId() {
		return get("id");
	}

	public void setPlegroupname(java.lang.String plegroupname) {
		set("plegroupname", plegroupname);
	}

	public java.lang.String getPlegroupname() {
		return get("plegroupname");
	}

	public void setCamp(java.lang.String camp) {
		set("camp", camp);
	}

	public java.lang.String getCamp() {
		return get("camp");
	}

	public void setState(java.lang.String state) {
		set("state", state);
	}

	public java.lang.String getState() {
		return get("state");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

	public void setDrill(java.lang.Integer drill) {
		set("drill", drill);
	}

	public java.lang.Integer getDrill() {
		return get("drill");
	}

}
