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
public abstract class BaseJNews<M extends BaseJNews<M>> extends JModel<M> implements IBean {

	public static final String CACHE_NAME = "news";
	public static final String METADATA_TYPE = "news";

	public static final String ACTION_ADD = "news:add";
	public static final String ACTION_DELETE = "news:delete";
	public static final String ACTION_UPDATE = "news:update";

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
		if(!(o instanceof BaseJNews<?>)){return false;}

		BaseJNews<?> m = (BaseJNews<?>) o;
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

	public void setTitle(java.lang.String title) {
		set("title", title);
	}

	public java.lang.String getTitle() {
		return get("title");
	}

	public void setPostTime(java.util.Date postTime) {
		set("postTime", postTime);
	}

	public java.util.Date getPostTime() {
		return get("postTime");
	}

	public void setPostMan(java.lang.String postMan) {
		set("postMan", postMan);
	}

	public java.lang.String getPostMan() {
		return get("postMan");
	}

	public void setContent(java.lang.String content) {
		set("content", content);
	}

	public java.lang.String getContent() {
		return get("content");
	}

	public void setType(java.lang.Integer type) {
		set("type", type);
	}

	public java.lang.Integer getType() {
		return get("type");
	}

	public void setAttachment(java.lang.String attachment) {
		set("attachment", attachment);
	}

	public java.lang.String getAttachment() {
		return get("attachment");
	}

	public void setAttachmentName(java.lang.String attachmentName) {
		set("attachmentName", attachmentName);
	}

	public java.lang.String getAttachmentName() {
		return get("attachmentName");
	}

	public void setReadcount(java.lang.Integer readcount) {
		set("readcount", readcount);
	}

	public java.lang.Integer getReadcount() {
		return get("readcount");
	}

}
