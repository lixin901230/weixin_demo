<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<base href="${ctx}"/>	<!-- 若不加，则在include中引用相对路径后，在使用浏览器后退或手机物理返回时会出现路径错误问题 -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<title>微信JS-SDK使用练习</title>
	<%@ include file="../common/head.jsp"%>
	<link href="${ctx}/resource/weixin/css/wx_js_sdk.css" rel="stylesheet" type="text/css">
	
	<!-- 引入微信在线js -->
	<!-- <script src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js" type="text/javascript"></script> -->
	<script src="${ctx}/resource/weixin/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script src="${ctx}/resource/weixin/js/wx_js_sdk.js" type="text/javascript"></script>
</head>

<body>
	<div class="pd_b50">
	
		<input type="hidden" id="unionId" value="${unionId}"/>
	
		<!---------- main start ---------->
	    <div class="c pd20">
	   		李鑫 微信开发JS-SDK调试页面
	    </div>
	    
	   	<div class="c">
	   		
	   		<div>
		    	<div class="cell bgc desc">图像接口</div>
		    	<div class="cell c_l"><input id="chooseImage" type="button" value="拍照或从手机相册中选图" class="btn" /></div>
		    	<div class="cell c_r"><input id="previewImage" type="button" value="预览图片接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="uploadImage" type="button" value="上传图片接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="downloadImage" type="button" value="下载图片接口" class="btn" /></div>
	    	</div>
	    	<div class="pd_t50 cb">
		    	<div class="cell bgc desc">音频接口</div>
		    	<div class="cell c_l"><input id="startRecord" type="button" value="开始录音接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="stopRecord" type="button" value="停止录音接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="startRecord" type="button" value="监听录音自动停止接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="stopRecord" type="button" value="播放语音接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="startRecord" type="button" value="暂停播放接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="stopRecord" type="button" value="停止播放接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="startRecord" type="button" value="监听语音播放完毕接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="stopRecord" type="button" value="上传语音接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="startRecord" type="button" value="下载语音接口" class="btn" /></div>
		    	<div class="cell c_r"></div>
			</div>
	    	<div class="pd_t50 cb">
		    	<div class="cell bgc desc">智能接口</div>
		    	<div class="cell"><input id="translateVoice" type="button" value="识别音频并返回识别结果" class="btn" /></div>
			</div>
	    	<div class="pd_t50 cb">
		    	<div class="cell bgc desc">设备信息</div>
		    	<div class="cell"><input id="getNetworkType" type="button" value="获取网络状态接口" class="btn" /></div>
		   	</div>
	    	<div class="pd_t50 cb">	
		    	<div class="cell bgc desc">地理位置</div>
		    	<div class="cell"><input id="openLocation" type="button" value="使用微信内置地图查看位置" class="btn" /></div>
		    	<div class="cell"><input id="getLocation" type="button" value="获取地理位置" class="btn" /></div>
		   	</div>
	    	<div class="pd_t50 cb">	
		    	<div class="cell bgc desc">摇一摇周边</div>
		    	<div class="cell"><input id="startSearchBeacons" type="button" value="开启查找周边ibeacon设备" class="btn" /></div>
		    	<div class="cell"><input id="stopSearchBeacons" type="button" value="关闭查找周边ibeacon设备" class="btn" /></div>
		    	<div class="cell"><input id="onSearchBeacons" type="button" value="监听周边ibeacon设备接口" class="btn" /></div>
		   	</div>
	    	<div class="pd_t50 cb">	
		    	<div class="cell bgc desc">界面操作</div>
		    	<div class="cell c_l"><input id="hideOptionMenu" type="button" value="隐藏右上角菜单接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="showOptionMenu" type="button" value="显示右上角菜单接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="closeWindow" type="button" value="关闭当前网页窗口接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="hideMenuItems" type="button" value="批量隐藏功能按钮接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="showMenuItems" type="button" value="批量显示功能按钮接口" class="btn" /></div>
		    	<div class="cell c_r"><input id="hideAllNonBaseMenuItem" type="button" value="显示所有功能按钮接口" class="btn" /></div>
		    	<div class="cell c_l"><input id="showAllNonBaseMenuItem" type="button" value="隐藏所有非基础按钮接口" class="btn" /></div>
		    	<div class="cell c_r"></div>
		   	</div>
	    	<div class="pd_t50 cb">	
		    	<div class="cell bgc desc">微信扫一扫</div>
		    	<div class="cell"><input id="scanQRCode" type="button" value="调起微信扫一扫接口" class="btn" /></div>
		   	</div>
	    	<div class="pd_t50 cb">	
		    	<div class="cell bgc desc">微信小店</div>
		    	<div class="cell"><input id="openProductSpecificView" type="button" value="跳转微信商品页接口" class="btn" /></div>
		   	</div>
	    	<div class="pd_t50 cb">	
		    	<div class="cell bgc desc">微信卡券</div>
		    	<div class="cell"><input id="chooseCard" type="button" value="拉取适用卡券列表并获取用户选择信息" class="btn" /></div>
		    	<div class="cell"><input id="addCard" type="button" value="核销后再次赠送卡券接口" class="btn" /></div>
		    	<div class="cell"><input id="openCard" type="button" value="批量添加卡券接口" class="btn" /></div>
		    	<div class="cell"><input id="consumeAndShareCard" type="button" value="查看微信卡包中的卡券接口" class="btn" /></div>
			</div>
	    	<div class="pd_t50 cb">	
				<div class="cell bgc desc">微信支付</div>
		    	<div class="cell"><input id="chooseWXPay" type="button" value="发起一个微信支付请求" class="btn" /></div>
		    </div>
		</div>
	    
		<!---------- main end ---------->
	</div>
</body>
</html>