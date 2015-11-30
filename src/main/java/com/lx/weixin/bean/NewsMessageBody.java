package com.lx.weixin.bean;

/**
 * 图文消息 消息体
 * 
 * @author lixin
 */
public class NewsMessageBody {

	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;
	
	public NewsMessageBody() {
	}
	
	public NewsMessageBody(String title, String description, String picUrl,
			String url) {
		Title = title;
		Description = description;
		PicUrl = picUrl;
		Url = url;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
