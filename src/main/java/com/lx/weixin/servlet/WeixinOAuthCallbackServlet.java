package com.lx.weixin.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 微信第三方网页授权回调
 * 授权回调地址：https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
 * 
 * 网页授权流程详见微信文档：http://mp.weixin.qq.com/wiki/9/01f711493b5a02f24b04365ac5d8fd95.html
 * 用户管理——》网页授权获取用户基本信息
 */
public class WeixinOAuthCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) throws Exception {
		String url = URLEncoder.encode("http://www.lixinsj.com.cn/weixin/wxOAuthCallback.do", "UTF-8");
		System.out.println(url);
	}
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
	}
	
}
