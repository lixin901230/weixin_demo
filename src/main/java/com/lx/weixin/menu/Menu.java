package com.lx.weixin.menu;

/**
 * 菜单实体
 * 
 * @author lixin
 */
public class Menu {

	private Button[] button;	//一级菜单数组，个数应为1~3个

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
	
}
