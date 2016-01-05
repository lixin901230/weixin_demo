package com.lx.weixin.servlet.core;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * servlet请求分发
 * <ul>
 * 	支持三种请求方法解析方式，例如：
 * 	<li>1：.../servletName?method=methodName[(.*)&参数key1=参数value1&...]</li>
 * 	<li>2：.../servletName/methodName[(.*)?参数key1=参数value1&...]</li>
 * </ul>
 * 	(注：方括号[]中的内容为可省内容，具体根据个人贵方和业务需要编写地址)
 * @author lixin
 */
//* <li>（暂不支持）方式3：.../servletName_methodName[(.*)?参数key1=参数value1&...]</li>
public abstract class DispatchServletSupport extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			execute(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取请求地址
	 * @param request
	 * @return	eg：
	 */
	public String getRequestUrl(HttpServletRequest request) {
		
		String url = request.getRequestURL().toString();//eg. http://localhost:8080/weixin/LoginRegsiterServlet
		String queryUrl = request.getQueryString();	//eg. method=methodName.do&userName=zhangsan
		
		String completeUrl = url;
		if(StringUtils.isNotEmpty(queryUrl)) {
			completeUrl = completeUrl + "?" + queryUrl;
		}
		logger.info("["+getClass().getName()+"]：开始解析请求方法名，请求地址："+ completeUrl);
		return completeUrl;
	}

	/**
	 * 执行方法
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String methodName = methodNameHandle(request);
		logger.info("["+getClass().getName()+"]：开始反射执行方法["+methodName+"]...");
		
		if(StringUtils.isEmpty(methodName)) {
			throw new NoSuchMethodException("["+methodName+"]方法不存在——————>["+getClass().getName()+"]");
		}
		
		Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
		if(method != null) {
			method.setAccessible(true);	//释放私有方法调用访问权限
			method.invoke(this, request, response);
		}
		logger.info("["+getClass().getName()+"]：反射执行方法["+methodName+"]完成!");	
	}
	
	/**
	 * 通过url解析请求的方法名<br/>
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	public String methodNameHandle(HttpServletRequest request) throws Exception {
		
		//如完整请求：http://localhost:8080/weixin/LoginRegsiterServlet?method=checkUserNameIsExist.do&userName=zhangsan
		//String uri = request.getRequestURI();	//eg. /weixin/LoginRegsiterServlet
		String url = request.getRequestURL().toString();//eg. http://localhost:8080/weixin/LoginRegsiterServlet
		String queryUrl = request.getQueryString();	//eg. method=methodName.do&userName=zhangsan
		
		String completeUrl = url;
		if(StringUtils.isNotEmpty(queryUrl)) {
			completeUrl = completeUrl + "?" + queryUrl;
		}
		logger.info("["+getClass().getName()+"]：开始解析请求方法名，请求地址："+ completeUrl);
		
		String methodName = null;
		methodName = request.getParameter("method");	//请求方式1，如：.../servletName?methodName
		if(StringUtils.isEmpty(methodName)) {	
			String urlTemp = null;
			int paramsIndex = url.indexOf("?");
			if(paramsIndex > -1) {	//有url参数时，截取只要url部分，去掉参数部分
				urlTemp = url.substring(0, paramsIndex);
			} else {
				urlTemp = url;
			}
			urlTemp = url.substring(url.lastIndexOf("/")+1);	//截取最后一个斜杠/后的部分，判断是否存在下划线分隔的请求方法名，存在则根据斜杠截取出需要请求的方法名，不存在则将最后一个斜杠后的部分最为该请求的方法名
//			if(urlTemp.lastIndexOf("_") > -1) {	//请求方式2，如：.../servletName_methodName
//				methodName = urlTemp.substring(urlTemp.lastIndexOf("_"));
//			} else {
				methodName = urlTemp;	//请求方式3，如：.../servletName/methodName
//			}
		}
		if(StringUtils.isEmpty(methodName)) {
			throw new Exception("请求地址有误，根据请求地址解析请求方法失败，url：["+completeUrl+"]");
		}
		if(methodName.contains(".")) {	//判断是否拼有后缀
			methodName = methodName.substring(0, methodName.indexOf("."));
		}
		logger.info("["+getClass().getName()+"]：方法名解析成功，请求的方法名：["+methodName+"]");
		return methodName;
	}
	
}
