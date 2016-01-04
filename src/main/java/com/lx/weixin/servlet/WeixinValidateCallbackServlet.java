package com.lx.weixin.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentException;

import com.lx.weixin.bean.TextMessage;
import com.lx.weixin.util.MessageUtil;
import com.lx.weixin.util.SHA1;
import com.lx.weixin.util.XmlUtil;
import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;

/**
 * 微信公众号  服务号、企业号开发者接入验证
 */
public class WeixinValidateCallbackServlet extends HttpServlet {
    
	private static final long serialVersionUID = 4440739483644821986L;
	
	private String corpID = "wx4edd47d3a6r4r991";//这里是你企业号的CorpID
	private String encodingAESKey = "eYgOUgzU4iUbBOKxNCl40FRUFdo5ypt6LksHaMcEJSs";//这个EncodingAESKey是随机生成，但是必须跟企业号上的相同
	
	private String token = "lixin891230";//这个Token是随机生成，但是必须跟企业号上的相同
	
	/**
     * 微信公众平台 开发者验证入口
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 微信加密签名
        String signature = request.getParameter("signature");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");

        // 1、字典序排序
        String[] str = { token, timestamp, nonce };
        Arrays.sort(str);
        String bigStr = str[0] + str[1] + str[2];
        
        // 2、SHA1加密
        String digest = new SHA1().getDigestOfString(bigStr.getBytes()).toLowerCase();

        // 3、确认请求来至微信返回加密串给微信平台进行确认
        if (digest.equals(signature)) {
        	System.out.println("微信平台认证通过："+echostr);
            response.getWriter().print(echostr);
        }
    }
	
	/**
	 * 公众号——企业号：确认请求来自微信服务器
	 * @throws IOException 
	 */
	public void doGet2(HttpServletRequest request, HttpServletResponse response) throws IOException{
		
		// 微信加密签名 
		String sVerifyMsgSig = request.getParameter("msg_signature");
		// 时间戳
		String sVerifyTimeStamp = request.getParameter("timestamp");
		// 随机数
		String sVerifyNonce = request.getParameter("nonce");
		// 随机字符串
		String sVerifyEchoStr = request.getParameter("echostr");
		
		String sEchoStr; //需要返回的明文
		PrintWriter out = response.getWriter();  
		WXBizMsgCrypt wxcpt;
		try {
			wxcpt = new WXBizMsgCrypt(token, encodingAESKey, corpID);
			sEchoStr = wxcpt.VerifyURL(sVerifyMsgSig, sVerifyTimeStamp, sVerifyNonce, sVerifyEchoStr);
			// 验证URL成功，将echoStr返回
			out.print(sEchoStr);  
		} catch (AesException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * 消息的接收、处理、响应
	 * ​注：当普通微信用户向公众账号发消息时，微信服务器将POST请求 公众号开发者接入配置中填写的 "接口配置信息"中的URL地址，并post传入消息的XML数据包
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 消息的接收、处理、响应
		
		response.setCharacterEncoding("UTF-8");
		PrintWriter writer = response.getWriter();
		try {
			Map<String, String> msgMap = XmlUtil.xmlToMap(request);	//将微信
			String toUserName = msgMap.get("ToUserName");
			String fromUserName = msgMap.get("FromUserName");
			Long createTime = Long.valueOf(msgMap.get("CreateTime"));
			String msgType = msgMap.get("MsgType");
			String msgId = msgMap.get("MsgId");
			
			String content = msgMap.get("Content");
			
			System.out.println("\n\n收到来自：【"+fromUserName+"】的消息！");
			
			String xmlMessage = null;
			if(MessageUtil.MESSAGE_NEWS.equals(msgType)) {
				
				MessageUtil.initNewsMessage(fromUserName, toUserName);
			} else if(MessageUtil.MESSAGE_TEXT.equals(msgType) 
					|| MessageUtil.MESSAGE_IMAGE.equals(msgType)
					|| MessageUtil.MESSAGE_VIDEO.equals(msgType)) {
				
				// 关键字回复，根据客户发送给公众号指定的关键字信息来自动回复
				if("0".equals(content)) {	//公众号接收到发送来的0时，自动回复一条图文消息
					
					xmlMessage = MessageUtil.initMsgReplyDesc(fromUserName, toUserName, MessageUtil.replyDesc());
				}else if("1".equals(content)) {		//自动回复指定文本消息
					
					xmlMessage = MessageUtil.initText(fromUserName, toUserName, MessageUtil.testMsg());
				} else if("2".equals(content)) {	//自动回复指定图文消息
					
					xmlMessage = MessageUtil.initNewsMessage(fromUserName, toUserName);
				} else if("3".equals(content)) {	//自动回复指定图片消息
					
					xmlMessage = MessageUtil.initImage(fromUserName, toUserName);
				} else if("4".equals(content)) {	//自动回复指定视频消息
					
					xmlMessage = MessageUtil.initVideo(fromUserName, toUserName);
				} else if("5".equals(content)) {	//自动回复指定音乐消息
					
					xmlMessage = MessageUtil.initMusic(fromUserName, toUserName);
				} else if("6".equals(content)) {	//自动回复指定语音消息
					
					xmlMessage = MessageUtil.initVoice(fromUserName, toUserName);
				} else {
					
					TextMessage sendMsg = new TextMessage();
					sendMsg.setToUserName(fromUserName);
					sendMsg.setFromUserName(toUserName);
					sendMsg.setMsgType(MessageUtil.MESSAGE_TEXT);
					sendMsg.setCreateTime(new Date().getTime());
					sendMsg.setContent("您发送的消息是：\n\n"+content);
					xmlMessage = XmlUtil.objToXml(sendMsg, "xml");
				}
			
			// 接收事件推送
			} else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
				
				String eventType = msgMap.get("Event");
				if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)) {
					//xmlMessage = MessageUtil.initText(fromUserName, toUserName, "你的微信openId="+fromUserName+"，感谢你订阅本公众号！你的关注是对我最大的支持，李鑫在此表示感谢！献上一份小福利！");
					xmlMessage = MessageUtil.initNewsMessage(fromUserName, toUserName);
				} else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals(eventType)) {
					xmlMessage = MessageUtil.initText(fromUserName, toUserName, fromUserName+"_取消订阅本公众号！");
				} else if(MessageUtil.MESSAGE_SCAN.equals(eventType)) {
					xmlMessage = MessageUtil.initText(fromUserName, toUserName, content);
				} else if(MessageUtil.MESSAGE_CLICK.equals(eventType)) {
					xmlMessage = MessageUtil.initText(fromUserName, toUserName, content);
				} else if(MessageUtil.MESSAGE_LOCATION.equals(eventType)) {
					xmlMessage = MessageUtil.initText(fromUserName, toUserName, content);
				}
			}
			
			System.out.println("回复消息内容：\n"+xmlMessage);
			if(StringUtils.isNotBlank(xmlMessage)) {
				writer.write(xmlMessage);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		} finally {
			if(writer != null) {
				writer.close();
			}
		}
	}
}
