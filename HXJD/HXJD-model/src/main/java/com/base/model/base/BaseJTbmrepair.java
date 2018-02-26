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
public abstract class BaseJTbmrepair<M extends BaseJTbmrepair<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "tbmrepair";
	public static final String METADATA_TYPE = "tbmrepair";

	public static final String ACTION_ADD = "tbmrepair:add";
	public static final String ACTION_DELETE = "tbmrepair:delete";
	public static final String ACTION_UPDATE = "tbmrepair:update";

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
		if(!(o instanceof BaseJTbmrepair<?>)){return false;}

		BaseJTbmrepair<?> m = (BaseJTbmrepair<?>) o;
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

	public void setTbmId(java.lang.Integer tbmId) {
		set("tbmId", tbmId);
	}

	public java.lang.Integer getTbmId() {
		return get("tbmId");
	}

	public void setTbmname(java.lang.String tbmname) {
		set("tbmname", tbmname);
	}

	public java.lang.String getTbmname() {
		return get("tbmname");
	}

	public void setWorksitename(java.lang.String worksitename) {
		set("worksitename", worksitename);
	}

	public java.lang.String getWorksitename() {
		return get("worksitename");
	}

	public void setCircuitname(java.lang.String circuitname) {
		set("circuitname", circuitname);
	}

	public java.lang.String getCircuitname() {
		return get("circuitname");
	}

	public void setRepairman(java.lang.String repairman) {
		set("repairman", repairman);
	}

	public java.lang.String getRepairman() {
		return get("repairman");
	}

	public void setCreatetime(java.util.Date createtime) {
		set("createtime", createtime);
	}

	public java.util.Date getCreatetime() {
		return get("createtime");
	}

	public void setRepairtime(java.util.Date repairtime) {
		set("repairtime", repairtime);
	}

	public java.util.Date getRepairtime() {
		return get("repairtime");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public void setCycle(java.util.Date cycle) {
		set("cycle", cycle);
	}

	public java.util.Date getCycle() {
		return get("cycle");
	}

	public void setUserId(java.lang.Integer userId) {
		set("userId", userId);
	}

	public java.lang.Integer getUserId() {
		return get("userId");
	}

	public void setReadcount(java.lang.Integer readcount) {
		set("readcount", readcount);
	}

	public java.lang.Integer getReadcount() {
		return get("readcount");
	}

}
