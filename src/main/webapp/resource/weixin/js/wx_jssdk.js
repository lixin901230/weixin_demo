/* 	预留的js
			//---------------	图像接口	-----------------------
			//图像接口：拍照或从手机相册中选图接口
			wx.chooseImage({
			    count: 1, // 默认9
			    sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
			    sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
			    success: function (res) {
			        var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片,
			        alert("拍照或从手机相册中选图成功！图片ids："+localIds);
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			        alert("取消拍照或从手机相册中选图成功！");
			    }
			});
			
			//预览图片接口
			wx.previewImage({
			    current: '', // 当前显示图片的http链接
			    urls: [] // 需要预览的图片http链接列表
			});
			
			//上传图片接口
			wx.uploadImage({
			    localId: '', // 需要上传的图片的本地ID，由chooseImage接口获得
			    isShowProgressTips: 1, // 默认为1，显示进度提示
			    success: function (res) {
			        var serverId = res.serverId; // 返回图片的服务器端ID
			    }
			});
			
			//下载图片接口
			wx.downloadImage({
			    serverId: '', // 需要下载的图片的服务器端ID，由uploadImage接口获得
			    isShowProgressTips: 1, // 默认为1，显示进度提示
			    success: function (res) {
			        var localId = res.localId; // 返回图片下载后的本地ID
			    }
			});
			
			
			//---------------	音频接口	-----------------------
			//开始录音接口
			wx.startRecord();
			
			//停止录音接口
			wx.stopRecord({
			    success: function (res) {
			        var localId = res.localId;
			    }
			});
			
			//监听录音自动停止接口
			wx.onVoiceRecordEnd({
			    // 录音时间超过一分钟没有停止的时候会执行 complete 回调
			    complete: function (res) {
			        var localId = res.localId; 
			    }
			});
			
			//播放语音接口
			wx.playVoice({
			    localId: '' // 需要播放的音频的本地ID，由stopRecord接口获得
			});
			
			//暂停播放接口
			wx.pauseVoice({
			    localId: '' // 需要暂停的音频的本地ID，由stopRecord接口获得
			});
			
			//停止播放接口
			wx.stopVoice({
			    localId: '' // 需要停止的音频的本地ID，由stopRecord接口获得
			});
			
			//监听语音播放完毕接口
			wx.onVoicePlayEnd({
			    success: function (res) {
			        var localId = res.localId; // 返回音频的本地ID
			    }
			});
			
			//上传语音接口
			wx.uploadVoice({
			    localId: '', // 需要上传的音频的本地ID，由stopRecord接口获得
			    isShowProgressTips: 1, // 默认为1，显示进度提示
			        success: function (res) {
			        var serverId = res.serverId; // 返回音频的服务器端ID
			    }
			});
			
			//下载语音接口
			wx.downloadVoice({
			    serverId: '', // 需要下载的音频的服务器端ID，由uploadVoice接口获得
			    isShowProgressTips: 1, // 默认为1，显示进度提示
			    success: function (res) {
			        var localId = res.localId; // 返回音频的本地ID
			    }
			});
			
			
			//---------------	智能接口	-----------------------
			//识别音频并返回识别结果接口
			wx.translateVoice({
			   localId: '', // 需要识别的音频的本地Id，由录音相关接口获得
			    isShowProgressTips: 1, // 默认为1，显示进度提示
			    success: function (res) {
			        alert(res.translateResult); // 语音识别的结果
			    }
			});
			
			
			//---------------	设备信息	-----------------------
			//获取网络状态接口
			wx.getNetworkType({
			    success: function (res) {
			        var networkType = res.networkType; // 返回网络类型2g，3g，4g，wifi
			    }
			});
	
			//---------------	地理位置	-----------------------
			//使用微信内置地图查看位置接口
			wx.openLocation({
			    latitude: 0, // 纬度，浮点数，范围为90 ~ -90
			    longitude: 0, // 经度，浮点数，范围为180 ~ -180。
			    name: '', // 位置名
			    address: '', // 地址详情说明
			    scale: 1, // 地图缩放级别,整形值,范围从1~28。默认为最大
			    infoUrl: '' // 在查看位置界面底部显示的超链接,可点击跳转
			});
			
			//获取地理位置接口
			wx.getLocation({
			    type: 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
			    success: function (res) {
			        var latitude = res.latitude; // 纬度，浮点数，范围为90 ~ -90
			        var longitude = res.longitude; // 经度，浮点数，范围为180 ~ -180。
			        var speed = res.speed; // 速度，以米/每秒计
			        var accuracy = res.accuracy; // 位置精度
			    }
			});
			
			
			//---------------	摇一摇周边	-----------------------
			//开启查找周边ibeacon设备接口
			wx.startSearchBeacons({
				ticket:"",  //摇周边的业务ticket, 系统自动添加在摇出来的页面链接后面
				complete:function(argv){
					//开启查找完成后的回调函数
				}
			});
			
			//关闭查找周边ibeacon设备接口
			wx.stopSearchBeacons({
				complete:function(res){
					//关闭查找完成后的回调函数
				}
			});
			
			//监听周边ibeacon设备接口
			wx.onSearchBeacons({
				complete:function(argv){
					//回调函数，可以数组形式取得该商家注册的在周边的相关设备列表
				}
			});
			
			
			//---------------	界面操作	-----------------------
			//隐藏右上角菜单接口
			wx.hideOptionMenu();
			
			//显示右上角菜单接口
			wx.showOptionMenu();
			
			//关闭当前网页窗口接口
			wx.closeWindow();
			
			//批量隐藏功能按钮接口
			wx.hideMenuItems({
			    menuList: [] // 要隐藏的菜单项，只能隐藏“传播类”和“保护类”按钮，所有menu项见附录3
			});
			
			//批量显示功能按钮接口
			wx.showMenuItems({
			    menuList: [] // 要显示的菜单项，所有menu项见附录3
			});
			
			//隐藏所有非基础按钮接口
			wx.hideAllNonBaseMenuItem();
			// “基本类”按钮详见附录3

			//显示所有功能按钮接口
			wx.showAllNonBaseMenuItem();
			
			
			//---------------	微信扫一扫	-----------------------
			//调起微信扫一扫接口
			wx.scanQRCode({
			    needResult: 0, // 默认为0，扫描结果由微信处理，1则直接返回扫描结果，
			    scanType: ["qrCode","barCode"], // 可以指定扫二维码还是一维码，默认二者都有
			    success: function (res) {
			   		var result = res.resultStr; // 当needResult 为 1 时，扫码返回的结果
				}
			});
			
			
			//---------------	微信小店	-----------------------
			//跳转微信商品页接口
			wx.openProductSpecificView({
			    productId: '', // 商品id
			    viewType: '' // 0.默认值，普通商品详情页1.扫一扫商品详情页2.小店商品详情页
			});
			
			
			//---------------	微信卡券	-----------------------
			//拉取适用卡券列表并获取用户选择信息
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
			
			//批量添加卡券接口
			wx.addCard({
			    cardList: [{
			        cardId: '',
			        cardExt: ''
			    }], // 需要添加的卡券列表
			    success: function (res) {
			        var cardList = res.cardList; // 添加的卡券列表信息
			    }
			});
			
			//查看微信卡包中的卡券接口
			wx.openCard({
			    cardList: [{
			        cardId: '',
			        code: ''
			    }]// 需要打开的卡券列表
			});
			
			//核销后再次赠送卡券接口
			wx.consumeAndShareCard({
			    cardId: '',
			    code: ''
			});
	
			//---------------	微信支付	-----------------------
			//发起一个微信支付请求
			wx.chooseWXPay({
			    timestamp: 0, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
			    nonceStr: '', // 支付签名随机串，不长于 32 位
			    package: '', // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
			    signType: '', // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
			    paySign: '', // 支付签名
			    success: function (res) {
			        // 支付成功后的回调函数
			    }
			}); */