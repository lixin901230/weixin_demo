package com.lx.weixin.servlet.qrCodeLogin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.servlet.core.DispatchServletSupport;
import com.lx.weixin.spring.bean.UserInfo;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.ResultHandle;

/**
 * 网站手机扫二维码登录——》服务端轮询（或长连接）监听手机端登录
 * @author lixin
 */
public class LongConnectionCheckServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = -6216698186845500756L;
	
	public static Logger logger = LoggerFactory.getLogger(LongConnectionCheckServlet.class);
	
	/**
	 * 检查手机端扫二维码登录状态
	 */
	public void checkPhoneLoginState(HttpServletRequest request, HttpServletResponse response) {
		String uuid = request.getParameter("uuid");
		UserInfo userInfo = null;
		boolean flag = true;
		//此处仅为了模拟长连接监听移动端扫码后的登录状态，需要优化改成websocket长连接形式
		while(flag) {
			userInfo = PhoneQrCodeLoginServlet.getLoginUserMap().get(uuid);
			if(userInfo != null) {
				logger.info(">>>>>>PC端登录成功！");
				PhoneQrCodeLoginServlet.getLoginUserMap().remove(uuid);
				flag = false;
			}
		}
		String jsonStr = JsonUtil.objToStr(ResultHandle.getResultMap(true, userInfo));
		JsonUtil.writeJsonStr(response, jsonStr);
	}
}
