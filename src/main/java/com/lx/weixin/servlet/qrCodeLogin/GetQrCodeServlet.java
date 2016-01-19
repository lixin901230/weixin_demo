package com.lx.weixin.servlet.qrCodeLogin;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lx.weixin.qrcode.ZXingQRCodeUtil;
import com.lx.weixin.servlet.core.DispatchServletSupport;
import com.lx.weixin.util.FileUtil;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.ResultHandle;

/**
 * 获取二维码Servlet
 * @author lixin
 *
 */
public class GetQrCodeServlet extends DispatchServletSupport {

	private static final long serialVersionUID = -7592765119136361697L;
	
	private static final String LOGIN_QR_CODE_PATH_NAME = "incoming";

	/**
	 * 获取二维码图片地址
	 */
	public void getQrCodeUrl(HttpServletRequest request, HttpServletResponse response) {
		
		String filePath = "";
		String content = "";
		ZXingQRCodeUtil qrCodeUtil = new ZXingQRCodeUtil();
		qrCodeUtil.generateQRCodeImage(filePath, content);
		
		String qrCodeName = "loginQrCode.jpg";
		String qrCodeUrl = request.getScheme() 
				+ "://" + request.getServerName() 
				+ ":" + request.getServerPort() 
				+ "/" + request.getServletPath() 
				+ "/" + LOGIN_QR_CODE_PATH_NAME 
				+ "/" + qrCodeName;
		Map<String, Object> resultDataMap = ResultHandle.getResultDataMap(qrCodeUrl);
		String jsonStr = JsonUtil.objToStr(resultDataMap);
		JsonUtil.writeJsonStr(response, jsonStr);
	}
	
}
