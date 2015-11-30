package com.lx.weixin.util;

import org.apache.commons.lang.StringUtils;

/**
 * 数字处理工具类
 * 
 * @author lixin
 *
 */
public class NumberUtil {

	/**
	 * 将数字字符串转为Integer类型
	 * @param numberStr
	 * @return
	 * @throws Exception
	 */
	public static Integer strToInteger(String numberStr) throws Exception {
		
		if(StringUtils.isEmpty(numberStr)) {
			throw new IllegalArgumentException("数字字符串转Integer失败，原因：numberStr参数为空！");
		}
		
		Integer result = null;
		try {
			result = Integer.parseInt(numberStr);
		} catch (Exception e) {
			e.printStackTrace();
			throw new NumberFormatException("字符串"+numberStr+"转换成Integer类型失败！");
		}
		return result;
	}
}
