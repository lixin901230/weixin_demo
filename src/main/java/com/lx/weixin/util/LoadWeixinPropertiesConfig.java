package com.lx.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 单例模式 加载weixin.properties配置文件
 * 
 * @author lixin
 */
public class LoadWeixinPropertiesConfig {

	private static Logger logger = LoggerFactory.getLogger(LoadWeixinPropertiesConfig.class);
	
	private static volatile Properties properties = null;
	private static volatile LoadWeixinPropertiesConfig loadPropertiesConfig = null;
	private static final String fileName = "weixin.properties";
	
	private LoadWeixinPropertiesConfig() {
		
	}
	
	/**
	 * 实例化对象
	 * @return
	 */
	public static LoadWeixinPropertiesConfig getInstance() {
		
		if(loadPropertiesConfig == null) {	//添加了一层if，提高性能，当loadPropertiesConfig已经实例化后下次进入不必执行synchronized (LoadPropertiesConfig.class)获取对象锁
			synchronized (LoadWeixinPropertiesConfig.class) {	//防止多线程同时进入造成loadPropertiesConfig被多次实例化
				if(loadPropertiesConfig == null) {
					loadPropertiesConfig = new LoadWeixinPropertiesConfig();
				}
			}
		}
		return loadPropertiesConfig;
	}
	
	/**
	 * 加载weixin.properties属性配置信息
	 * @return
	 */
	public Properties getConfig() {
		loadProperties();
		return properties;
	}
	
	/**
	 * 加载properties配置信息
	 * @param fileName  properties配置文件名称
	 * @return
	 * @throws IOException
	 */
	private void loadProperties() {
		
		if(properties == null) {
			InputStream inputStream = null;
			try {
				ClassLoader loader = LoadWeixinPropertiesConfig.class.getClassLoader();
				inputStream = loader.getResourceAsStream(fileName);
				
				if(inputStream != null) {
					properties = new Properties();
				    properties.load(inputStream);
				} else {
					logger.error("无法读入"+fileName+"文件，启动失败！");
				    throw new RuntimeException(">>>>>>>>>>无法读入"+fileName+"文件，启动失败！");
				}
			} catch (Exception e) {
				logger.error("加载"+fileName+"配置信息失败，原因："+e);
				e.printStackTrace();
			} finally {
				if(inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void main(String[] args) {
		Properties properties = LoadWeixinPropertiesConfig.getInstance().getConfig();
		System.out.println(properties);
	}
}
