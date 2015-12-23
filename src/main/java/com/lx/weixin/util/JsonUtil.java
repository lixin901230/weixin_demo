package com.lx.weixin.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

/**
 * json工具类
 * 
 * @author lixin
 *
 */
public class JsonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	public static Map<String, Object> getResultDataMap(Object obj) {
		Map<String, Object> resultData = new HashMap<String, Object>();
		resultData.put("data", obj);
		return resultData;
	}
	
	public static void writeJsonStr(HttpServletResponse response, String jsonStr) {
		
		response.setCharacterEncoding("UTF-8");
		try {
			PrintWriter writer = response.getWriter();
			writer.write(jsonStr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			logger.error(jsonStr+" 转换失败，原因："+e);
			e.printStackTrace();
		}
	}

	/**
	 * 将对象转为json字符串
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj) {
		try {
			JSONObject jsonObject = JSONObject.fromObject(obj);
			String jsonStr = jsonObject.toString();
			return jsonStr;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将json字符串装换为JsonObject
	 * @param jsonStr
	 * @return
	 */
	public static JSONObject strToJson(String jsonStr) {
		
		try {
			if(jsonStr.indexOf("[") > -1) {
				jsonStr = jsonStr.replaceAll("\\[", "\\\\[");
			}
			if(jsonStr.indexOf("]") > -1) {
				jsonStr = jsonStr.replaceAll("\\]", "\\\\]");
			}
			JSONObject jsonObject = JSONObject.fromObject(jsonStr);
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static JSONObject objToJson(Object object) {
		
		JSONObject jsonObject = JSONObject.fromObject(object);
		return jsonObject;
	}
}
