<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<base href="${ctx}"/>	<!-- 若不加，则在include中引用相对路径后，在使用浏览器后退或手机物理返回时会出现路径错误问题 -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<title>注册</title>
	<%@ include file="../common/head.jsp"%>
	
	<script type="text/javascript"> 
		$(function(){
			$("input[type='checkbox']").click(function(){
				if($(this).is(':checked')){
					$(this).attr("checked","checked");
					$(this).parent().removeClass("c_off").addClass("c_on");
				}else{
					$(this).removeAttr("checked");
					$(this).parent().removeClass("c_on").addClass(" c_off");
				} 
			});
			$("input[type='radio']").click(function(){
				$("input[type='radio']").removeAttr("checked");
				$(this).attr("checked","checked");
				$(this).parent().removeClass("r_off").addClass("r_on").siblings().removeClass("r_on").addClass("r_off");
			}); 
		}); 
	</script> 
</head>

<body class="bg_f9">
	<div class="auto">
	    
		<!---------- main start ---------->
	    <div>
	        <div class="aTab_tabs">
	            <ul>
	                <li class="aTab_tabs_li"><a href="javascript:void(0);" class="aTab_cur" id="aTab1" onClick="setTab('aTab','1','2');">手机注册</a></li>
	                <!-- <li class="aTab_tabs_li"><a href="javascript:;" id="aTab2" onClick="setTab('aTab','2','2');">邮箱注册</a></li> -->
	            </ul>
	        </div>
	        <div>
	        	<!-- 手机注册 -->
	            <div class="aTab_con" id="aTab_con_1" style="display:block;">
	                <div class="form_box"><i class="iconfont form_ico login_phone"></i>
	                	<input name="userName" id="userName" type="text" value="账号" class="form_input" onfocus="if(this.value=='账号') this.value=''; this.style.color='#5d5d5d'" onblur="if(this.value=='') this.value='账号'; this.style.color='#cfcfcf'" >
	                </div>
	                <div class="form_box"><i class="iconfont form_ico login_pwd"></i>
	                	<input name="password" id="password" type="password" class="form_input"/>
	                </div>
	               <!--  <div class="form_box" style="padding-right:230px;"><i class="iconfont form_ico login_vcode"></i>
	                	<input name="validateCode" id="validateCode" type="text" value="验证码" class="form_input" onfocus="if(this.value=='验证码') this.value=''; this.style.color='#5d5d5d'" onblur="if(this.value=='') this.value='验证码'; this.style.color='#cfcfcf'">
	                	<a href="javascript:void(0);" id="send_validate_code" class="form_btn">发送验证码</a>
	                </div> -->
	                <!-- 登录错误提示 -->
			        <div id="register_error_msg" style="margin-left: 30px; margin-top: 5px;">
						<span class="validate_prompt"></span>
					</div>
			        <!-- 登录错误提示end -->
	               <!--  <div class="clearfix p30">
	                    <div class="f_style">
	                        <label for="checkbox_1" class="label_check c_on">
	                        	<input type="checkbox" checked="checked" value="1" id="checkbox_1" name="checkbox_1" /> 
	                       		 同意《平台注册协议》
	                       	</label> 
	                    </div>
	                </div> -->
	                <br/>
	                <div class="p0_30"><a href="javascript:void(0);" id="register_btn" class="btn1">注册</a></div>
	                <div class="login_foot"><span class="f24 gaf">已有帐号？</span><a href="javascript:void(0);" name="btn_login" class="link1">立即登录</a></div>
	            </div>
	            
	        	<!-- 邮箱注册 -->
	            <div class="aTab_con" id="aTab_con_2">
	                <div class="form_box"><i class="iconfont form_ico login_mail"></i>
	                	<input name="" type="text" value="邮箱" class="form_input" onfocus="if(this.value=='邮箱') this.value=''; this.style.color='#5d5d5d'" onblur="if(this.value=='') this.value='邮箱'; this.style.color='#cfcfcf'" >
	                </div>
	                <div class="form_box"><i class="iconfont form_ico login_pwd"></i>
	                	<input name="" type="text" value="密码" class="form_input" onfocus="if(this.value=='密码') this.value=''; this.style.color='#5d5d5d'" onblur="if(this.value=='') this.value='密码'; this.style.color='#cfcfcf'">
	                </div>
	                <div class="clearfix p30">
	                    <div class="f_style">
	                        <label for="checkbox_1" class="label_check c_on">
	                        	<input type="checkbox" checked="checked" value="1" id="checkbox_1" name="checkbox_1" /> 
	                        	同意《MOOC平台注册协议》
	                        </label> 
	                    </div>
	                </div>
	                <div class="p0_30"><a href="javascript:void(0);" class="btn1">注册</a></div>
	                <div class="login_foot"><span class="f24 gaf">已有帐号？</span><a href="javascript:void(0);" name="btn_login" class="link1">立即登录</a></div>
	            </div>
	        </div>
	    </div>
		<!---------- main end ---------->
	</div>
</body>
</html>
