package com.lx.weixin.servlet;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.servlet.core.DispatchServletSupport;
import com.lx.weixin.util.AccessTokenUtil;
import com.lx.weixin.util.CommonConst;
import com.lx.weixin.util.CommonUtils;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.ResultHandle;

/**
 * 微信JS-SDK使用接入与签名<br/>
 * 1）、获取微信jsapi使用票据（ jsapi_ticket)<br/>
 * 2）、获取JS-SDK使用权限签名（使用SHA1算法）
 * 
 * @author lixin
 * 
 */
public class WeixinJsSdkValidateServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = 3832691448389040033L;
	private static Logger logger = LoggerFactory.getLogger(WeixinJsSdkValidateServlet.class);
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		
		JSONObject jsApiTicketJsonObj = getJsApiTicketJsonObj();
		//String result = (jsApiTicketJsonObj != null) ? jsApiTicketJsonObj.toString() : "获取japi_ticket失败！";
		//System.out.println(result);
		
		if(jsApiTicketJsonObj != null) {
			
			String jsApiTicket = jsApiTicketJsonObj.getString("ticket");
			logger.info("\n>>>>>>jsApiTicket："+jsApiTicket+"\n");
			
			String url = "http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp";
			Map<String, String> params = wxJsSdkSignature(CommonConst.APPID, jsApiTicket, url);
			logger.info("\n>>>>>>jsSdkSignature："+params.get("signature")+"\n");
		}
	}
	
	/**
	 * 获取微信js-sdk调试配置
	 * @param jsApiTicket
	 * @param url
	 * @return
	 */
	public void getWxJsSdkConfig(HttpServletRequest request, HttpServletResponse response) {
		
		String appId = request.getParameter("appId");	//	检测是否传入了appId,未传在使用系统中配置的公众号的appId
		if(StringUtils.isEmpty(appId)) {
			appId = CommonConst.APPID;
		}
		
		String url = getRequestUrl(request);	//获取请求地址
		String jsApiTicket = getJsApiTicket();
		
		Map<String, String> params = wxJsSdkSignature(appId, jsApiTicket, url);
		
		JSONObject jsonObject = JsonUtil.objToJson(ResultHandle.getResultDataMap(params));
		String jsonStr = jsonObject.toString();
		logger.info("\n>>>>>>微信JS-SDK接入配置："+jsonStr+"\n");
		
		JsonUtil.writeJsonStr(response, jsonStr);
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
	 * @param appId
	 * 			微信公众号appId
	 * 
	 * @param jsApiTicket
	 * 			jsapi使用票据
	 * 
	 * @param url	
	 * 			当前网页的URL，不包含#及其后面部分
	 * 
	 * @return
	 */
	public static Map<String, String> wxJsSdkSignature(String appId, String jsApiTicket, String url) {
		
		if(url.contains("#")) {	//签名算法要求：当前网页的URL，不包含#及其后面部分
			url = url.substring(0, url.indexOf("#"));
		}
		
        Map<String, String> result = new HashMap<String, String>();
        String noncestr = createNonceStr();
        String timestamp = createTimestamp();
        String signature = "";
 
        //注意这里参数名必须全部小写，且必须有序
        String str = "jsapi_ticket=" + jsApiTicket +
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
        
        result.put("appid", appId);
        result.put("url", url);
        result.put("jsapi_ticket", jsApiTicket);
        result.put("noncestr", noncestr);
        result.put("timestamp", timestamp);
        result.put("signature", signature);
 
        return result;
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