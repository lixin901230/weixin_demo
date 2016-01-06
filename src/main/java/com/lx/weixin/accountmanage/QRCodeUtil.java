package com.lx.weixin.accountmanage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.httpClient.HttpClientUtil;
import com.lx.weixin.util.AccessTokenUtil;
import com.lx.weixin.util.FileUtil;
import com.lx.weixin.util.JsonUtil;
import com.lx.weixin.util.ResultHandle;
import com.lx.weixin.util.UUIDUtil;

/**
 * 二维码生成工具类</br>
 * 通过微信公众平台接口生成二维码
 * 
 * @author lixin
 *
 */
public class QRCodeUtil {
	
	private static Logger logger = LoggerFactory.getLogger(QRCodeUtil.class);
	
	public static void main(String[] args) {
		
		try {
			String filePath = FileUtil.getIncomingDirPath();	//文件存放路径
			String fileName = "qr_weixin";						//文件名称
			String fileExt = ".jpg";							//文件扩展名
			File qrFile = new File(filePath, fileName + fileExt);
			if(qrFile.exists()) {		//判断文件是否存在，存在则更换文件名加上时间戳后缀
				fileName = fileName + "_" + new Date().getTime();
			}
			fileName += fileExt;

			// 从微信公众平台获取微信二维码
			generateQrCode(filePath, fileName);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成二维码
	 * @param filePath
	 * @param fileName
	 * @throws IOException
	 */
	public static Map<String, Object> generateQrCode(String filePath, String fileName) throws IOException {
		
		boolean success = false;	//成功失败标记；true：成功，false：失败
		String msg = "获取二维码成功！";
		try {
			// 1、获取微信接口调用认证token
			String accessToken = AccessTokenUtil.getToken();
			
			// 2、获取微信二维码ticket，用于去微信平台换取二维码图片
			JSONObject jsonObject = createQRCodeTicket(accessToken);
			String ticket = jsonObject.getString("ticket");
			String qrExpire = jsonObject.getString("expire_seconds");
			String qrImgUrl = jsonObject.getString("url");
			
			// 3、通过二维码ticket换取二维码图片
			byte[] qrCodeBytes = exchangeQrCode(ticket);
			
			// 4、下载二维码图片到指定路径
			downloadQRCodeImg(qrCodeBytes, filePath, fileName);
			
			success = true;
			
		} catch (Exception e) {
			success = false;
			msg = "获取二维码失败！";
			e.printStackTrace();
		}
		return ResultHandle.getResultMap(success, msg);
	}
	
	
	/**
	 * 获取二维码ticket，用于换取二维码图片时使用
	 * @param accessToken	接口调用认证token
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
			if(jsonObject != null && jsonObject.has("errcode")) {
				throw new Exception("创建二维码ticket失败，原因："+jsonObject.toString());
			}
			
			logger.info("\n>>>>>>换取二维码的ticken ["+jsonObject.getString("ticket")+"]\n");
			return jsonObject;
		} catch (Exception e) {
			logger.error("\n>>>>>>创建二维码ticket失败\n");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 通过ticket换取二维码</br>
	 * 注意：此处换取二维码也可在浏览器中直接访问url（https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=网页授权token）即可获取到二维码图片
	 * @param ticket	创建二维码后返回的用于换取二维码图片的ticket
	 */
	public static byte[] exchangeQrCode(String ticket) {
		
		try {
			ticket = URLEncoder.encode(ticket, "UTF-8");
			String url = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
			
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			GetMethod getMethod = new GetMethod(url);
			int stateCode = httpClient.executeMethod(getMethod);
			if(stateCode == 200) {
				byte[] responseBody = getMethod.getResponseBody();
				logger.info("\n>>>>>>ticket换取二维码成功！\n");
				return responseBody;
			} else {
				throw new Exception("创建二维码ticket失败，httpclient 请求状态："+stateCode);
			}
		} catch (Exception e) {
			logger.error("\n>>>>>>ticket换取二维码失败！\n");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 下载二维码图片
	 * @param qrCodeBytes	httpclient请求返回的二维码图片byte数组
	 * @param filePath		二维码图片保存的路径
	 * @param fileName		二维码图片名称
	 */
	public static void downloadQRCodeImg(byte[] qrCodeBytes, String filePath, String fileName) {
		
		FileOutputStream fos = null;
		try {
            File file = new File(filePath, fileName);//可以是任何图片格式.jpg,.png等
            fos = new FileOutputStream(file);
            fos.write(qrCodeBytes);
            fos.flush();
            fos.close();
            
            logger.info("\n>>>>>>下载二维码图片成功，文件路径为："+ file.getAbsolutePath()+"\n");
        } catch (Exception e) {
        	logger.error("\n>>>>>>下载二维码图片失败！\n");
            e.printStackTrace();
        } finally {
        	if(fos != null) {
        		try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }
	}
	
}
