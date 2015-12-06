<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<title>用户注册</title>
	<%@ include file="../common/head.jsp" %>
</head>
<body>
	<div id="login" class="bor" >
		<h1>新用户注册</h1>
		<!-- <a class="back" href="/">返回首页&gt;&gt;</a> -->
		<div class="theme">
			<form method="post" action="reg.php" id="regUser" name="form2">
			   	<input type="hidden" value="regbase" name="dopost"/>
			  	<input type="hidden" value="1" name="step"/>
			  	<input type="hidden" value="个人" name="mtype"/>
				<ul>
				  	<li>
					  	<span>用户名：</span>
					  	<input class="intxt w200" id="txtUsername"  name="userid" type="text" id="txtUsername"  onpaste="return false" ondragenter="return false"  style="ime-mode:disabled" class="pwd_inp"/>
				<!--  <input type="text" class="intxt w200" id="txtUsername" name="userid"/> -->
						<em id="_userid"></em> 
					</li>
					<li>
						<span>真实姓名：</span>
						<input type="text" class="intxt w200" id="txtUsername"  name="uname"/>
						<em id="_uname"/></em>&nbsp;重要！请认真填写
					</li>
					<li>
						<span>登陆密码：</span>
					  	<input type="password" onkeyup="setPasswordLevel(this, document.getElementById('passwordLevel'));" style="font-family: verdana;" class="intxt w200" id="txtPassword" name="userpwd"/>
					  	<input id="passwordLevel" class="rank r2" disabled="disabled" name="Input"/>
					</li>
					<li>
						<span>确认密码：</span>
					    <input type="password" class="intxt w200" size="20" value="" id="userpwdok" name="userpwdok"/>
					    <em id="_userpwdok"></em> 
					</li>
					<li>
						<span>电子邮箱：</span>
					    <input type="text" class="intxt w200" id="email" name="email"/> 
					    <em id="_email"></em> 
					</li>
					<li>
						<span>性别：</span>
					    <input type="radio" value="女" name="sex" checked="checked" />女
					    <input type="radio" value="男" name="sex"/>男
					</li>
					<li>
						<span>验证码：</span>
						<input type="text" class="intxt w200" style="width: 50px; text-transform: uppercase;" id="vdcode" name="vdcode"/>&nbsp;&nbsp;
					  	<img id="vdimgck" align="absmiddle" title="看不清？点击更换" onclick="this.src=this.src+'?'" style="cursor: pointer;" alt="看不清？点击更换" src="${ctx}/imageCodeAction!getImageCode.action"/> 看不清？ 
					  	<a href="#" onclick="changeAuthCode();">点击更换</a>
					</li>
					<li>
					  	<span>&nbsp;</span>
					  	<button type="submit" id="btnSignCheck" class="reg-btn">确定注册</button>
					  	<a class="regbt" href="${ctx}/view/login_reg/login.jsp" rel="nofollow">已有账号？</a>
				  	</li>
				</ul>
			
			</form>
		</div>
		<br class="clear"/>
	</div>
	<div class="footer">Copyright &copy; 2014-2020 用于学习，无版权限制!     
		<a onclick="javascript:window.open('${ctx}/view/common/layout/portal/aboutWe.jsp');" style="text-decoration: underline;cursor: pointer;">关于我们 </a>
	</div>
</body>
</html>