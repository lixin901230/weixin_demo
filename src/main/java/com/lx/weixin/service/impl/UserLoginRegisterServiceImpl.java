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
		
		StringBuffer sb = new StringBuffer();sb.append("	");
		sb.append("	INSERT INTO user_info (");
		sb.append("		id,                ");
		sb.append("		userName,          ");
		sb.append("		PASSWORD,          ");
		sb.append("		trueName,          ");
		sb.append("		unionId,           ");
		sb.append("		nickName,          ");
		sb.append("		sex,               ");
		sb.append("		email,             ");
		sb.append("		phone,             ");
		sb.append("		validFlag,         ");
		sb.append("		createDate         ");
		sb.append("	) VALUES (             ");
		sb.append("		':id',             ");
		sb.append("		':userName',       ");
		sb.append("		':password',       ");
		sb.append("		':trueName',       ");
		sb.append("		':unionId',        ");
		sb.append("		':nickName',       ");
		sb.append("		':sex',            ");
		sb.append("		':email',          ");
		sb.append("		':phone',          ");
		sb.append("		':validFlag',      ");
		sb.append("		':createDate')	   ");
		int flag = userLoginRegsiterDao.saveUser(sb.toString(), params);
		return flag;
	}

	@Override
	public int saveUser(UserInfo userInfo) {
		StringBuffer sb = new StringBuffer();sb.append("	");
		sb.append("	INSERT INTO user_info (");
		sb.append("		id,                ");
		sb.append("		userName,          ");
		sb.append("		PASSWORD,          ");
		sb.append("		trueName,          ");
		sb.append("		unionId,           ");
		sb.append("		nickName,          ");
		sb.append("		sex,               ");
		sb.append("		email,             ");
		sb.append("		phone,             ");
		sb.append("		validFlag,         ");
		sb.append("		createDate         ");
		sb.append("	) VALUES (             ");
		sb.append("		':id',             ");
		sb.append("		':userName',       ");
		sb.append("		':password',       ");
		sb.append("		':trueName',       ");
		sb.append("		':unionId',        ");
		sb.append("		':nickName',       ");
		sb.append("		':sex',            ");
		sb.append("		':email',          ");
		sb.append("		':phone',          ");
		sb.append("		':validFlag',      ");
		sb.append("		':createDate')	   ");
		int flag = userLoginRegsiterDao.saveUser(sb.toString(), userInfo);
		return flag;
	}

	@Override
	public UserInfo getUserInfoByUnionId(String unionId) {
		return userLoginRegsiterDao.getUserInfoByUnionId(unionId);
	}
	
	@Override
	public UserInfo login(String userName, String password) {
		return userLoginRegsiterDao.login(userName, password);
	}
}
