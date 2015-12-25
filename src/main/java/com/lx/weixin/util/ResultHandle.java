package com.lx.weixin.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * 请求结果处理
 * @author lx
 *
 */
public class ResultHandle {

	public static void main(String[] args) {
		Map<String, Object> resultMap = getResultMap(true, null, null);
		System.out.println(resultMap);
	}
	
	public static Map<String, Object> getResultDataMap(Object obj) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("data", obj);
		return resultData;
	}
	
	/**
	 * @param success	请求成功、失败标记；true：成功；false：失败
	 * @param data		请求数据
	 * @param msg		请求成功、失败信息
	 * @return
	 */
	public static Map<String, Object> getResultMap(boolean success, Object data, String msg) {
		
		/*if(data == null) {
			data = "";
		}
		if(StringUtils.isEmpty(msg)) {
			msg = "";
		}*/
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", success);
		resultMap.put("data", data);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	/**
	 * @param success	请求成功、失败标记；true：成功；false：失败
	 * @param data		请求数据
	 * @param msg		请求成功、失败信息
	 * @return
	 */
	public static Map<String, Object> getResultMap(boolean success, String msg) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", success);
		resultMap.put("msg", msg);
		return resultMap;
	}
	
	/**
	 * @param success	请求成功、失败标记
	 * @param data		请求数据
	 * @param msg		请求成功、失败信息
	 * @return
	 */
	public static Map<String, Object> getResultMap(String success, String msg) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("success", success);
		resultMap.put("msg", msg);
		return resultMap;
	}
}
