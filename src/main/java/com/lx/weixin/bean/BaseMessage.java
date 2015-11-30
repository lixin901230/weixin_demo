package com.lx.weixin.bean;

/**
 * 微信消息 基础信息
 * 
 * 消息类型：
 * 	1 文本消息：text
 * 	2 图片消息：image
 * 	3 语音消息：voice
 * 	4 视频消息：video
 * 	5 小视频消息：shortvideo
 * 	6 地理位置消息：location
 * 	7 链接消息：link
 * 
 * @author lixin
 *
 */
public abstract class BaseMessage {

	private String ToUserName;
	private String FromUserName;
	private Long CreateTime;
	private String MsgType;
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	
}
