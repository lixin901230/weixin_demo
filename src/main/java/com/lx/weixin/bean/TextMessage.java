package com.lx.weixin.bean;

/**
 * 文本消息对象
 * @author lx
 *
 */
public class TextMessage extends BaseMessage {

	private String Content;
	private String MsgId;
	
	
	public TextMessage() {
	}
	
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	
}
