package com.lx.weixin.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.bean.AccessToken;
import com.lx.weixin.httpClient.HttpClientUtil;
import com.lx.weixin.menu.Button;
import com.lx.weixin.menu.ClickButton;
import com.lx.weixin.menu.Menu;
import com.lx.weixin.menu.ViewButton;

/**
 * 菜单创建工具类
 * 
 * @author lixin
 *
 */
public class MenuButtonUtil {
	
	private static Logger logger = LoggerFactory.getLogger(MenuButtonUtil.class);
	
	public static void main(String[] args) {
		// 组装菜单对象
		/*Menu menu = initMenu();
		String menuJson = JSONObject.fromObject(menu).toString();
		System.out.println(menuJson);*/

		createMenu();
		
	}
	
	/**
	 * 微信公众号菜单创建
	 */
	public static void createMenu() {
		
		try {
			// 1、获取接口调用access_token
			AccessToken accessToken = AccessTokenUtil.getAccessToken();
			if(accessToken == null) {
				logger.error("菜单未创建，原因：access_token获取失败！");
				throw new Exception("菜单未创建，原因：access_token获取失败！");
			}
			String token = accessToken.getToken();
			
			// 2、组装菜单对象
			Menu menu = initMenu();
			String menuJson = JSONObject.fromObject(menu).toString();
//			menuJson = "{\"button\":[{\"key\":\"111\",\"name\":\"事件菜单\",\"type\":\"click\"},{\"name\":\"腾讯网\",\"type\":\"view\",\"url\":\"http://www.qq.com/\"},{\"name\":\"菜单\",\"sub_button\":[{\"name\":\"百度音乐\",\"type\":\"view\",\"url\":\"http://play.baidu.com/\"},{\"name\":\"百度视频\",\"type\":\"view\",\"url\":\"http://v.baidu.com/i\"}]}]}";
//			menuJson = "{\"button\":[{\"name\":\"腾讯网\",\"type\":\"view\",\"url\":\"http://www.qq.com/\"},{\"name\":\"菜单\",\"sub_button\":[{\"name\":\"百度音乐\",\"type\":\"view\",\"url\":\"http://play.baidu.com/\"},{\"name\":\"百度视频\",\"type\":\"view\",\"url\":\"http://v.baidu.com/i\"}]}]}";
			System.out.println(menuJson);
			String url = WeixinURLUtil.MENU_CREATE_URL.substring(0, WeixinURLUtil.MENU_CREATE_URL.indexOf("?")) +"?access_token="+token;
			
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("access_token", token);
			parameter.put("body", menuJson);
		
			
			HttpClient httpClient = HttpClientUtil.initHttpClient();
			
//			PostMethod postMethod = HttpClientUtil.postMethod(url, parameter);
//			int stateCode = httpClient.executeMethod(postMethod);
//			if(stateCode == 200) {
//				
//				String respBody = postMethod.getResponseBodyAsString();
//				JSONObject jsonObject = JSONObject.fromObject(respBody);
				JSONObject jsonObject = HttpClientUtil.doPostStr(url, menuJson);
				Object errcode = jsonObject.get("errcode");
				if("0".equals(errcode.toString())) {
					System.out.println("菜单创建成功！");
				} else {
					System.out.println("菜单创建失败，code:"+errcode);
				}
//			}
		} catch (Exception e) {
			logger.error("菜单创建失败，原因："+e);
			e.printStackTrace();
		}
	}
	
	/**
	 * 构建生成菜单对象
	 * @return
	 */
	public static Menu initMenu() {
		
		// 第一个菜单为事件点击菜单
		ClickButton clickButton11 = new ClickButton();
		clickButton11.setType("click");
		clickButton11.setName("事件菜单");
		clickButton11.setKey("111");
		
		//第二个菜单为带有一个子菜单的view类型菜单
		ViewButton viewButton21 = new ViewButton();
		viewButton21.setType("view");
		viewButton21.setName("腾讯网");
		viewButton21.setUrl("http://www.qq.com/");
		
		//第三个菜单为包含两个二级子菜单的view类型菜单
		ViewButton viewButton31 = new ViewButton();
		viewButton31.setType("view");
		viewButton31.setName("百度音乐");
		viewButton31.setUrl("http://play.baidu.com/");
		
		ViewButton viewButton32 = new ViewButton();
		viewButton32.setType("view");
		viewButton32.setName("百度视频");
		viewButton32.setUrl("http://v.baidu.com/i");
		
		Button button3 = new Button();
		button3.setName("菜单");
		button3.setSub_button(new Button[]{viewButton31, viewButton32});
		
		Menu menu = new Menu();
		menu.setButton(new Button[]{clickButton11, viewButton21, button3});
		
		return menu;
	}
	
}
