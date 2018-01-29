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
public abstract class BaseJJob<M extends BaseJJob<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "job";
	public static final String METADATA_TYPE = "job";

	public static final String ACTION_ADD = "job:add";
	public static final String ACTION_DELETE = "job:delete";
	public static final String ACTION_UPDATE = "job:update";

	public void removeCache(Object key){
		if(null == key) {
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
		if(!(o instanceof BaseJJob<?>)){return false;}

		BaseJJob<?> m = (BaseJJob<?>) o;
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

	public void setJobName(java.lang.String jobName) {
		set("jobName", jobName);
	}

	public java.lang.String getJobName() {
		return get("jobName");
	}

	public void setJobGroup(java.lang.String jobGroup) {
		set("jobGroup", jobGroup);
	}

	public java.lang.String getJobGroup() {
		return get("jobGroup");
	}

	public void setJobStatus(java.lang.Integer jobStatus) {
		set("jobStatus", jobStatus);
	}

	public java.lang.Integer getJobStatus() {
		return get("jobStatus");
	}

	public void setCronExp(java.lang.String cronExp) {
		set("cronExp", cronExp);
	}

	public java.lang.String getCronExp() {
		return get("cronExp");
	}

	public void setRemark(java.lang.String remark) {
		set("remark", remark);
	}

	public java.lang.String getRemark() {
		return get("remark");
	}

}
