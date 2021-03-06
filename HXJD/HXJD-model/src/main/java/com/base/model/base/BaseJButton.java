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
public abstract class BaseJButton<M extends BaseJButton<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "button";
	public static final String METADATA_TYPE = "button";

	public static final String ACTION_ADD = "button:add";
	public static final String ACTION_DELETE = "button:delete";
	public static final String ACTION_UPDATE = "button:update";

	public void removeCache(Object key){
		if(key == null) return;
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
		if(!(o instanceof BaseJButton<?>)){return false;}

		BaseJButton<?> m = (BaseJButton<?>) o;
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

	public void setMenuId(java.lang.Integer menuId) {
		set("menuId", menuId);
	}

	public java.lang.Integer getMenuId() {
		return get("menuId");
	}

	public void setName(java.lang.String name) {
		set("name", name);
	}

	public java.lang.String getName() {
		return get("name");
	}

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setCode(java.lang.String code) {
		set("code", code);
	}

	public java.lang.String getCode() {
		return get("code");
	}

	public void setUrl(java.lang.String url) {
		set("url", url);
	}

	public java.lang.String getUrl() {
		return get("url");
	}

	public void setCss(java.lang.String css) {
		set("css", css);
	}

	public java.lang.String getCss() {
		return get("css");
	}

	public void setIcon(java.lang.String icon) {
		set("icon", icon);
	}

	public java.lang.String getIcon() {
		return get("icon");
	}

	public void setIsModal(java.lang.Boolean isModal) {
		set("isModal", isModal);
	}

	public java.lang.Boolean getIsModal() {
		return get("isModal");
	}

	public void setIsRight(java.lang.Boolean isRight) {
		set("isRight", isRight);
	}

	public java.lang.Boolean getIsRight() {
		return get("isRight");
	}

	public void setSort(java.lang.Long sort) {
		set("sort", sort);
	}

	public java.lang.Long getSort() {
		return get("sort");
	}

}
