package com.lx.weixin.servlet.qrCodeLogin;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.qrcode.ZXingQRCodeUtil;
import com.lx.weixin.service.IUserLoginRegsiterService;
import com.lx.weixin.servlet.core.DispatchServletSupport;
import com.lx.weixin.spring.bean.UserInfo;
import com.lx.weixin.spring.util.ApplicationContextHelper;
import com.lx.weixin.util.CommonUtils;
import com.lx.weixin.util.FileUtil;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.ResultHandle;

/**
 * 网站手机扫二维码登录——》手机端扫描登录
 * @author lixin
 */
public class PhoneQrCodeLoginServlet extends DispatchServletSupport {
	
	private static final long serialVersionUID = -4297336984087600007L;
	
	public static Logger logger = LoggerFactory.getLogger(PhoneQrCodeLoginServlet.class);
	
	private static final String LOGIN_QR_CODE_PATH_NAME = "incoming";
	
	private IUserLoginRegsiterService userLoginRegsiterService;
	
	private static final String CUR_LOGIN_USER = "cur_Login_User";	//当前登录用户session的key
	
	public static Map<String, UserInfo> loginUserMap = new HashMap<String, UserInfo>();
	
	@Override
	public void init() throws ServletException {
		
		userLoginRegsiterService = (IUserLoginRegsiterService) ApplicationContextHelper.getBean("userLoginRegsiterService");
		if(userLoginRegsiterService == null) {
			logger.error("初始化 IUserLoginRegsiterService bean 失败");
		}
	}
	
	/**
	 * 获取二维码图片地址
	 */
	public void getQrCodeUrl(HttpServletRequest request, HttpServletResponse response) {
		
		boolean success = true;
		String uuid = CommonUtils.uuid();
		String qrCodeName = "login_qrcode_"+ uuid + (int) (new Date().getTime() / 1000) + ".jpg";
		String filePath = FileUtil.getIncomingDirPath() + qrCodeName;
		String deplyPath = CommonUtils.getDeplyPath(request);
		String content = "http://www.lixinsj.com.cn/weixin/page/qrCodeLogin/login.jsp?uuid="+ uuid;
		
		String qrCodeImgUrl = "";
		try {
			ZXingQRCodeUtil qrCodeUtil = new ZXingQRCodeUtil();
			qrCodeUtil.generateQRCodeImage(filePath, content);
			qrCodeImgUrl = CommonUtils.getDeplyPath(request) +"/"+ LOGIN_QR_CODE_PATH_NAME +"/"+ qrCodeName;
		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}
		
		logger.info("\n>>>>>>生成的登录二维码图片地址："+qrCodeImgUrl+"\n");
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("uuid", uuid);
		result.put("qrCodeUrl", qrCodeImgUrl);
		Map<String, Object> resultDataMap = ResultHandle.getResultMap(success, result);
		String jsonStr = JsonUtil.objToStr(resultDataMap);
		JsonUtil.writeJsonStr(response, jsonStr);
	}
	
	/**
	 * 手机端扫描二维码进入手机登录页面后使用用户密码登录
	 * @param request
	 * @param response
	 */
	public void phoneLogin(HttpServletRequest request, HttpServletResponse response) {
		String uuid = request.getParameter("uuid");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		
		logger.info("\n>>>>>>uuid："+uuid+"\n");
		UserInfo user = userLoginRegsiterService.getUserInfo(userName, password);
		if(uuid != null && user != null) {
			request.getSession().setAttribute(CUR_LOGIN_USER, user);
			loginUserMap.put(uuid, user);
			logger.info("\n>>>>>>用户："+user.getUserName()+"登录成功！\n");
		}
		Map<String, Object> resultDataMap = ResultHandle.getResultMap(true, "登录成功");
		String jsonStr = JsonUtil.objToStr(resultDataMap);
		JsonUtil.writeJsonStr(response, jsonStr);
	}

	public static Map<String, UserInfo> getLoginUserMap() {
		return loginUserMap;
	}
	public static void setLoginUserMap(Map<String, UserInfo> loginUserMap) {
		PhoneQrCodeLoginServlet.loginUserMap = loginUserMap;
	}
}
