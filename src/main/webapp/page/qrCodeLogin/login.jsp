<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<base href="${ctx}"/>	<!-- 若不加，则在include中引用相对路径后，在使用浏览器后退或手机物理返回时会出现路径错误问题 -->
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<%@ include file="common/head.jsp"%>
	<script src="${ctx}/resource/weixin/js/jweixin-1.0.0.js" type="text/javascript"></script>
	<script src="${ctx}/resource/weixin/js/wx_js_sdk_init_config.js" type="text/javascript"></script>
	<title>用户登录</title>
</head>
<body>
	<div class="auto">
		<%
			String uuid = request.getParameter("uuid");
			System.out.println("===="+uuid);
		%>
		<input type="hidden" id="uuid" value="<%=uuid%>"/>
	
		<!---------- main start ---------->
	    <div>
	    	<h2 class="tit1 p30_0 tc bb_d">登录</h2>
	        
	        <div class="form_box"><i class="iconfont form_ico login_user"></i>
	        	<input name="userName" id="userName" type="text" value="用户名" class="form_input" onfocus="if(this.value=='用户名') this.value=''; this.style.color='#5d5d5d'" onblur="if(this.value=='') this.value='用户名'; this.style.color='#cfcfcf'" >
	        </div>
	        <div class="form_box"><i class="iconfont form_ico login_pwd"></i>
	        	<input name="password" id="password" type="password" class="form_input" />
	        </div>
	        <!-- 登录错误提示 -->
	        <div id="login_error_msg" style="margin-left: 30px; margin-top: 5px;">
				<span class="validate_prompt"></span>
			</div>
	        <!-- 登录错误提示end -->
	        <div class="clearfix p30">
	        	<div class="float_two f_style">
	            	<label for="checkbox_1" class="label_check c_on">
	                <input type="checkbox" checked="checked" value="1" id="checkbox_1" name="checkbox_1" /> 
					自动登录</label> 
	            </div>
	            <div class="float_two tr"><a href="javascript:void(0);" class="link2">忘记密码</a></div>
	        </div>
	        <div class="p0_30"><a href="javascript:void(0);" id="login_btn" class="btn1">登录</a></div>
			<!-- <div class="login_foot"><span class="f24 gaf">没有帐号？</span><a href="javascript:void(0);" id="btn_register" class="link1">立即注册</a></div> -->
			<br/>
			<button id="quit_btn" style="display: ;">退出</button>
	    </div>
		<!---------- main end ---------->
	</div>
	<script type="text/javascript">
				
    	$(function() {
			//手机登录事件绑定
		    $("#login_btn").on("click", function(){
		    	qrCodeLogin();
		    });
			
			//退出页面
			$("#quit_btn").on("click", function(){
				if(wx) {
					//微信浏览器关闭（微信jssdk接口关闭当前打开的登录页面）
			    	WeixinJSBridge.call('closeWindow');
			    	//wx.closeWindow();
				} else {
					window.close();	//其他手机浏览器关闭
				}
		    });
		});
    	
		//手机扫码后登录
		function qrCodeLogin(){
			var uuid = $('#uuid').val();
			var userName = $("#userName").val();
			var password = $("#password").val();
			if(userName == null || userName == "") {
				return false;
			}
			if(password == null || password == "") {
				return false;
			}
			password = MD5(password.replace(/\%/g, "%25").replace(/\+/g, "%2B").replace(/\&/g, "%26"));
			$.ajax({
				url: base +"/PhoneQrCodeLoginServlet?method=phoneLogin",
				type: 'post',
				data: {
					uuid: uuid,
			        userName: userName,
			        password: password
				},
				dataType: 'json',
				cache: false,
				async: false,
				success: function(data) {
					
					if(data && data.success){
			        	alert("登录成功");
			        	
			        	if(wx) {
				        	//微信浏览器关闭（微信jssdk接口关闭当前打开的登录页面）
							//WeixinJSBridge.call('closeWindow');
				        	wx.closeWindow();
			        	} else {
			        		//其他手机浏览器须在此依次判断关闭
			        		window.close();
			        	}
			        }else{
			        	alert("登录失败");
			        }
				},
				error: function(){}
			});
		    return loginSuccess;
		}
	</script>
</body>
</html>
