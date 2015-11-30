package com.lx.weixin.menu;

/**
 * click 类型菜单
 * 
 * @author lixin
 *
 */
public class ClickButton extends Button {

	private String key;	//菜单KEY值，用于消息接口推送，不超过128字节

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
