package com.lx.weixin.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.cache.CacheUtils;
import com.lx.weixin.httpClient.HttpClientUtil;

/**
 * 微信接口访问凭证（access_token）获取工具类
 * 
 * @author lixin
 *
 */
public class AccessTokenUtil {
	
	private static Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);
	
	/** 微信接口调用凭证access_token缓存key */
	private static final String ACCESS_TOKEN_CACHE_KEY = "weixin_@_access_token_key";

	private static String APPID;
	private static String APPSECRET;
	private final static String GRANT_TYPE = "client_credential";
	
	/** 加载微信公众号账号配置 */
	static {
		Properties config = LoadWeixinPropertiesConfig.getInstance().getConfig();
		APPID = config.getProperty("appid");
		APPSECRET = config.getProperty("appsecret");
	}
	
	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		AccessToken accessToken = getAccessToken();
		System.out.println(accessToken.getToken());
	}
	
	/**
	 * 获取access_token	接口访问凭证
	 * @return
	 */
	public static String getToken() {
		return getAccessToken().getToken();
	}
	
	/**
	 * 获取access_token 对象
	 * @return
	 */
	public static AccessToken getAccessToken() {
		
		AccessToken accessToken = new AccessToken();
		
		// 从缓存中取AccessToken对象，若未获取到缓存的accessToken，则重新获取
		Object object = CacheUtils.get(ACCESS_TOKEN_CACHE_KEY);
		if(object != null) {
			logger.info("\n>>>>>>从缓存中获取AccessToken对象成功！\n");
			accessToken = (AccessToken) object;
		} else {
			
			logger.info("\n>>>>>>从缓存中获取AccessToken对象失败，开始重新调用微信平台获取access_token...\n");
			
			//重新获取AccessToken对象
			String url = WeixinURLUtil.ACCESS_TOKEN_URL.substring(0, WeixinURLUtil.ACCESS_TOKEN_URL.indexOf("?"));
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("grant_type", GRANT_TYPE);
			parameter.put("appid", APPID);
			parameter.put("secret", APPSECRET);
			try {
				HttpClient httpClient = HttpClientUtil.initHttpClient();
				GetMethod method = HttpClientUtil.getMethod(url, parameter);
				int state = httpClient.executeMethod(method);
				if(state == 200) {
					String respBodyStr = method.getResponseBodyAsString();
					JSONObject jsonObject = JsonUtil.strToJson(respBodyStr);
					if(jsonObject.has("access_token")) {
						
						String token = jsonObject.getString("access_token");
						String expiresInStr = jsonObject.getString("expires_in");
						accessToken = new AccessToken(token, NumberUtil.strToInteger(expiresInStr));
						
						CacheUtils.put(ACCESS_TOKEN_CACHE_KEY, accessToken, 5400);	//缓存AccessToken对象，由于access_token有效期为7200秒，故此处缓存有效期为90分钟（5400秒）
					} else {
						throw new Exception("获取access_token失败，原因："+jsonObject.toString());
					}
				}
				
				logger.info("\n>>>>>>微信公众号接口访问认证accessToken ["+accessToken.getToken()+"]\n");
			} catch (Exception e) {
				logger.error("\n>>>>>>获取access_token失败，原因："+e+"\n");
				e.printStackTrace();
			}
		}
		return accessToken;
	}
}
