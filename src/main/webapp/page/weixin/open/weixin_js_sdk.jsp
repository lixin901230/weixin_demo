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
	   		
	    	<div class="cell bgc desc">图像接口</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	
	    	<div class="cell bgc desc">音频接口</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
			
	    	<div class="cell bgc desc">智能接口</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	
	    	<div class="cell bgc desc">设备信息</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">地理位置</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">摇一摇周边</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">界面操作</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">微信扫一扫</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">微信小店</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">微信卡券</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	   		
	    	<div class="cell bgc desc">微信支付</div>
	    	<div class="cell cell_l"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
	    	<div class="cell cell_r"><input id="chooseImage" type="button" value="选择图片" class="btn" /></div>
		</div>
	    
		<!---------- main end ---------->
	</div>
</body>
</html>