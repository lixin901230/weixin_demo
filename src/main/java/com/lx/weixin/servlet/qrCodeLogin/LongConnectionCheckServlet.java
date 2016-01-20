package com.lx.weixin.servlet.qrCodeLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.servlet.core.DispatchServletSupport;
import com.lx.weixin.spring.bean.UserInfo;

/**
 * 网站手机扫二维码登录——》服务端轮询（或长连接）监听手机端登录
 * @author lixin
 */
public class LongConnectionCheckServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = -6216698186845500756L;
	
	public static Logger logger = LoggerFactory.getLogger(LongConnectionCheckServlet.class);
	
	private static final String CUR_LOGIN_USER = "cur_Login_User";	//当前登录用户session的key
	
	/**
	 * 检查手机端扫二维码登录状态
	 */
	public void checkPhoneLoginState(HttpServletRequest request, HttpServletResponse response) {
		String uuid = request.getParameter("uuid");
		boolean flag = true;
		while(flag) {
			UserInfo userInfo = PhoneQrCodeLoginServlet.getLoginUserMap().get(uuid);
			if(userInfo != null) {
				request.getSession().setAttribute(CUR_LOGIN_USER, userInfo);
				PhoneQrCodeLoginServlet.getLoginUserMap().remove(uuid);
				flag = false;
				logger.info("\n>>>>>>用户："+userInfo.getUserName()+"登录成功！\n");
			}
		}
	}
}
