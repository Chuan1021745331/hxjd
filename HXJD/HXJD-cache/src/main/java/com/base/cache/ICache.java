package com.base.cache;

import java.util.List;

import com.jfinal.plugin.ehcache.IDataLoader;

public interface ICache extends com.jfinal.plugin.activerecord.cache.ICache {

	public List<?> getKeys(String cacheName);

	public void remove(String cacheName, Object key);

	public void removeAll(String cacheName);

	public <T> T get(String cacheName, Object key, IDataLoader dataLoader);
}
