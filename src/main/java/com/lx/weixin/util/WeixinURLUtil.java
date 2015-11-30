package com.lx.weixin.util;

/**
 * 微信平台接口地址
 * 
 * @author lixin
 */
public class WeixinURLUtil {
	
	// 微信平台接口参数
	public static final String APPID = "APPID";
	public static final String APPSECRET = "APPSECRET";
	public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
	public static final String TYPE = "TYPE";
	public static final String MEDIA_ID = "MEDIA_ID";
	public static final String OPENID = "OPENID";
	public static final String NEXT_OPENID = "NEXT_OPENID";
	public static final String TICKET = "TICKET";
	
	
	/** 获取access_token接口url，<br>http请求方式: GET */
	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//	public static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token";
	
	/** 获取微信服务器IP地址，<br>http请求方式: GET */
	public static final String WEIXIN_SERVER_IP_URL = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=ACCESS_TOKEN";
	
	
	//######################	自定义菜单管理	############################
	// 自定义菜单url
	
	/** 自定义菜单创建、修改接口url，<br>http请求方式: POST */
	public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	/** 自定义菜单查询接口url，<br>http请求方式: GET */
	public static final String MENU_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";
	
	/** 自定义菜单删除接口url，<br>http请求方式: GET */
	public static final String MENU_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";
	
	
	//######################	发送消息	############################
	// 客服接口
	
	/** 添加客服帐号，<br>http请求方式: POST */
	public static final String CUSTOMER_SERVICE_ADD_URL = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	
	/** 获取所有客服账号，<br>http请求方式: GET */
	public static final String CUSTOMER_SERVICE_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN";
	
	/** 修改客服帐号，<br>http请求方式: POST */
	public static final String CUSTOMER_SERVICE_UPDATE_URL = "https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN";
	
	/** 删除客服帐号，<br>http请求方式: GET */
	public static final String CUSTOMER_SERVICE_DELETE_URL = "https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN";
	
	/** 客服接口-发消息，<br>http请求方式: POST */
	public static final String CUSTOMER_SERVICE_SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN";
	
	
	// 群发接口
	
	/** 上传图文消息内的图片获取URL，<br>http请求方式: POST */
	public static final String UPLOAD_IMAGE_BACKURL_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN";
	
	/** 上传图文消息素材获取media_id，<br>http请求方式: POST */
	public static final String UPLOAD_IMAGETEXT_MEDIAID_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	
	/** 上传视频消息素材获取视频的media_id，<br>http请求方式: POST */
	public static final String UPLOAD_VIDEO_MEDIAID_URL = "https://file.api.weixin.qq.com/cgi-bin/media/uploadvideo?access_token=ACCESS_TOKEN";
	
	/** 根据分组进行群发，<br>http请求方式: POST */
	public static final String GROUPSEND_BY_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token=ACCESS_TOKEN";
			
	/** 根据OpenID列表群发，<br>http请求方式: POST 【订阅号不可用，服务号认证后可用】*/
	public static final String GROUPSEND_BY_OPENID_LIST_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
		
	/** 删除群发，<br>http请求方式: POST */
	public static final String GROUPSEND_DELETE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN";
	
	/** 预览接口（通过该接口发送消息给指定用户，在手机端查看消息的样式和排版），<br>http请求方式: POST */
	public static final String PREVIEW_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN";
	
	/** 查询群发消息发送状态，<br>http请求方式: POST */
	public static final String GROUPSEND_MSG_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/get?access_token=ACCESS_TOKEN";
	
	
	// 模板消息接口
	
	/** 设置所属行业，<br>http请求方式: POST */
	public static final String SET_INDUSTRY_URL = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=ACCESS_TOKEN";
	
	/** 获得模板ID，<br>http请求方式: POST */
	public static final String ADD_TEMPLATE_URL = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=ACCESS_TOKEN";

	/** 发送模板消息，<br>http请求方式: POST */
	public static final String SEND_TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";
	
	/** 获取自动回复规则，<br>http请求方式: GET（请使用https协议） */
	public static final String GET_AUTOREPLY_INFO_URL = "https://api.weixin.qq.com/cgi-bin/get_current_autoreply_info?access_token=ACCESS_TOKEN";
	
	
	// 素材管理
	
	/** 新增临时素材，<br>http请求方式: POST/FORM,需使用https */
	public static final String ADD_TEMP_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	
	/** 获取临时素材，<br>http请求方式: GET,https调用 */
	public static final String GET_TEMP_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID";
	
