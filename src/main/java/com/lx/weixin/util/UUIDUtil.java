package com.lx.weixin.util;

import java.util.UUID;

public class UUIDUtil {

	/**
	 * 生成一个32位的uuid字符串
	 * @return
	 */
	public static String id() {
		String uuidStr = UUID.randomUUID().toString();
		String uuid = uuidStr.replaceAll("-", "");
		return uuid;
	}
	
	public static void main(String[] args) {
		System.out.println(id());
	}
}
