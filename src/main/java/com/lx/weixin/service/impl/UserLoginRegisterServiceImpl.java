package com.lx.weixin.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lx.weixin.service.IUserLoginRegsiterService;
import com.lx.weixin.spring.bean.UserInfo;
import com.lx.weixin.spring.jdbc.IUserLoginRegsiterDao;

/**
 * 微信公众号用户登录注册业务接口实现
 * 
 * @author lixin
 */
public class UserLoginRegisterServiceImpl implements IUserLoginRegsiterService {
	
	@Autowired
	private IUserLoginRegsiterDao userLoginRegsiterDao;

	@Override
	public int saveUser(Map<String, Object> params) {
		
		String sql = "";
		int flag = userLoginRegsiterDao.saveUser(sql, params);
		return flag;
	}

	@Override
	public int saveUser(UserInfo userInfo) {
		String sql = "";
		int flag = userLoginRegsiterDao.saveUser(sql, userInfo);
		return flag;
	}

}
