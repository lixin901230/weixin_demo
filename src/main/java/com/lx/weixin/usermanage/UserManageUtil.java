package com.lx.weixin.usermanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.httpClient.HttpClientUtil;
import com.lx.weixin.util.AccessTokenUtil;
import com.lx.weixin.util.WeixinURLUtil;

/**
 * 用户管理测试
 * @author lixin
 *
 */
public class UserManageUtil {
	
	
	public static void main(String[] args) {
		getUserList();
	}

	/**
	 * 获取用户列表
	 * @return
	 */
	public static Map<String, Object> getUserList() {
		
		Map<String, Object> resMap = new HashMap<String, Object>();
		
		String nextOpenId = "";
		
		String accessTokenStr = AccessTokenUtil.getToken();
		
		String url = WeixinURLUtil.GET_USER_LIST_URL.replace("/ACCESS_TOKEN/", accessTokenStr).replace("/NEXT_OPENID/", nextOpenId);
//		url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
		url = "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+accessTokenStr+"&next_openid="+nextOpenId;
		System.out.println("url="+url);
		try {
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			GetMethod method = new GetMethod(url);
			int state = httpClient.executeMethod(method);
			if(state == 200) {
				String rsStr = method.getResponseBodyAsString();
				JSONObject jsonObject = JSONObject.fromObject(rsStr);
				System.out.println("调用结果："+rsStr);
				
				//例如：{"total":8,"count":8,"data":{"openid":["ouXKWwECc2eLgZN7Hrw6Fqh2-9yM","ouXKWwC1h-wE8jv2kkcZwwCcRmUE"]},"next_openid":"ouXKWwO1vkFtyTso5EcD3zExXhNU"}
				Object total = jsonObject.get("total");
				Object count = jsonObject.get("count");
				Object data = jsonObject.get("data");
				JSONObject openIdJson = JSONObject.fromObject(data);
				Object openid = openIdJson.get("openid");
				Object next_openid = openIdJson.get("next_openid");
				
				resMap.put("total", total);
				resMap.put("count", count);
				resMap.put("next_openid", next_openid);
				resMap.put("openIds", openid);
			} else {
				System.out.println("获取用户列表失败");
			}
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return resMap;
	}
}
