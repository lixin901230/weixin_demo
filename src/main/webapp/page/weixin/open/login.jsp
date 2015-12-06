<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<base href="${ctx}"/>	<!-- 若不加，则在include中引用相对路径后，在使用浏览器后退或手机物理返回时会出现路径错误问题 -->
	<title>用户登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<%@ include file="../common/head.jsp"%>
</head>
<body>
	<div class="auto">
	
		<input type="hidden" id="unionId" value="${unionId}"/>
	
		<!---------- main start ---------->
	    <div>
	    	<h2 class="tit1 p30_0 tc bb_d">登录</h2>
	        
	        <div class="form_box"><i class="iconfont form_ico login_user"></i>
	        	<input name="loginId" id="loginId" type="text" value="手机号" class="form_input" onfocus="if(this.value=='手机号') this.value=''; this.style.color='#5d5d5d'" onblur="if(this.value=='') this.value='手机号'; this.style.color='#cfcfcf'" >
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
	        <div class="login_foot"><span class="f24 gaf">没有帐号？</span><a href="javascript:void(0);" id="btn_register" class="link1">立即注册</a></div>
	    </div>
		<!---------- main end ---------->
	</div>
</body>
</html>
