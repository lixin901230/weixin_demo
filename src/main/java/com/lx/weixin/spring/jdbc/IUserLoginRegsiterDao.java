package com.lx.weixin.spring.jdbc;

import java.util.Map;

import com.lx.weixin.spring.bean.UserInfo;

public interface IUserLoginRegsiterDao {

	/**
	 * 保存用户信息
	 * @param sql
	 * @throws Exception
	 */
	public void saveUser(String sql) throws Exception;
	
	/**
	 * 保存用户信息<br>
	 * 注意：参数以map形式传入，key为sql占位符对应的参数名称，value即为参数值
	 * @param sql
	 * @param params
	 * @return
	 */
	public int saveUser(String sql, Map<String, Object> params);
	
	/**
	 * 保存用户信息<br>
	 * 注意：使用SqlParameterSource做统一参数时，sql 语句中的占位符参数名必须 与 实体类的属性名要一致
	 * @param sql
	 * @param userInfo
	 * @return
	 */
	public int saveUser(String sql, UserInfo userInfo);
	
	/**
	 * 根据微信账号唯一标识查询该用户信息
	 * @param unionId
	 * @return
	 */
	public UserInfo getUserInfoByUnionId(String unionId);
	
	/**
	 * 根据用户名查询用户
	 * @param userName
	 * @return
	 */
	public UserInfo getUserInfo(String userName);
	
	/**
	 * 根据用户名和密码查询用户
	 * @param userName
	 * @param password
	 * @return
	 */
	public UserInfo getUserInfo(String userName, String password);
	
	/**
	 * 更新用户微信唯一标识UnionId
	 * @param userId
	 * @param unionId
	 * @return
	 */
	public int updateUserUnionId(String userId, String unionId);
}
