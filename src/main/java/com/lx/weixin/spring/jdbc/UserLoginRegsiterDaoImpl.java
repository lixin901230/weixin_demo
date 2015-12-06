package com.lx.weixin.spring.jdbc;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.lx.weixin.spring.bean.UserInfo;

/**
 * 微信公众号用户登录注册
 * @author lixin
 *
 */
public class UserLoginRegsiterDaoImpl implements IUserLoginRegsiterDao {
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;	//注入具名参数模板对象来操作数据库
	
	@Override
	public void saveUser(String sql) throws Exception {
		jdbcTemplate.execute(sql);
	}
	
	@Override
	public int saveUser(String sql, Map<String, Object> params) {
		int updateRow = namedParameterJdbcTemplate.update(sql, params);
		return updateRow;
	}
	
	@Override
	public int saveUser(String sql, UserInfo userInfo) {
		SqlParameterSource parameterSource = new BeanPropertySqlParameterSource(userInfo);
		int updateRow = namedParameterJdbcTemplate.update(sql, parameterSource);
		return updateRow;
	}
	
	@Override
	public UserInfo getUserInfoByUnionId(String unionId) {
		
		try {
			if(StringUtils.isEmpty(unionId)) {
				throw new Exception("getUserInfoByUnionId(String unionId)调用失败，原因：UnionId不能为空");
			}
			
			Map<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("unionId", unionId);
			
			StringBuffer sb = new StringBuffer();
			sb.append("	SELECT                     ");
			sb.append("		usr.id,                ");
			sb.append("		usr.userName,          ");
			sb.append("		usr.`password`,        ");
			sb.append("		usr.trueName,          ");
			sb.append("		usr.unionId,           ");
			sb.append("		usr.nickName,          ");
			sb.append("		usr.sex,               ");
			sb.append("		usr.phone,             ");
			sb.append("		usr.email,             ");
			sb.append("		usr.validFlag,         ");
			sb.append("		usr.createDate         ");
			sb.append("	FROM                       ");
			sb.append("		user_info usr          ");
			sb.append("	WHERE                      ");
			sb.append("		usr.unionId = :unionId ");
			UserInfo userInfo = namedParameterJdbcTemplate.queryForObject(sb.toString(), paramMap, UserInfo.class);
			return userInfo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public UserInfo getUserInfo(String userName) {
		return getUserInfo(userName, null);
	}
	
	public UserInfo getUserInfo(String userName, String password) {
		
		UserInfo userInfo = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userName", userName);
		paramMap.put("password", password);
		
		StringBuffer sb = new StringBuffer();
		sb.append("	SELECT                     ");
		sb.append("		usr.id,                ");
		sb.append("		usr.userName,          ");
		sb.append("		usr.`password`,        ");
		sb.append("		usr.trueName,          ");
		sb.append("		usr.unionId,           ");
		sb.append("		usr.nickName,          ");
		sb.append("		usr.sex,               ");
		sb.append("		usr.phone,             ");
		sb.append("		usr.email,             ");
		sb.append("		usr.validFlag,         ");
		sb.append("		usr.createDate         ");
		sb.append("	FROM                       ");
		sb.append("		user_info usr          ");
		sb.append("	WHERE                      ");
		sb.append("		usr.userName = :userName ");
		if(StringUtils.isNotEmpty(password)) {
			sb.append("	AND usr.password = :password ");
		}
		try {
			userInfo = namedParameterJdbcTemplate.queryForObject(sb.toString(), paramMap, UserInfo.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return userInfo;
	}
	
	/**
	 * 更新用户微信唯一标识UnionId
	 * @param userId
	 * @return
	 */
	public int updateUserUnionId(String userId, String unionId) {
		
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("userId", userId);
		paramMap.put("unionId", unionId);
		String sql = "UPDATE user_info SET unionId=':unionId' WHERE id=':userId'";
		return namedParameterJdbcTemplate.update(sql, paramMap);
	}
}
