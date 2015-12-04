package com.lx.weixin.spring.bean;

import java.io.Serializable;
import java.util.Date;


/**
 * 用户信息对象
 * @author lixin
 */
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1976453699249551218L;
	
	private String id;			//主键
	private String userName;	//用户名
	private String password;	//密码
	private String trueName;	//真实姓名
	private String unionId;		//微信账号唯一标示
	private String nickName;	//昵称
	private String sex;   		//性别
	private String email;		//邮箱
	private String phone;		//手机
	private String validFlag;	//有效标记；0：无效；1：有效；挂起
	private Date createDate;	//创建日期
	
	public UserInfo() {
		
	}

	public UserInfo(String id, String userName, String password,
			String trueName, String unionId, String nickName, String sex,
			String email, String phone, String validFlag, Date createDate) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.trueName = trueName;
		this.unionId = unionId;
		this.nickName = nickName;
		this.sex = sex;
		this.email = email;
		this.phone = phone;
		this.validFlag = validFlag;
		this.createDate = createDate;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTrueName() {
		return trueName;
	}
	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}
	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getValidFlag() {
		return validFlag;
	}
	public void setValidFlag(String validFlag) {
		this.validFlag = validFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
}
