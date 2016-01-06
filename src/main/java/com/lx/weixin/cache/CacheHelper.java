package com.lx.weixin.cache;

import java.util.Timer;
import java.util.TimerTask;


/**
 * 缓存有效期 
 * @author lixin
 */
public class CacheHelper {
	
	public static void main(String[] args) {
		
	}
	
	public static void period(TimerTask task, long delay) {
		Timer timer = new Timer();
		
		TimerTask task2 = new TimerTask() {
			
			@Override
			public void run() {
				
			}
		};
		timer.schedule(task, delay);
	}
	
	public void clearCache(String key) {
		
	}
}
