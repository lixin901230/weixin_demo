package com.lx.weixin.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.httpClient.HttpClientUtil;

/**
 * 申请开通摇一摇周边功能工具类
 * @author lixin
 *
 */
public class WeixinShakeApplyUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MenuButtonUtil.class);

	/**
	 * 测试
	 * @param args
	 */
	public static void main(String[] args) {
		//申请微信摇一摇周边功能权限
//		System.out.println(applyOpenShake().toString());
		
		//查询审核状态
		System.out.println(queryCheckState().toString());
	}
	
	/**
	 * 申请开通摇一摇周边功能
	 * @return
	 */
	public static JSONObject applyOpenShake() {
		
		String token = AccessTokenUtil.getToken();
		String url = "https://api.weixin.qq.com/shakearound/account/register?access_token="+token;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("access_token", token);
		paramMap.put("name", "李鑫");
		paramMap.put("phone_number", "18701216683");
		paramMap.put("email", "1127295539@qq.com");
		paramMap.put("industry_id", "1204");	//机构组织——>其他组织
		paramMap.put("qualification_cert_urls", new String[]{});
		paramMap.put("apply_reason", "test");
		String parameter = JsonUtil.objToStr(paramMap);
		PostMethod method = new PostMethod(url);
		HttpClient httpClient = new HttpClient();
		try {
			int state = httpClient.executeMethod(method);
			
			if(state == 200) {
				JSONObject jsonObject = HttpClientUtil.doPostStr(url, parameter);
				if(jsonObject != null && String.valueOf(jsonObject.get("errcode")).equals("0")) {
					return jsonObject;
				} else {
					logger.error("\n>>>>>>申请开通摇一摇周边功能失败，原因："+jsonObject.toString()+"\n");
				}
			} else {
				logger.error("\n>>>>>>申请开通摇一摇周边功能微信平台接口调用失败，http请求状态："+state+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询审核状态
	 * @return
	 */
	public static JSONObject queryCheckState() {
		
		String token = AccessTokenUtil.getToken();
		String url = "https://api.weixin.qq.com/shakearound/account/auditstatus?access_token="+token;
		GetMethod method = new GetMethod(url);
		HttpClient httpClient = new HttpClient();
		try {
			int state = httpClient.executeMethod(method);
			if(state == 200) {
				String bodyAsString = method.getResponseBodyAsString();
				JSONObject jsonObject = JsonUtil.strToJson(bodyAsString);
				if(jsonObject != null && String.valueOf(jsonObject.get("errcode")).equals("0")) {
					return jsonObject;
				} else {
					logger.error("\n>>>>>>申请开通摇一摇周边功能失败，原因："+jsonObject.toString()+"\n");
				}
			} else {
				logger.error("\n>>>>>>查询申请开通摇一摇周边功能审核状态接口调用失败，http请求状态："+state+"\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
