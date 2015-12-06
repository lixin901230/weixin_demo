package com.lx.weixin.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.bean.Image;
import com.lx.weixin.bean.ImageMessage;
import com.lx.weixin.bean.Music;
import com.lx.weixin.bean.MusicMessage;
import com.lx.weixin.bean.NewsMessage;
import com.lx.weixin.bean.NewsMessageBody;
import com.lx.weixin.bean.TextMessage;
import com.lx.weixin.bean.Video;
import com.lx.weixin.bean.VideoMessage;
import com.lx.weixin.bean.Voice;
import com.lx.weixin.bean.VoiceMessage;
import com.lx.weixin.httpClient.HttpClientUtil;

public class MessageUtil {
	
	//消息类型
	public static final String MESSAGE_TEXT = "text";				//文本消息
	public static final String MESSAGE_IMAGE = "image";				//图片消息
	public static final String MESSAGE_VOICE = "voice";				//语音消息
	public static final String MESSAGE_VIDEO = "video";				//视频消息
	public static final String MESSAGE_SHORTVIDEO = "shortvideo";	//视频消息
	public static final String MESSAGE_LINK = "link";				//连接消息
	public static final String MESSAGE_LOCATION = "location";		//地理位置消息
	
	public static final String MESSAGE_MUSIC = "music";				//音乐消息
	public static final String MESSAGE_THUMB = "thumb";				//缩略图，如：音乐消息中的缩略图

	public static final String MESSAGE_NEWS = "news";				//图文消息
	
	//接收事件推送类型
	public static final String MESSAGE_EVENT = "event";				//关注/取消关注事件
	public static final String MESSAGE_SUBSCRIBE = "subscribe";		//关注时的事件推送
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";	//取消关注时的事件推送
	public static final String MESSAGE_SCAN = "scan";				//用户已关注时的事件推送
	public static final String MESSAGE_CLICK = "click";				//点击菜单拉取消息时的事件推送
	

	public static String replyDesc() {
		StringBuffer sb = new StringBuffer();
		sb.append("本公众号主要是用于微信公众平台开发测试；\n回复内容介绍：\n");
		sb.append("0：关键字回复说明；\n");
		sb.append("1：view链接菜单测试；\n");
		sb.append("2：文本信息；\n");
		sb.append("3：图片信息；\n");
		sb.append("4：视频信息；\n");
		sb.append("5：音乐信息；\n");
		sb.append("6：语音信息；\n");
		return sb.toString();
	}
	
	/**
	 * 文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initMsgReplyDesc(String toUserName, String fromUserName, String content) {
		
		TextMessage sendMsg = new TextMessage();
		sendMsg.setToUserName(toUserName);
		sendMsg.setFromUserName(fromUserName);
		sendMsg.setMsgType(MessageUtil.MESSAGE_TEXT);
		sendMsg.setCreateTime(new Date().getTime());
		sendMsg.setContent(content);
		return XmlUtil.objToXml(sendMsg, "xml");
	}
	
	/**
	 * 文本消息
	 * @param toUserName
	 * @param fromUserName
	 * @param content
	 * @return
	 */
	public static String initText(String toUserName, String fromUserName, String content) {
		TextMessage sendMsg = new TextMessage();
		sendMsg.setToUserName(toUserName);
		sendMsg.setFromUserName(fromUserName);
		sendMsg.setMsgType(MessageUtil.MESSAGE_TEXT);
		sendMsg.setCreateTime(new Date().getTime());
		sendMsg.setContent(content);
		return XmlUtil.objToXml(sendMsg, "xml");
	}
	
	public static String testMsg() {
		StringBuffer sb = new StringBuffer();
		sb.append("课程空间MOOC为 网梯科技发展有限公司提供的一个在线学习平台 inside.kfkc.webtrn.cn\n\n");
		return sb.toString();
	}
	
