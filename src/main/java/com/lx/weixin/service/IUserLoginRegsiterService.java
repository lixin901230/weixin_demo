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
	
	/**
	 * 根据微信账号唯一标识查询该用户信息
	 * @param unionId
	 * @return
	 */
	public UserInfo getUserInfoByUnionId(String unionId);
	
	/**
	 * 用户登录
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserInfo login(String userName, String password);
}
