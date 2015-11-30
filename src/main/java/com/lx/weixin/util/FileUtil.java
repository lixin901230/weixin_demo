package com.lx.weixin.util;

public class FileUtil {
	
	public static final String UPLOAD_PATH = "incoming";
	
	public static void main(String[] args) {
		System.out.println(getResRealPath());
	}

	/**
	 * 获取项目webapp的绝对路径<br/>
	 * 如：D:/Workspaces/eclipse-jee-luna-SR2/workspace1/weixin/src/main/webapp/
	 * @return
	 */
	public static String getResRealPath() {
		String absolutePath = Class.class.getResource("/").getPath();
		int startIndex = absolutePath.startsWith("/") ? 1 : 0;
		String resPath = absolutePath.substring(startIndex, absolutePath.indexOf("WEB-INF"));
		return resPath;
	}
}
