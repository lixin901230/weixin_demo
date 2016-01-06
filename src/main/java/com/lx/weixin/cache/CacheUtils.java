package com.lx.weixin.cache;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 缓存工具类，由于未使用nosql，临时使用该类来进行缓存数据
 * 
 * @author lixin
 */
public class CacheUtils {
	
	private static Logger logger = LoggerFactory.getLogger(CacheUtils.class);
	
	private static ConcurrentMap<String, Object> cacheMap = new ConcurrentHashMap<String, Object>();

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
		
		//设置缓存有效期
		Timer timer = new Timer();
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
	
	public static ConcurrentMap<String, Object> getCacheMap() {
		return cacheMap;
	}
	public static void setCacheMap(ConcurrentMap<String, Object> cacheMap) {
		CacheUtils.cacheMap = cacheMap;
	}
	
}
