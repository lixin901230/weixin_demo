<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>网站扫码登录</title>
	<%@ include file="common/head.jsp"%>
</head>
<body style="font-size: 45px; text-align: center;">
	<div>
		<div>PC端网站扫码登录</div>
		<div><img src="" id="qrCodeImg" title="扫二维码登录"/></div>
		<div id="userName_msg"></div>
		
	</div>
	
	<script type="text/javascript">
		var uuid = null;
		$(function() {
			//获取二维码
			getQrCode();
		});
	
		//获取二维码
		function getQrCode() {
		    $.get(base +"/PhoneQrCodeLoginServlet?method=getQrCodeUrl", function(data, status) {
		    	var obj = eval("(" + data + ")");
		    	if(obj.success) {
		    		//存储UUID
		    		uuid = obj.data.uuid;
		    		//显示二维码
		    		$("#qrCodeImg").attr("src", obj.data.qrCodeUrl);
		    		//开始验证登录
		    		validateLogin();
		    	}
		    });
		}
	
		//pc端模拟长连接检查手机端是否已经成功登录后台
		function validateLogin(){
			$.get(base +"/LongConnectionCheckServlet?method=checkPhoneLoginState&uuid="+ uuid , function(data, status) {
				var obj = eval("(" + data + ")");
				if(obj && obj.success){
		    		alert("登录成功了:" + obj.data.userName);
		    		var userName = obj.data.userName;
		    		$("#userName_msg").text(userName+" 登录成功！");
		    		location.href = base +"/page/qrCodeLogin/login_success.jsp?userName="+userName;
		        } else {
		        	$("#userName_msg").text("登录中...");
		        	validateLogin();
		        }
		    });
		}
	</script>
</body>
</html>