	/** 新增永久图文素材，<br>http请求方式: POST */
	public static final String ADD_FOREVER_IMGTEXT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_news?access_token=ACCESS_TOKEN";
	
	/** 新增其他类型永久素材，<br>http请求方式: POST */
	public static final String ADD_FOREVER_OTHER_TYPE_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/add_material?access_token=ACCESS_TOKEN";
	
	/** 获取永久素材，<br>http请求方式: POST,https调用 */
	public static final String GET_FOREVER_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/get_material?access_token=ACCESS_TOKEN";
	
	/** 删除永久素材，<br>http请求方式: POST */
	public static final String DELETE_FOREVER_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/del_material?access_token=ACCESS_TOKEN";
	
	/** 修改永久图文素材，<br>http请求方式: POST */
	public static final String UPDATE_FOREVER_IMGTEXT_MATERIAL_URL = "https://api.weixin.qq.com/cgi-bin/material/update_news?access_token=ACCESS_TOKEN";
	
	/** 获取素材总数，<br>http请求方式: GET */
	public static final String GET_MATERIAL_COUNT_URL = "https://api.weixin.qq.com/cgi-bin/material/get_materialcount?access_token=ACCESS_TOKEN";
	
	/** 获取素材列表，<br>http请求方式: POST */
	public static final String GET_MATERIAL_LIST_URL = "https://api.weixin.qq.com/cgi-bin/material/batchget_material?access_token=ACCESS_TOKEN";
	
	
	// 用户管理
	
	/** 用户分组管理——创建分组，<br>http请求方式: POST（请使用https协议） */
	public static final String CREATE_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN";
	
	/** 用户分组管理——查询所有分组，<br>http请求方式: GET（请使用https协议） */
	public static final String QUERY_ALL_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/get?access_token=ACCESS_TOKEN";

	/** 用户分组管理——查询用户所在分组，<br>http请求方式: GET（请使用https协议） */
	public static final String QUERY_USER_GROUP_BY_USERID_URL = "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token=ACCESS_TOKEN";

	/** 用户分组管理——修改分组名，<br>http请求方式: POST（请使用https协议） */
	public static final String UPDATE_USER_GROUP_NAME_URL = "https://api.weixin.qq.com/cgi-bin/groups/update?access_token=ACCESS_TOKEN";
	
	/** 用户分组管理——移动用户分组，<br>http请求方式: POST（请使用https协议） */
	public static final String MOVE_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token=ACCESS_TOKEN";

	/** 用户分组管理——批量移动用户分组，<br>http请求方式: POST（请使用https协议） */
	public static final String BATCH_MOVE_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/members/batchupdate?access_token=ACCESS_TOKEN";

	/** 用户分组管理——删除分组，<br>http请求方式: POST（请使用https协议） */
	public static final String DELETE_USER_GROUP_URL = "https://api.weixin.qq.com/cgi-bin/groups/delete?access_token=ACCESS_TOKEN";

	/** 设置备注名，<br>http请求方式: POST（请使用https协议） */
	public static final String UPDATE_USER_REMARK_NAME_URL = "https://api.weixin.qq.com/cgi-bin/user/info/updateremark?access_token=ACCESS_TOKEN";
	
	/** 获取用户基本信息（包括UnionID机制），开发者可通过OpenID来获取用户基本信息<br>http请求方式: GET（请使用https协议） */
	public static final String GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
	
	/** 批量获取用户基本信息（最多支持一次拉取100条），<br>http请求方式: POST */
	public static final String BATCH_GET_USER_INFO_URL = "https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN";

	/** 获取用户列表，<br>http请求方式: GET（请使用https协议） */
	public static final String GET_USER_LIST_URL = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=ACCESS_TOKEN&next_openid=NEXT_OPENID";
	
	
	// 账号管理
	
	/** 生成带参数的二维码——创建（临时/永久）二维码ticket，<br>http请求方式: POST */
	public static final String CREATE_QR_CODE = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=ACCESS_TOKEN";

	/** 生成带参数的二维码——通过ticket换取二维码，<br>HTTP GET请求（请使用https协议） */
	public static final String GET_QR_CODE_IMG = "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET";
	
	/** 长链接转短链接接口，<br>http请求方式: POST */
	public static final String LONGURL_TO_SHORTURL = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token=ACCESS_TOKEN";
	
}
