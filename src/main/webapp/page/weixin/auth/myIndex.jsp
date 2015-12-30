<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<%@ include file="../common/head.jsp"%>
	<title>我的首页（已登录）</title>
</head>
<body>
	<div style="font-size: 45px;">通过微信UnionId自动登录成功，已进入李鑫的微信网站首页（微信第三方网页授权认证成功）<div>
	<div style="font-size: 45px;">当前登录用户：<a href="#" style="font-size: 45px;">${session_user.userName}</a></div>
	<div style="font-size: 45px;" id="info">
		用户名：${session_user.userName}<br/>
		用户绑定的微信账号唯一标识：${session_user.unionId}
	</div>
	
	<div>退出登录：<a href="javascript:void(0);" id="login_out" style="font-size: 45px;">退出登录</a></div><br/>
	<div align="center"><a href="${ctx}/page/weixin/open/weixin_js_sdk.jsp" style="font-size: 45px;">JS-SDK调试页面</a></div>
</body>
</html>