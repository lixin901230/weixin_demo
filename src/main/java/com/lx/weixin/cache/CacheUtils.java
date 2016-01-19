package com.lx.weixin.cache;

import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存工具类，由于未使用nosql，临时使用该类来进行缓存数据
 * 
 * @author lixin
 */
public class CacheUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	
	/** 使用并发HashMap来做静态缓存 */
	private static Map<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();
	private final static Timer timer = new Timer();
	
	/**
	 * 存入缓存
	 * @param key		缓存key
	 * @param object	缓存value
	 */
	public static void put(String key, Object object) {
		cacheMap.put(key, object);
	}
	
	/**
	 * 存入缓存，并指定有效期
	 * @param key		缓存key
	 * @param object	缓存值
	 * @param period	有效期，单位：秒
	 */
	public static void put(final String key, Object object, long period) {
		
		//放入缓存
		cacheMap.put(key, object);
		
		//@FIX 如果并发的情况，此处可能会造成线程堆积或机器CPU资源耗尽的情况，考虑单例模式如何？
		//设置缓存有效期
		TimerTask task = new TimerTask() {
			
			@Override
			public void run() {
				cacheMap.remove(key);
				logger.info("\n>>>>>>移除缓存["+key+"]\n");
			}
		};
		timer.schedule(task, (period * 1000));
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

	public static Map<String, Object> getCacheMap() {
		return cacheMap;
	}
	public static void setCacheMap(Map<String, Object> cacheMap) {
		CacheUtils.cacheMap = cacheMap;
	}
	
}
