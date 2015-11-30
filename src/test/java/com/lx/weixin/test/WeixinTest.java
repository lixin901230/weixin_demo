package com.lx.weixin.test;

import org.junit.Test;

import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.util.AccessTokenUtil;

public class WeixinTest {

	/**
	 * access_token获取测试
	 */
	@Test
	public void testGetAccessToken() {
		
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		System.out.println(accessToken);
	}
}
