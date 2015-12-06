<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<title>用户登录</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<%@ include file="../common/head.jsp"%>
</head>
<body>
	<div id="login" class="bor">
	  	<h1>用户登录</h1>
	<!--   <a class="back" href="/">返回首页&gt;&gt;</a> -->
	  	<div class="theme">
		   	<form name="form1" action="${ctx}/j_spring_security_check" method="post">
		      	<input type="hidden" name="fmdo" value="login">
		      	<input type="hidden" name="dopost" value="login">
		      
			  	<span style="color: red;width: 80%;text-align: center;">${SPRING_SECURITY_LAST_EXCEPTION.message}</span>
		      
		      	<ul>
		        	<li> 
		        		<span>用户名：</span>
		          		<input id="txtUsername" name="j_username" class="intxt login_from" type="text"/>
		        	</li>
			        <li> 
			        	<span>密&nbsp;&nbsp;&nbsp;码：</span>
			          	<input id="txtPassword" name="j_password" class="intxt login_from2" type="password"/>
			        </li>
			        <!-- 
			        <li>
			        	<span>验证码：</span>
			          	<input type="text" class="intxt w200" style="width: 50px; text-transform: uppercase;" id="vdcode" name="vdcode"/>&nbsp;&nbsp;
			          	<img id="vdimgck" align="absmiddle" onclick="this.src=this.src+'?'" title="看不清？点击更换" style="cursor: pointer;" alt="看不清？点击更换" src="${ctx}/imageCodeAction!getImageCode.action"/> 看不清？ 
			          	<a href="#" onclick="changeAuthCode();">点击更换</a>
			        </li>
			         -->
			        <li> 
			        	<span>有效期：</span>
			          	<input type="radio" value="2592000" name="keeptime" id="ra1"/>
			          	<label for="ra1">一个月</label>
			          	<input type="radio" checked="checked" value="604800" name="keeptime" id="ra2"/>
			          	<label for="ra2">一周</label>
			          	<input type="radio" value="86400" name="keeptime"  id="ra3"/>
			         	<label for="ra3">一天</label>
			          	<input type="radio" value="0" name="keeptime"  id="ra4"/>
			          	<label for="ra4">即时</label>
			        </li>
			        <li> 
			        	<span>&nbsp;</span>
			          	<button id="btnSignCheck" class="login-btn" type="submit">登&nbsp;录</button>
			          	<a class="regbt" href="${ctx}/${ctx}/page/weixin/open/reg.jsp" rel="nofollow">注册账号</a>
					</li>
		      	</ul>
			</form>
	  </div>
	  <br class="clear"/>
	</div>
	<div class="footer">
		Copyright &copy; 2014-2020 用于学习，无版权限制!     
		<a onclick="javascript:window.open('${ctx}/page/weixin/about.jsp');" style="text-decoration: underline;cursor: pointer;">关于我们 </a>
	</div>
</body>
</html>