	/**
	 * 发送图文消息
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initNewsMessage(String toUserName, String fromUserName) {
		
		List<NewsMessageBody> msgBodys = new ArrayList<NewsMessageBody>();
		NewsMessageBody msgBody = new NewsMessageBody("还不快给朕跪下请安！", "这是lixin的第一条图文测试消息", 
				"http://www.lixinsj.com.cn/weixin/upload/images/shuangjiegun.jpg", 
				"http://www.lixinsj.com.cn/weixin/upload/images/shuangjiegun.jpg");
		NewsMessageBody msgBody2 = new NewsMessageBody("你的微信openId="+fromUserName+"，感谢你关注本公众号！你的关注是对我最大的支持，李鑫在此表示感谢！特献上一份小福利！", "漂亮MM", 
				"http://www.lixinsj.com.cn/weixin/upload/images/meinv.jpg", 
				"http://www.lixinsj.com.cn/weixin/upload/images/meinv.jpg");
		NewsMessageBody msgBody3 = new NewsMessageBody("萌宠来了", "萌萌哒", 
				"http://www.lixinsj.com.cn/weixin/upload/images/Zhuoku189.jpg", 
				"http://www.lixinsj.com.cn/weixin");
		msgBodys.add(msgBody);
		msgBodys.add(msgBody2);
		msgBodys.add(msgBody3);
		
		NewsMessage newsMsg = new NewsMessage();
		
		newsMsg.setToUserName(toUserName);
		newsMsg.setFromUserName(fromUserName);
		newsMsg.setCreateTime(Long.valueOf(new Date().getTime()));
		newsMsg.setMsgType(MESSAGE_NEWS);
		newsMsg.setArticleCount(msgBodys.size()+"");
		newsMsg.setArticles(msgBodys);
		
		Map<String, Object> xmlElementAliasMap = new HashMap<String, Object>();
		xmlElementAliasMap.put("xml", newsMsg);
		xmlElementAliasMap.put("item", msgBody);
		String msgXml = XmlUtil.objToXml(newsMsg, xmlElementAliasMap);
		return msgXml;
	}
	
	/**
	 * 发送图片消息
	 * 图片格式：bmp, png, jpeg, jpg, gif
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initImage(String toUserName, String fromUserName) {
		
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken.getToken());
		params.put("type", MESSAGE_IMAGE);
		
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			String url = WeixinURLUtil.ADD_TEMP_MATERIAL_URL.substring(0, WeixinURLUtil.ADD_TEMP_MATERIAL_URL.indexOf("?"));
			
			String filePath = "D:/Workspaces/eclipse-jee-luna-SR2/workspace1/weixin/src/main/webapp/upload/images/meinv.jpg";
			PostMethod postMethod = HttpClientUtil.uploadFile(url, filePath, params);
			int state = httpClient.executeMethod(postMethod);
			if(state == 200) {
				String respBody = postMethod.getResponseBodyAsString();
				jsonObject = JsonUtil.strToJson(respBody);
				if(!jsonObject.has("errcode")) {
					System.out.println("图片上传成功！");
				}
				System.out.println("返回内容："+respBody);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mediaId = jsonObject.getString("media_id");
		String type = jsonObject.getString("type");
		
		Image image = new Image();
		image.setMediaId(mediaId);
		
		ImageMessage imageMessage = new ImageMessage();
		imageMessage.setToUserName(toUserName);
		imageMessage.setFromUserName(fromUserName);
		imageMessage.setCreateTime(new Date().getTime());
		imageMessage.setMsgType(MESSAGE_IMAGE);
		imageMessage.setImage(image);
		
		Map<String, Object> xmlElementAliasMap = new HashMap<String, Object>();
		xmlElementAliasMap.put("xml", imageMessage);
		xmlElementAliasMap.put("Image", image.getClass());
		String result = XmlUtil.objToXml(imageMessage, xmlElementAliasMap);
		return result;
	}

	/**
	 * 视频消息
	 * 语音格式：amr
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initVoice(String toUserName, String fromUserName) {
		
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken.getToken());
		params.put("type", MESSAGE_VIDEO);
		
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			String url = WeixinURLUtil.ADD_TEMP_MATERIAL_URL.substring(0, WeixinURLUtil.ADD_TEMP_MATERIAL_URL.indexOf("?"));
			
			String filePath = "D:/Workspaces/eclipse-jee-luna-SR2/workspace1/weixin/src/main/webapp/upload/voice/See_You_Again.amr";
			PostMethod postMethod = HttpClientUtil.uploadFile(url, filePath, params);
			int state = httpClient.executeMethod(postMethod);
			if(state == 200) {
				String respBody = postMethod.getResponseBodyAsString();
				jsonObject = JsonUtil.strToJson(respBody);
				if(!jsonObject.has("errcode")) {
					System.out.println("语音上传成功！");
				}
				System.out.println("返回内容："+respBody);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String mediaId = jsonObject.getString("media_id");
		String type = jsonObject.getString("type");
		System.out.println(jsonObject.toString());
		
		
		//mediaId = "0SV8fcZXPnyf4m3tdLm7NsN2qOm8nOr3fRR7BjWPYnVnHa40gWdIFiNL_z-Japzu";
		
		Voice voice = new Voice();
		voice.setMediaId(mediaId);
		
		VoiceMessage voiceMessage = new VoiceMessage();
		voiceMessage.setToUserName(toUserName);
		voiceMessage.setFromUserName(fromUserName);
		voiceMessage.setCreateTime(new Date().getTime());
		voiceMessage.setMsgType(MESSAGE_VOICE);
		voiceMessage.setVoice(voice);
		
		Map<String, Object> xmlElementAliasMap = new HashMap<String, Object>();
		xmlElementAliasMap.put("xml", voiceMessage);
		xmlElementAliasMap.put("Voice", voice);
		String result = XmlUtil.objToXml(voiceMessage, xmlElementAliasMap);
		return result;
	}
	
	/**
	 * 视频消息
	 * 视频格式：rm, rmvb, wmv, avi, mpg, mpeg, mp4
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initVideo(String toUserName, String fromUserName) {
		
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		
		/*
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken.getToken());
		params.put("type", MESSAGE_VIDEO);
		
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			String url = WeixinURLUtil.ADD_TEMP_MATERIAL_URL.substring(0, WeixinURLUtil.ADD_TEMP_MATERIAL_URL.indexOf("?"));
			
			String filePath = "D:/Workspaces/eclipse-jee-luna-SR2/workspace1/weixin/src/main/webapp/upload/video/video.mp4";
			PostMethod postMethod = HttpClientUtil.uploadFile(url, filePath, params);
			int state = httpClient.executeMethod(postMethod);
			if(state == 200) {
				String respBody = postMethod.getResponseBodyAsString();
				jsonObject = JsonUtil.jsonStrToJsonObject(respBody);
				if(!jsonObject.has("errcode")) {
					System.out.println("视频上传成功！");
				}
				System.out.println("返回内容："+respBody);
			}
			
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String mediaId = jsonObject.getString("media_id");
//		String type = jsonObject.getString("type");
		System.out.println(jsonObject.toString());
		*/
		
