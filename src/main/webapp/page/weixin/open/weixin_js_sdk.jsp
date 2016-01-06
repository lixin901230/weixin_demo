<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<base href="${ctx}"/>	<!-- 若不加，则在include中引用相对路径后，在使用浏览器后退或手机物理返回时会出现路径错误问题 -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<%@ include file="../common/head.jsp"%>
	<script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<title>微信JS-SDK使用练习</title>
	<style type="text/css">
		.font_show {font-size: 45px; line-height: 50px; text-align: center;}
	</style>
	<script type="text/javascript">

		$(function(){
			
			//获取微信js-sdk接入权限配置并接入微信js-sdk
			getWxJsSdkConfig();
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
		
		//通过ready接口处理成功验证
	    //config信息验证后会执行ready方法，所有接口调用都必须在config接口获得结果之后，config是一个客户端的异步操作，所以如果需要在页面加载时就调用相关接口，则须把相关接口放在ready函数中调用来确保正确执行。对于用户触发时才调用的接口，则可以直接调用，不需要放在ready函数中。
		wx.ready(function(){
			
			//基础接口：判断当前客户端版本是否支持指定JS接口
			wx.checkJsApi({
			    jsApiList: jsApiArr, // 需要检测的JS接口列表，所有JS接口列表见附录2,
			    success: function(res) {
			        // 以键值对的形式返回，可用的api值true，不可用为false；如：{"checkResult":{"chooseImage":true},"errMsg":"checkJsApi:ok"}
			        console.info(">>>>>>wx.checkJsApi："+res);
								        
			        
			        //######################	js-sdk	调试		################################
			        
			        //---------------	分享接口	-----------------------
					
					//分享接口：获取“分享到朋友圈”按钮点击状态及自定义分享内容接口
					wx.onMenuShareTimeline({
						title: '分享到朋友圈测试', // 分享标题
					    link: 'http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp', // 分享链接
					    imgUrl: 'http://www.lixinsj.com.cn/weixin/incoming/qrcode.jpg', // 分享图标
					    success: function () { 
					        // 用户确认分享后执行的回调函数
					        alert("分享到朋友圈测试成功！");
					    },
					    cancel: function () { 
					        // 用户取消分享后执行的回调函数
					        alert("取消分享到朋友圈测试成功！");
					    }
					});
			
					//分享接口：获取“分享给朋友”按钮点击状态及自定义分享内容接口
					wx.onMenuShareQQ({
						title: '分享给朋友', // 分享标题
					    desc: '微信公众号开发js-sdk测试——分享给朋友', // 分享描述
					    link: 'http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp', // 分享链接
					    imgUrl: 'http://www.lixinsj.com.cn/weixin/incoming/qrcode.jpg', // 分享图标
					    success: function () { 
					       // 用户确认分享后执行的回调函数
					       alert("分享给朋友测试成功！");
					    },
					    cancel: function () { 
					       // 用户取消分享后执行的回调函数
					        alert("取消分享给朋友测试成功！");
					    }
					});
					
					//分享接口：获取“分享到QQ”按钮点击状态及自定义分享内容接口
					wx.onMenuShareQQ({
						title: '分享到QQ', // 分享标题
					    desc: '微信公众号开发js-sdk测试——分享到QQ', // 分享描述
					    link: 'http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp', // 分享链接
					    imgUrl: 'http://www.lixinsj.com.cn/weixin/incoming/qrcode.jpg', // 分享图标
					    success: function () { 
					       // 用户确认分享后执行的回调函数
					       alert("分享到QQ测试成功！");
					    },
					    cancel: function () { 
					       // 用户取消分享后执行的回调函数
					       alert("取消分享到QQ测试成功！");
					    }
					});
					
					//分享接口：获取“分享到腾讯微博”按钮点击状态及自定义分享内容接口
					wx.onMenuShareWeibo({
						title: '分享到微博', // 分享标题
					    desc: '微信公众号开发js-sdk测试——分享到微博', // 分享描述
					    link: 'http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp', // 分享链接
					    imgUrl: 'http://www.lixinsj.com.cn/weixin/incoming/qrcode.jpg', // 分享图标
					    success: function () { 
					       // 用户确认分享后执行的回调函数
					       alert("分享到腾讯微博测试成功！");
					    },
					    cancel: function () { 
					        // 用户取消分享后执行的回调函数
					        alert("取消分享到腾讯微博测试成功！");
					    }
					});
					
					//分享接口：获取“分享到QQ空间”按钮点击状态及自定义分享内容接口
					wx.onMenuShareQZone({
						title: '分享到QQ空间', // 分享标题
					    desc: '微信公众号开发js-sdk测试——分享到QQ空间', // 分享描述
					    link: 'http://www.lixinsj.com.cn/weixin/page/weixin/open/weixin_js_sdk.jsp', // 分享链接
					    imgUrl: 'http://www.lixinsj.com.cn/weixin/incoming/qrcode.jpg', // 分享图标
					    success: function () {
					       // 用户确认分享后执行的回调函数
					       alert("分享到QQ空间测试成功！");
					    },
					    cancel: function () { 
					        // 用户取消分享后执行的回调函数
					        alert("取消分享到QQ空间测试成功！");
					    }
					});
					
					
					//---------------	图像接口	-----------------------
					//图像接口：拍照或从手机相册中选图接口
					
					
					//预览图片接口
					
					
					//上传图片接口
					
					
					//下载图片接口
					
					
					//---------------	音频接口	-----------------------
					//开始录音接口
					//停止录音接口
					//监听录音自动停止接口
					//播放语音接口
					//暂停播放接口
					//停止播放接口
					//监听语音播放完毕接口
					//上传语音接口
					//下载语音接口
			
					//---------------	音频接口	-----------------------
					//识别音频并返回识别结果接口
			
					//---------------	设备信息	-----------------------
					//获取网络状态接口
			
					//---------------	地理位置	-----------------------
					//使用微信内置地图查看位置接口
					//获取地理位置接口
					
					//---------------	摇一摇周边	-----------------------
					//开启查找周边ibeacon设备接口
					//关闭查找周边ibeacon设备接口
					//监听周边ibeacon设备接口
			
					//---------------	界面操作	-----------------------
					//隐藏右上角菜单接口
					//显示右上角菜单接口
					//关闭当前网页窗口接口
					//批量隐藏功能按钮接口
					//批量显示功能按钮接口
					//隐藏所有非基础按钮接口
					//显示所有功能按钮接口
			
					//---------------	微信扫一扫	-----------------------
					//调起微信扫一扫接口
			
					//---------------	微信小店	-----------------------
					//跳转微信商品页接口
			
					//---------------	微信卡券	-----------------------
					//拉取适用卡券列表并获取用户选择信息
					//批量添加卡券接口
					//查看微信卡包中的卡券接口
					//核销后再次赠送卡券接口
			
					//---------------	微信支付	-----------------------
					//发起一个微信支付请求
			    }
			});
			
		});
	    
		
	</script>
</head>
<body>
	<div class="auto">
	
		<input type="hidden" id="unionId" value="${unionId}"/>
	
		<!---------- main start ---------->
	    <div class="font_show">
	   		微信JS-SDK调试页面
	    </div>
		<!---------- main end ---------->
	</div>
</body>
</html>