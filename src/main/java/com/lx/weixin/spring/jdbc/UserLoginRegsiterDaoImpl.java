package com.lx.weixin.spring.jdbc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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
}
