package com.lx.weixin.bean;

/**
 * 语音消息对象
 * 
 * @author lixin
 *
 */
public class VoiceMessage extends BaseMessage {

	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}
	public void setVoice(Voice voice) {
		Voice = voice;
	}
}
