package com.lx.weixin.menu;

/**
 * 菜单基类
 * 
 * @author lixin
 */
public class Button {

	private String type;	//click或view	
	private String name;	//菜单标题，不超过16个字节，子菜单不超过40个字节
	private Button[] sub_button;	//二级菜单数组，个数应为1~5个

	/** 
	 * 菜单类型
	 * 	1、click（点击推事件）：用户点击click类型按钮后，微信服务器会通过消息接口推送消息类型为event	的结构给开发者（参考消息接口指南），并且带上按钮中开发者填写的key值，开发者可以通过自定义的key值与用户进行交互;
	 * 	2、view（跳转URL）：用户点击view类型按钮后，微信客户端将会打开开发者在按钮中填写的网页URL，可与网页授权获取用户基本信息接口结合，获得用户基本信息。
	 */
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Button[] getSub_button() {
		return sub_button;
	}

	public void setSub_button(Button[] sub_button) {
		this.sub_button = sub_button;
	}
	
}
