$(function(){
	
	getWxJsSdkConfig();	//获取微信js-sdk接入权限配置并接入微信js-sdk
});

//js-sdk api 数组
var jsApiArr = ['onMenuShareTimeline',
			'onMenuShareAppMessage',
			'onMenuShareQQ',
			'onMenuShareWeibo',
			'onMenuShareQZone',
			'startRecord',
			'stopRecord',
			'onVoiceRecordEnd',
			'playVoice',
			'pauseVoice',
			'stopVoice',
			'onVoicePlayEnd',
			'uploadVoice',
			'downloadVoice',
			'chooseImage',
			'previewImage',
			'uploadImage',
			'downloadImage',
			'translateVoice',
			'getNetworkType',
			'openLocation',
			'getLocation',
			'hideOptionMenu',
			'showOptionMenu',
			'hideMenuItems',
			'showMenuItems',
			'hideAllNonBaseMenuItem',
			'showAllNonBaseMenuItem',
			'closeWindow',
			'scanQRCode',
			'chooseWXPay',
			'openProductSpecificView',
			'addCard',
			'chooseCard',
			'openCard'];

//js-sdk接入配置
//var appId = "wx70b0d2dbde434838";
var jsSdkConfig = {
    debug: true, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
   	appId: null, // 必填，公众号的唯一标识
    timestamp: null, // 必填，生成签名的时间戳
    nonceStr: null, // 必填，生成签名的随机串
    signature: null,// 必填，签名，见附录1
    jsApiList: jsApiArr // 必填，需要使用的JS接口列表，所有JS接口列表见附录2
};

//获取微信js-sdk接入权限配置
function getWxJsSdkConfig() {
	var targetUrl = window.location.href;	//eg：http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp
	$.ajax({
		url: base +'/jssdk/wxJsSdkValidateServlet?method=getWxJsSdkConfig',
		type: 'GET',
		data: 'targetUrl='+targetUrl,	//不传则后台通过读取weixin.properties配置文件来获取appId
		dataType: 'json',
		async: false,
		cache: false,
		success: function(data){
			if(data && data.data) {
				
				var config = data.data;
				//更新js-sdk接入配置
				jsSdkConfig["appId"] = config.appId;
				jsSdkConfig["timestamp"] = config.timestamp;
				jsSdkConfig["nonceStr"] = config.nonceStr;
				jsSdkConfig["signature"] = config.signature;
			}
		},
		error: function() {
			alert("获取微信js-sdk接入权限配置失败！");
		}
	});
}

//通过config接口注入权限验证配置
wx.config(jsSdkConfig);