package com.lx.weixin.accountmanage;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.httpClient.HttpClientUtil;
import com.lx.weixin.util.AccessTokenUtil;
import com.lx.weixin.util.FileUtil;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.UUIDUtil;

/**
 * 二维码生成工具类
 * @author lixin
 *
 */
public class QRCodeUtil {
	
	private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);
	
	public static void main(String[] args) {
		
		try {
			AccessToken accessToken = AccessTokenUtil.getAccessToken();
			String token = accessToken.getToken();
			getQrCode(token);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 获取二维码
	 * @param accessToken	接口调用基础token
	 * @throws IOException 
	 */
	public static void getQrCode(String accessToken) throws IOException {
		
		JSONObject jsonObject = createQRCodeTicket(accessToken);
		String ticket = jsonObject.getString("ticket");
//		String qrExpire = jsonObject.getString("expire_seconds");
//		String qrImgUrl = jsonObject.getString("url");
		
		System.out.println("QR Code Ticket="+ticket);
		
		String qrCodeImgCont = exchangeQrCode(ticket);
		System.out.println(qrCodeImgCont);
		
	}
	
	
	/**
	 * 创建二维码ticket
	 * @param accessToken	接口调用基础token
	 */
	public static JSONObject createQRCodeTicket(String accessToken) {
		
		try {
			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+accessToken;
//			String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
			Map<String, String> parameter = new HashMap<String, String>();
//			parameter.put("access_token", accessToken);
			parameter.put("expire_seconds", "600");
			parameter.put("action_name", "QR_SCENE");
			parameter.put("action_info", "{\"scene\": {\"scene_str\": \"lixin_qr_code\"}");
			parameter.put("scene_id", UUIDUtil.id());
//			parameter.put("scene_str", UUIDUtil.id());
			
			String str = JsonUtil.objToStr(parameter);
			
			JSONObject jsonObject = HttpClientUtil.doPostStr(url, str);
			if(jsonObject.has("errcode")) {
				System.out.println("创建二维码ticket失败，原因："+jsonObject.toString());
			}
			return jsonObject;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过ticket换取二维码
	 * @param ticket	创建二维码后返回的用于换取二维码图片的ticket
	 */
	public static String exchangeQrCode(String ticket) {
		
		try {
			ticket = URLEncoder.encode(ticket, "UTF-8");
			String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
			
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			GetMethod getMethod = new GetMethod(url);
			int stateCode = httpClient.executeMethod(getMethod);
			if(stateCode == 200) {
				String responseBody = getMethod.getResponseBodyAsString();
				
				return responseBody;
			} else {
				logger.error("创建二维码ticket失败，httpclient 请求状态："+stateCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
