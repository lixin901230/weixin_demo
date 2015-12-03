package com.lx.weixin.service;

import java.util.Map;

import com.lx.weixin.spring.bean.UserInfo;

/**
 * 微信公众号用户登录注册业务接口定义
 * 
 * @author lixin
 */
public interface IUserLoginRegsiterService {

	/**
	 * 保存用户信息<br>
	 * @param params
	 * @return
	 */
	public int saveUser(Map<String, Object> params);
	
	/**
	 * 保存用户信息<br>
	 * @param userInfo
	 * @return
	 */
	public int saveUser(UserInfo userInfo);
}
