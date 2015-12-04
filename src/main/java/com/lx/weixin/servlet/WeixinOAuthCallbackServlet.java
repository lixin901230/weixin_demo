package com.lx.weixin.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.httpClient.HttpClientUtil;
import com.lx.weixin.service.IUserLoginRegsiterService;
import com.lx.weixin.spring.bean.UserInfo;
import com.lx.weixin.spring.util.ApplicationContextHelper;
import com.lx.weixin.util.LoadWeixinPropertiesConfig;
import com.lx.weixin.util.WeixinConst;

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
	
	private static String APPID;
	private static String APP_SECRET;
	private static String GRANT_TYPE = "authorization_code";
	
	private String unionId = "";	//微信账号唯一标示
	
	private String redirectUrl;	//微信平台第三方网页授权成功并回调我们的平台进行重定向跳转进入我们的微站
	
	public static void main(String[] args) throws Exception {
		String url = URLEncoder.encode("http://www.lixinsj.com.cn/weixin/wxOAuthCallback.do", "UTF-8");
		System.out.println(url);
	}

	@Override
	public void init() throws ServletException {
		try {
			userLoginRegsiterService = (IUserLoginRegsiterService) ApplicationContextHelper.getBean("userLoginRegsiterService");
			if(userLoginRegsiterService == null) {
				throw new Exception("初始化 IUserLoginRegsiterService bean 失败");
			}
			
			Properties properties = LoadWeixinPropertiesConfig.getInstance().getConfig();
			if(properties != null) {
				APPID = properties.getProperty("appid");
				APP_SECRET = properties.getProperty("appsecret");
				GRANT_TYPE = properties.getProperty("grant_type");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
       
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			
			String forwardPageName = wxOauthCallback(request);
			request.setAttribute("redirectUrl", redirectUrl);	//将跳转地址传入到自动登录页面，登录成功后重定向到登录后的我的主页
			String forwardUrl = "/page/weixin/open/"+ forwardPageName +".html";
			request.getRequestDispatcher(forwardUrl).forward(request, response);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 通过code换取网页授权access_token
	 * @return
	 * @throws Exception
	 */
	public String wxOauthCallback(HttpServletRequest request) {
		
		String logInfo = "";
        try {
			String code = request.getParameter("code");		//微信平台返回的code，用于换取网页授权access_token的票据，每次用户授权带上的code将不一样，code只能使用一次，5分钟未被使用自动过期
			String state = request.getParameter("state");	//微信平台返回的state，为用户授权请求时的url参数（开发者自己定义的url参数）
			if(StringUtils.isNotEmpty(code)) {
			    JSONObject accessTokenJson = getOAuthAccessTokenByCode(code);
			    if(accessTokenJson != null) {
			    	
			    	unionId = accessTokenJson.getString("unionid");
			        String openId = accessTokenJson.getString("openid");
			        
			        request.getSession().setAttribute(WeixinConst.SESSION_UNIONID, unionId);	//存入unionId到session中，方便页面取值
			        
			        //unionId = "oZ6H7t2tWAU5ipECOe5m44U9b3jU";
			       // openId = "oT0b-jqYdPi92LaKybuUptyfrGWQ";
			        
			        String accessToken = accessTokenJson.getString("access_token");
			        boolean isValid = checkOAuthAccessToken(accessToken, openId);
			        if(isValid) {
			        	
			        	// 微信公众号进入mooc微站，检查该微信用户是否与mooc微站平台账号绑定，已绑定用户将自动登录后进入mooc首页，未注册用户 或 未与微信公众号union绑定的用户不进行登录直接进入mooc首页
			        	UserInfo user = userLoginRegsiterService.getUserInfoByUnionId(unionId);
			        	if(user != null) {
				        	if (WeixinConst.WEIXIN_LOGIN_STATE.equals(state)) {
				        		redirectUrl = "/page/weixin/auth/myIndex.jsp";	//自动登录成功后重定向的地址
				        	} 
				        	return "autologin";	//去自动登录
			        	}
			        	if (WeixinConst.WEIXIN_LOGIN_STATE.equals(state)) {
			        		return "login";	//去手动登录
			        	} else {
			        		return "index";	//去首页（不需要登录）
			        	}
			        	
			        } else {
			        	logInfo = "微信网页授权access_token失效";
			        }
			    }
			} else {
				logInfo = "微信网页授权缺少oauth code";
			}
			logger.error(logInfo);
		} catch (Exception e) {
			logger.error("微信公众号请求网页授权回调失败，原因："+e);
			e.printStackTrace();
		}
        return "error";
	}

	/**
	 * 获取网页授权access_token和unionid及openid等信息（通过微信公众平台OAuth2认证后返回的code进行获取）
	 * @param code	微信平台oauth2认证后返回的code
	 * @return
	 */
	public JSONObject getOAuthAccessTokenByCode(String code) {
		
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("appid", APPID);
		parameter.put("secret", APP_SECRET);
		parameter.put("grant_type", GRANT_TYPE);
		parameter.put("code", code);
		
		try {
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			GetMethod method = HttpClientUtil.getMethod(url, parameter);
			int stateCode = httpClient.executeMethod(method);
			String responseBody = null;
			if(stateCode == 200) {
				responseBody = method.getResponseBodyAsString();
				JSONObject accessTokenJson = JSONObject.fromObject(responseBody);
				Object errcode = accessTokenJson.get("errcode");
				if(errcode == null) {
					return accessTokenJson;
				} else {
					logger.error(errcode + ":" + accessTokenJson.get("errmsg"));
					return null;
				}
			} else {
				logger.error("HttpClient 调用微信平台失败，通过微信平台code获取网页授权access_token和UnionId失败！httpclient state code="+stateCode);
			}
		} catch (HttpException e) {
			e.printStackTrace();
			logger.error("获取网页授权失败，error:"+e);
		} catch (IOException e) {
			e.printStackTrace();
			logger.error("获取网页授权失败，error:"+e);
		}
		logger.error("请求微信失败");
        return null;
	}
	
	/**
	 * 验证网页授权access_token是否有效（此access_token与基础支持进行接口调用的access_token不同）
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public boolean checkOAuthAccessToken(String accessToken, String openId) {
		
		String url = "https://api.weixin.qq.com/sns/auth";
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("access_token", accessToken);
		parameter.put("openid", openId);
		
		try {
			GetMethod method = HttpClientUtil.getMethod(url, parameter);
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			int stateCode = httpClient.executeMethod(method);
			if(stateCode == 200) {
				String responseBody = method.getResponseBodyAsString();
				JSONObject accessTokenJson = JSONObject.fromObject(responseBody);
				String errcode = accessTokenJson.getString("errcode");
				if(StringUtils.isNotBlank(errcode) && "0".endsWith(errcode)) {
					return true;
				} else {
					logger.error(errcode + ":" + accessTokenJson.get("errmsg"));
					return false;
				}
			} else {
				logger.error("HttpClient 调用微信平台失败，通过微信平台code获取网页授权access_token和UnionId失败！httpclient state code="+stateCode);
			}
		} catch (Exception e) {
			logger.error("请求微信验证网页授权access_token失败，error："+e);
			e.printStackTrace();
		}
		logger.error("验证微信网页授权access_token失败");
		return false;
	}
}
