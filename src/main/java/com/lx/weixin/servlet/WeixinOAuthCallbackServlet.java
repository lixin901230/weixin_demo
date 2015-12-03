package com.lx.weixin.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.httpClient.HttpClientUtil;
import com.lx.weixin.service.IUserLoginRegsiterService;
import com.lx.weixin.spring.util.ApplicationContextHelper;

/**
 * 微信第三方网页授权回调
 * 授权回调地址：https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
 * 
 * 网页授权流程详见微信文档：http://mp.weixin.qq.com/wiki/9/01f711493b5a02f24b04365ac5d8fd95.html
 * 用户管理——》网页授权获取用户基本信息
 */
public class WeixinOAuthCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private IUserLoginRegsiterService userLoginRegsiterService; 
	
	@Override
	public void init() throws ServletException {
		try {
			userLoginRegsiterService = (IUserLoginRegsiterService) ApplicationContextHelper.getBean("userLoginRegsiterService");
			if(userLoginRegsiterService == null) {
				throw new Exception("初始化 IUserLoginRegsiterService bean 失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		String url = URLEncoder.encode("http://www.lixinsj.com.cn/weixin/wxOAuthCallback.do", "UTF-8");
		System.out.println(url);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String code = request.getParameter("code");		//获取微信平台回调传入参数，通过该code换取网页授权access_token
			String state = request.getParameter("state");	//获取微信平台回调传入参数
			
			String unionId = "";			//微信账号唯一标示
			String WebPageAccessToken = "";	//网页授权access_token
			
			// 通过微信平台回调传入的code获取网页授权access_token 和 微信用户唯一标示 UnionId
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
			HttpClient httpClient = new HttpClient();
			GetMethod method = new GetMethod(url);
			int httpState = httpClient.executeMethod(method);
			if(httpState == 200) {
				String resultString = method.getResponseBodyAsString();
				JSONObject json = JSONObject.fromObject(resultString);
				unionId = json.getString("unionid");
				WebPageAccessToken = json.getString("access_token");
				
			} else {
				logger.error("调用微信平台失败，通过微信平台code获取网页授权access_token和UnionId失败！");
			}
			
			Map<String, Object> params = new HashMap<String, Object>();
			int flag = userLoginRegsiterService.saveUser(params);
			if(flag > -1) {	//注册成功
				
			}
			
			System.out.println("doGet");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
	}

}
