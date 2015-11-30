package com.lx.weixin.bean;

/**
 * 视频消息对象
 * 
 * @author lixin
 *
 */
public class VideoMessage extends BaseMessage {

	private Video Video;

	public Video getVideo() {
		return Video;
	}
	public void setVideo(Video video) {
		Video = video;
	}
	
}
