package com.lx.weixin.util;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;


public class CommonUtils {
	
	/**
	 * 获取项目发布访问路径
	 * @param request
	 * @return String e.g：http://192.168.1.2:8080/weixin/
	 */
	public static String getDeplyPath(HttpServletRequest request) {
		String deplyPath = request.getScheme() 
				+ "://" + request.getServerName() 
				+ ":" + request.getServerPort() 
				+ "/" + request.getServletPath();
		return deplyPath;
	}
	
	/**
	 * 生成32位UUID字符串
	 * @return
	 */
	public static String uuid() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(uuid());
	}
}
