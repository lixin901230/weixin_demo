package com.lx.weixin.bean;

/**
 * 
 * @author lixin
 *
 */
public class AccessToken {

	private String token;		//票据
	private Integer expiresIn;	//有效期
	
	public AccessToken() {
	}
	public AccessToken(String token, Integer expiresIn) {
		this.token = token;
		this.expiresIn = expiresIn;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Integer getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}
	
	@Override
	public String toString() {
		return "AccessToken [\ntoken=" + token + ", \nexpiresIn=" + expiresIn + "\n]";
	}
}
