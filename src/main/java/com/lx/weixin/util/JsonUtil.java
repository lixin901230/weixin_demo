package com.lx.weixin.util;

import net.sf.json.JSONObject;

/**
 * json工具类
 * 
 * @author lixin
 *
 */
public class JsonUtil {

	/**
	 * 将对象转为json字符串
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj) {
		JSONObject jsonObject = JSONObject.fromObject(obj);
		String jsonStr = jsonObject.toString();
		return jsonStr;
	}
	
	/**
	 * 将json字符串装换为JsonObject
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject jsonStrToJsonObject(String jsonStr) {
		
		JSONObject jsonObject = JSONObject.fromObject(jsonStr);
		return jsonObject;
	}
}