		String mediaId = "iI-PKWCpy4DxVhmFFu16duvBnNaUF5mP7DPqsDBOtfSMrELnT3fHSsmwlPw0Vvau";
		
		Video video = new Video();
		video.setMediaId(mediaId);
		
		VideoMessage videoMessage = new VideoMessage();
		videoMessage.setToUserName(toUserName);
		videoMessage.setFromUserName(fromUserName);
		videoMessage.setCreateTime(new Date().getTime());
		videoMessage.setMsgType(MESSAGE_VIDEO);
		videoMessage.setVideo(video);
		
		Map<String, Object> xmlElementAliasMap = new HashMap<String, Object>();
		xmlElementAliasMap.put("xml", videoMessage);
		xmlElementAliasMap.put("Image", video);
		String result = XmlUtil.objToXml(videoMessage, xmlElementAliasMap);
		return result;
	}
	
	/**
	 * 音乐消息（微信有些音乐可能播放不了，但消息是没问题的）
	 * @param toUserName
	 * @param fromUserName
	 * @return
	 */
	public static String initMusic(String toUserName, String fromUserName) {
		
		AccessToken accessToken = AccessTokenUtil.getAccessToken();
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("access_token", accessToken.getToken());
		params.put("type", MESSAGE_THUMB);
		
		JSONObject jsonObject = null;
		try {
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			String url = WeixinURLUtil.ADD_TEMP_MATERIAL_URL.substring(0, WeixinURLUtil.ADD_TEMP_MATERIAL_URL.indexOf("?"));
			
			String filePath = "D:/Workspaces/eclipse-jee-luna-SR2/workspace1/weixin/src/main/webapp/upload/images/thumb_music2.jpg";
			PostMethod postMethod = HttpClientUtil.uploadFile(url, filePath, params);
			int state = httpClient.executeMethod(postMethod);
			if(state == 200) {
				String respBody = postMethod.getResponseBodyAsString();
				jsonObject = JsonUtil.strToJson(respBody);
				if(!jsonObject.has("errcode")) {
					System.out.println("音乐消息缩略图上传成功！");
				}
				System.out.println("返回内容："+respBody);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String thumbMediaId = jsonObject.getString("thumb_media_id");
//		String type = jsonObject.getString("type");
		System.out.println(jsonObject.toString());
		
		Music music = new Music();
		music.setTitle("See You Again");
		music.setDescription("速7片尾曲");
		music.setThumbMediaId(thumbMediaId);
		music.setMusicUrl("http://www.lixinsj.com.cn/weixin/upload/music/See_You_Again.mp3");
		music.setHQMusicUrl("http://www.lixinsj.com.cn/weixin/upload/music/See_You_Again.mp3");
		
		MusicMessage musicMessage = new MusicMessage(); 
		musicMessage.setToUserName(toUserName);
		musicMessage.setFromUserName(fromUserName);
		musicMessage.setCreateTime(new Date().getTime());
		musicMessage.setMsgType(MESSAGE_MUSIC);
		musicMessage.setMusic(music);
		
		Map<String, Object> xmlElementAliasMap = new HashMap<String, Object>();
		xmlElementAliasMap.put("xml", musicMessage);
		xmlElementAliasMap.put("Image", music);
		String result = XmlUtil.objToXml(musicMessage, xmlElementAliasMap);
		return result;
	}
	
}
