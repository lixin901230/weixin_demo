package com.lx.weixin.util;

/**
 * 微信公众号开发 常量类
 * @author lixin
 *
 */
public class WeixinConst {

	//微信账号唯一标识sesion 存储key
	public static final String SESSION_UNIONID = "unionId";
	
	//weixin开放URL(无需登录)
	public static final String WEIXIN_OPEN_URL = "/weixin/open";
	//weixin要做登录验证的URL
	public static final String WEIXIN_AUTH_URL = "/weixin/auth";
	
	//在公众号的菜单中访问公众号微站主页，不需要登录 的状态标识
	public static final String WEIXIN_INDEX_STATE = "0";
	
	//在公众号的菜单中访问微信公众号需要登录的页面的状态标识
	public static final String WEIXIN_LOGIN_STATE = "1";
}
