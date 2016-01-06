package com.lx.weixin.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 缓存工具类，由于未使用nosql，临时使用该类来进行缓存数据
 * 
 * @author lixin
 */
public class CacheUtils {
	
	private static ConcurrentMap<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

	/**
	 * 存入缓存
	 * @param key		缓存key
	 * @param object	缓存value
	 */
	public static void put(String key, Object object) {
		getCacheMap().put(key, object);
	}
	
	/**
	 * 取出缓存
	 * @param key	缓存key
	 * @return
	 */
	public static Object get(String key) {
		Object object = getCacheMap().get(key);
		return object;
	}
	
	public static ConcurrentMap<String, Object> getCacheMap() {
		return cacheMap;
	}
	public static void setCacheMap(ConcurrentMap<String, Object> cacheMap) {
		CacheUtils.cacheMap = cacheMap;
	}
	
}
