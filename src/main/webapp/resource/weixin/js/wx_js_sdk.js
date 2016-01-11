
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
    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
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
			    trigger: function (res) {
	                alert('触发点击分享到朋友圈');
	            },
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
			wx.onMenuShareAppMessage({
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
	    }
	});
	
	
	//---------------	图像接口	-----------------------
	
	var localIds = "";	//上传图片接口需要使用选择的图片ids，
	var serverIds = [];	//上传的图片在服务端的id，测试下载图片时使用
	
	//拍照或从手机相册中选图接口
	$("#chooseImage").on("click", function(){
		wx.chooseImage({
		    count: 3, // 默认9
		    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
		    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
		    success: function (res) {
		    	
		        localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片,
		        alert("拍照或从手机相册中选图成功！图片ids："+localIds);
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		        alert("取消拍照或从手机相册中选图成功！");
		    }
		});
	});
	
	//预览图片接口
	$("#previewImage").on("click", function(){
		var domainBaseUrl = window.location.protocol+"//"+window.location.host + base;
		var imgUrls = [		
	       domainBaseUrl +'/upload/images/thumb_music2.jpg',	
	       domainBaseUrl +'/upload/images/meinv.jpg',	
	       domainBaseUrl +'/upload/images/shuangjiegun.jpg',
	       domainBaseUrl +'/upload/images/thumb_music.jpg',
	       domainBaseUrl +'/upload/images/Zhuoku189.jpg']
		wx.previewImage({
		    current: imgUrls[1], // 当前显示图片的http链接，显示第二张
		    urls: imgUrls	// 需要预览的图片http链接列表
		});
	});
	
	//上传图片接口（注意：默认只上传一张图片，自己做轮询去上传）
	$("#uploadImage").on("click", function(){
		alert("已选中待上传的图片本地id："+localIds);
		syncUpload(localIds);
	});
	function syncUpload(localIds) {
		var localId = localIds.pop();
		alert("准备上传图片："+localId)
		wx.uploadImage({
		    localId: localId, // 需要上传的图片的本地ID，由chooseImage接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		        var serverId = res.serverId; // 返回图片的服务器端ID
		        alert("图片："+localId+"上传完成，图片在服务器端的ID："+serverId);
		        
		        serverIds.push(serverId);	//存储图片的服务端ID，方便下载图片使用
		        
		        //其他对serverId做处理的代码
		        if(localIds.length > 0){
		        	syncUpload(localIds);
		        }
		    },
		    cancel: function () { 
		        alert("取消图片上传");
		    },
		    fail: function() {
		    	alert("上传图片失败");
		    }
		});
	}
	
	//下载图片接口
	$("#downloadImage").on("click", function(){
		wx.downloadImage({
		    serverId: '', // 需要下载的图片的服务器端ID，由uploadImage接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		        var localId = res.localId; // 返回图片下载后的本地ID
		    }
		});
	});
	
	//---------------	音频接口	-----------------------
	//开始录音接口
	$("#startRecord").on("click", function(){
		wx.startRecord();
		alert("开始录音...");
	});
	
	//停止录音接口
	var voiceLocalId = "";	//存储录音后生产的语音资源ID，供后续语音接口使用，如：播放语音接口等等
	$("#stopRecord").on("click", function(){
		wx.stopRecord({
		    success: function (res) {
		        var localId = res.localId;
		        alert("录音成功，语音资源ID："+localId);
		        voiceLocalId = localId;
		    }
		});
	});
	
	//监听录音自动停止接口
	$("#onVoiceRecordEnd").on("click", function(){
		wx.onVoiceRecordEnd({
		    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
		    complete: function (res) {
		    	voiceLocalId = res.localId; 
		        alert("监听录音自动停止，本地语音资源ID："+localId);
		    }
		});
	});
	
	//播放语音接口
	$("#playVoice").on("click", function(){
		wx.playVoice({
		    localId: voiceLocalId // 需要播放的音频的本地ID，由stopRecord接口获得
		});
	});
	
	//暂停播放接口
	$("#pauseVoice").on("click", function(){
		wx.pauseVoice({
		    localId: voiceLocalId // 需要暂停的音频的本地ID，由stopRecord接口获得
		});
	});
	
	//停止播放接口
	$("#stopVoice").on("click", function(){
		wx.stopVoice({
		    localId: voiceLocalId // 需要停止的音频的本地ID，由stopRecord接口获得
		});
	});
	
	//监听语音播放完毕接口
	$("#onVoicePlayEnd").on("click", function(){
		wx.onVoicePlayEnd({
		    success: function (res) {
		    	voiceLocalId = res.localId; // 返回音频的本地ID
		        alert("监听语音播放完毕，语音资源本地ID："+voiceLocalId);
		    }
		});
	});
	
	//上传语音接口
	var serverId = "";	//服务端语音资源ID，定义该全局变量为了供测试下载语音接口
	$("#uploadVoice").on("click", function(){
		wx.uploadVoice({
		    localId: voiceLocalId, // 需要上传的音频的本地ID，由stopRecord接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		        success: function (res) {
		        serverId = res.serverId; // 返回音频的服务器端ID
		    }
		});
		alert("成功上传刚录制的语音，语音本地ID："+voiceLocalId)
	});
	
	//下载语音接口
	$("#downloadVoice").on("click", function(){
		wx.downloadVoice({
		    serverId: serverId, // 需要下载的音频的服务器端ID，由uploadVoice接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		    	voiceLocalId = res.localId; // 返回音频的本地ID
		    	alert("下载的语音的本地ID："+voiceLocalId);
		    }
		});
	});
	
	
	//---------------	智能接口	-----------------------
	//识别音频并返回识别结果接口
	$("#translateVoice").on("click", function(){
		wx.translateVoice({
			localId: voiceLocalId, // 需要识别的音频的本地Id，由录音相关接口获得
		    isShowProgressTips: 1, // 默认为1，显示进度提示
		    success: function (res) {
		        alert(res.translateResult); // 语音识别的结果
		    }
		});
	});
	
	
	//---------------	设备信息	-----------------------
	//获取网络状态接口
	$("#getNetworkType").on("click", function(){
		wx.getNetworkType({
		    success: function (res) {
		        var networkType = res.networkType; // 返回网络类型2g，3g，4g，wifi
		        alert("网络状态："+networkType);
		    }
		});
	});

	//---------------	地理位置	-----------------------
	//使用微信内置地图查看位置接口
	$("#openLocation").on("click", function(){
		wx.openLocation({
		    latitude: 0, // 纬度，浮点数，范围为90 ~ -90
		    longitude: 0, // 经度，浮点数，范围为180 ~ -180。
		    name: '', // 位置名
		    address: '', // 地址详情说明
		    scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
		    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
		});
	});
	
	//获取地理位置接口
	$("#getLocation").on("click", function(){
		wx.getLocation({
		    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
		    success: function (res) {
		        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
		        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
		        var speed = res.speed; // 速度，以米/每秒计
		        var accuracy = res.accuracy; // 位置精度
		    }
		});
	});
	
	
	//---------------	摇一摇周边	-----------------------
	//开启查找周边ibeacon设备接口
	$("#startSearchBeacons").on("click", function(){
		wx.startSearchBeacons({
			ticket:"",  //摇周边的业务ticket, 系统自动添加在摇出来的页面链接后面
			complete:function(argv){
				//开启查找完成后的回调函数
			}
		});
	});
	
	//关闭查找周边ibeacon设备接口
	$("#stopSearchBeacons").on("click", function(){
		wx.stopSearchBeacons({
			complete:function(res){
				//关闭查找完成后的回调函数
			}
		});
	});
	
	//监听周边ibeacon设备接口
	$("#onSearchBeacons").on("click", function(){
		wx.onSearchBeacons({
			complete:function(argv){
				//回调函数，可以数组形式取得该商家注册的在周边的相关设备列表
			}
		});
	});
	
	
	//---------------	界面操作	-----------------------
	//隐藏右上角菜单接口
	$("#hideOptionMenu").on("click", function(){
		wx.hideOptionMenu();
	});
	
	//显示右上角菜单接口
	$("#showOptionMenu").on("click", function(){
		wx.showOptionMenu();
	});
	
	//关闭当前网页窗口接口
	$("#closeWindow").on("click", function(){
		wx.closeWindow();
	});
	
	//批量隐藏功能按钮接口
	$("#hideMenuItems").on("click", function(){
		wx.hideMenuItems({
	    	menuList: [] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
		});
	});
	
	//批量显示功能按钮接口
	$("#showMenuItems").on("click", function(){
		wx.showMenuItems({
		    menuList: [] // 要显示的菜单项，所有menu项见附录3
		});
	});
	
	//隐藏所有非基础按钮接口
	$("#hideAllNonBaseMenuItem").on("click", function(){
		wx.hideAllNonBaseMenuItem();
		// “基本类”按钮详见附录3
	});

	//显示所有功能按钮接口
	$("#showAllNonBaseMenuItem").on("click", function(){
		wx.showAllNonBaseMenuItem();
	});
	
	
	//---------------	微信扫一扫	-----------------------
	//调起微信扫一扫接口
	$("#scanQRCode").on("click", function(){
		wx.scanQRCode({
		    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
		    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
		    success: function (res) {
		   		var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
			}
		});
	});
	
	
	//---------------	微信小店	-----------------------
	//跳转微信商品页接口
	$("#openProductSpecificView").on("click", function(){
		wx.openProductSpecificView({
		    productId: '', // 商品id
		    viewType: '' // 0.默认值，普通商品详情页1.扫一扫商品详情页2.小店商品详情页
		});
	});
	
	
	//---------------	微信卡券	-----------------------
	//拉取适用卡券列表并获取用户选择信息
	$("#chooseCard").on("click", function(){
		wx.chooseCard({
		    shopId: '', // 门店Id
		    cardType: '', // 卡券类型
		    cardId: '', // 卡券Id
		    timestamp: 0, // 卡券签名时间戳
		    nonceStr: '', // 卡券签名随机串
		    signType: '', // 签名方式，默认'SHA1'
		    cardSign: '', // 卡券签名
		    success: function (res) {
		        var cardList= res.cardList; // 用户选中的卡券列表信息
		    }
		});
	});
	
	//批量添加卡券接口
	$("#addCard").on("click", function(){
		wx.addCard({
		    cardList: [{
		        cardId: '',
		        cardExt: ''
		    }], // 需要添加的卡券列表
		    success: function (res) {
		        var cardList = res.cardList; // 添加的卡券列表信息
		    }
		});
	});
	
	//查看微信卡包中的卡券接口
	$("#openCard").on("click", function(){
		wx.openCard({
		    cardList: [{
		        cardId: '',
		        code: ''
		    }]// 需要打开的卡券列表
		});
	});
	
	//核销后再次赠送卡券接口
	$("#consumeAndShareCard").on("click", function(){
		wx.consumeAndShareCard({
		    cardId: '',
		    code: ''
		});
	});

	//---------------	微信支付	-----------------------
	//发起一个微信支付请求
	$("#chooseWXPay").on("click", function(){
		wx.chooseWXPay({
		    timestamp: 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
		    nonceStr: '', // 支付签名随机串，不长于 32 位
		    package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
		    signType: '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
		    paySign: '', // 支付签名
		    success: function (res) {
		        // 支付成功后的回调函数
		    }
		});
	});
});