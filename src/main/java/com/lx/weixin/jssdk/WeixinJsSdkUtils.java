package com.lx.weixin.jssdk;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.util.AccessTokenUtil;
import com.lx.weixin.util.CommonConst;
import com.lx.weixin.util.JsonUtil;

/**
 * 获取 sapi_ticket接入微信JS-SDK
 * @author lx
 *
 */
public class WeixinJsSdkUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WeixinJsSdkUtils.class);

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		getJApiTickentStr();
	}
	
	/**
	 * 获取js-sdk接入所需要的sapi_ticket
	 * @return
	 */
	public static String getJApiTickentStr() {
		String jApiTicketStr = "";
		JSONObject jApiTicketObj = getJApiTicketJsonObj();
		if(jApiTicketObj != null) {
			jApiTicketObj.getString("ticket");
		}
		logger.info("\n>>>>>>获取js-sdk接入所需要的sapi_ticket成功；ticket："+jApiTicketStr+"\n");
		return jApiTicketStr;
	}
	
	/**
	 * 获取sapi_ticket的json对象
	 * @return
	 */
	public static JSONObject getJApiTicketJsonObj() {
		
		String accessToken = AccessTokenUtil.getAccessToken().getToken();
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			httpClient.executeMethod(method);
			String responseBody = method.getResponseBodyAsString();
			JSONObject jsonObject = JsonUtil.strToJson(responseBody);
			if("0".equals(jsonObject.getString("errcode"))){
				logger.info("\n>>>>>>获取js-sdk接入所需要的sapi_ticket成功； jsonResult："+jsonObject.toString()+"\n");
				return jsonObject;
			} else {
				logger.info("\n>>>>>>获取js-sdk接入所需要的sapi_ticket失败； jsonResult："+jsonObject.toString()+"\n");
				String errmsg = jsonObject.getString("errmsg");
				throw new Exception("请求微信获取sapi_ticket失败，原因："+errmsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取js-sdk接入所需要的sapi_ticket
	 * @return
	 */
	public static String getJsApiTicket() {
		String jsApiTicketStr = "";
		JSONObject jsApiTicketObj = getJsApiTicketJsonObj();
		if(jsApiTicketObj != null) {
			jsApiTicketStr = jsApiTicketObj.getString("ticket");
		}
		logger.info("\n>>>>>>获取js-sdk接入所需要的sapi_ticket成功；ticket："+jsApiTicketStr+"\n");
		return jsApiTicketStr;
	}
	
	/**
	 * 获取sapi_ticket的json对象
	 * @return
	 */
	public static JSONObject getJsApiTicketJsonObj() {
		
		String accessToken = AccessTokenUtil.getAccessToken().getToken();	//获取接口访问凭证
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
		HttpClient httpClient = new HttpClient();
		GetMethod method = new GetMethod(url);
		try {
			httpClient.executeMethod(method);
			String responseBody = method.getResponseBodyAsString();
			JSONObject jsonObject = JsonUtil.strToJson(responseBody);
			if("0".equals(jsonObject.getString("errcode"))){
				logger.info("\n>>>>>>获取js-sdk接入所需要的sapi_ticket成功； Request Result："+jsonObject.toString()+"\n");
				return jsonObject;
			} else {
				logger.info("\n>>>>>>获取js-sdk接入所需要的sapi_ticket失败； Request Result："+jsonObject.toString()+"\n");
				String errmsg = jsonObject.getString("errmsg");
				throw new Exception("请求微信获取sapi_ticket失败，原因："+errmsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 微信JS-SDK签名
	 * @param url	当前网页的URL，不包含#及其后面部分
	 * @return
	 */
	public static String wxJsSdkSignature(String jsApiTicket, String url) {
		
		//String jsApiTicket = getJsApiTicket();
		Map<String, String> params = sign(jsApiTicket, url);
		params.put("appid", CommonConst.APPID);
		JSONObject jsonObject = JsonUtil.objToJson(params);
		String jsonStr = jsonObject.toString();
		logger.info("\n>>>>>>微信JS-SDK签名："+jsonStr+"\n");
		return jsonStr;
	}
	
	public static Map<String, String> sign(String jsapi_ticket, String url) {
		
        Map<String, String> ret = new HashMap<String, String>();
        String noncestr = createNonceStr();
        String timestamp = createTimestamp();
        String signature = "";
 
        //注意这里参数名必须全部小写，且必须有序
        String str = "jsapi_ticket=" + jsapi_ticket +
                  "&noncestr=" + noncestr +
                  "&timestamp=" + timestamp +
                  "&url=" + url;
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(str.getBytes("UTF-8"));
            signature = byteToHex(crypt.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret.put("url", url);
        ret.put("jsapi_ticket", jsapi_ticket);
        ret.put("noncestr", noncestr);
        ret.put("timestamp", timestamp);
        ret.put("signature", signature);
 
        return ret;
    }
	
	private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
	
	private static String createNonceStr() {
        return UUID.randomUUID().toString();
    }
 
	private static String createTimestamp() {
        return Long.toString(System.currentTimeMillis() / 1000);
    }
     
    /**
     * 获取当前系统时间 用来判断access_token是否过期
     * @return
     */
    public static String getTime(){
    	Date dt=new Date();
        SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(dt);
	}
}
