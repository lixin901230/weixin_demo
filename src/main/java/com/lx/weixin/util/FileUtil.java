package com.lx.weixin.util;

import java.io.File;

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
		String absolutePath = FileUtil.class.getClassLoader().getResource("").getPath();
		int startIndex = absolutePath.startsWith("/") ? 1 : 0;
		String resPath = absolutePath.substring(startIndex, absolutePath.indexOf("WEB-INF"));
		return resPath;
	}
	
	/**
     * 获取文件目录路径<br/>
     * 即：如：D:/Workspaces/eclipse-jee-luna-SR2/workspace1/weixin/src/main/webapp/incoming/
     * 
     * @return
     */
    public static String getIncomingDirPath() {
    	String resRealPath = getResRealPath();
		resRealPath = resRealPath.endsWith("/") ? resRealPath : resRealPath + "/"; 
		String fileDirPath = resRealPath + FileUtil.UPLOAD_PATH + "/";
		return fileDirPath;
    }
}
