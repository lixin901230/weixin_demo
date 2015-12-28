package com.lx.weixin.jssdk;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.util.AccessTokenUtil;
import com.lx.weixin.util.JsonUtil;

/**
 * 获取 sapi_ticket接入微信JS-SDK
 * @author lx
 *
 */
public class WeixinJsSdkUtils {
	
	private static Logger logger = LoggerFactory.getLogger(WeixinJsSdkUtils.class);

	public static void main(String[] args) {
		String jApiTickentStr = getJApiTickentStr();
		//System.out.println("\n"+jApiTickentStr);
		
		JSONObject jApiTicketJsonObj = getJApiTicketJsonObj();
		String result = (jApiTicketJsonObj != null) ? jApiTicketJsonObj.toString() : "获取japi_ticket失败！";
		//System.out.println(result);
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
}
