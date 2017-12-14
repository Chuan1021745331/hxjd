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
public abstract class BaseJUsergrade<M extends BaseJUsergrade<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "usergrade";
	public static final String METADATA_TYPE = "usergrade";

	public static final String ACTION_ADD = "usergrade:add";
	public static final String ACTION_DELETE = "usergrade:delete";
	public static final String ACTION_UPDATE = "usergrade:update";

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
		if(!(o instanceof BaseJUsergrade<?>)){return false;}

		BaseJUsergrade<?> m = (BaseJUsergrade<?>) o;
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

	public void setParentId(java.lang.Integer parentId) {
		set("parentId", parentId);
	}

	public java.lang.Integer getParentId() {
		return get("parentId");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setScore(java.lang.Integer score) {
		set("score", score);
	}

	public java.lang.Integer getScore() {
		return get("score");
	}

	public void setTime(java.lang.String time) {
		set("time", time);
	}

	public java.lang.String getTime() {
		return get("time");
	}

	public void setCamp(java.lang.Integer camp) {
		set("camp", camp);
	}

	public java.lang.Integer getCamp() {
		return get("camp");
	}

}
