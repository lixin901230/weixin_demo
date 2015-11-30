package com.lx.weixin.bean;

/**
 * 图片消息
 * @author lixin
 */
public class ImageMessage extends BaseMessage {

	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image image) {
		Image = image;
	}
	
}
