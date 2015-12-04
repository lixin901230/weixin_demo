package com.lx.weixin.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.service.IUserLoginRegsiterService;
import com.lx.weixin.spring.bean.UserInfo;
import com.lx.weixin.spring.util.ApplicationContextHelper;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.UUIDUtil;

/**
 * 用户注册、登录Servlet
 */
public class LoginRegsiterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Logger logger = LoggerFactory.getLogger(getClass());
	
	private IUserLoginRegsiterService userLoginRegsiterService;
	
	@Override
	public void init() throws ServletException {
		
		userLoginRegsiterService = (IUserLoginRegsiterService) ApplicationContextHelper.getBean("userLoginRegsiterService");
		if(userLoginRegsiterService == null) {
			logger.error("初始化 IUserLoginRegsiterService bean 失败");
		}
	}
    
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if("register".equals(method)) {	//注册
			register(request);
		} else if("login".equals(method)) {	//登录
			login(request);
		}
	}
	
	/**
	 * 用户注册
	 */
	public void register(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean success = false;
		String errorMsg = "";
		
		String id = UUIDUtil.id();
		String unionId = request.getParameter("unionId");	//微信账号唯一标识，注册时将当前注册的账号与微信用户唯一标示UnionId进行绑定，便于自动登录
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String trueName = request.getParameter("trueName");
		String nickName = request.getParameter("nickName");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String validFlag = request.getParameter("validFlag");
		Date createDate = new Date();
		UserInfo user = new UserInfo(id, 
				userName, password, trueName, 
				unionId, nickName, sex, email, 
				phone, validFlag, createDate);
		
		int flag = userLoginRegsiterService.saveUser(user);
		String redirectUrl = "/page/weixin/open/autologin.html";
	}
	
	/**
	 * 登录
	 */
	public void login(HttpServletRequest request) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		boolean success = false;
		String errorMsg = "";
		
		String unionId = request.getParameter("unionId");	//微信账号唯一标识，注册时将当前注册的账号与微信用户唯一标示UnionId进行绑定，便于自动登录
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		UserInfo user = userLoginRegsiterService.login(userName, password);
		request.getSession().setAttribute("sessionUser", user);
	}

}
