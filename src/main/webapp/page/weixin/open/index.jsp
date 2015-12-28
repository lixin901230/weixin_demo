<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="${ctx}"/>	<!-- 若不加，则在include中引用相对路径后，在使用浏览器后退或手机物理返回时会出现路径错误问题 -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<title>公开首页（不需要登录）</title>
	<%@ include file="../common/head.jsp"%>
	<style type="text/css">
		.font_show {font-size: 45px; line-height: 50px; text-align: center;}
	</style>
</head>
<body>
	<div class="font_show">李鑫的微信网站首页（微信第三方网页授权认证成功）</div>
	<div class="font_show">
		<a href="${ctx}/page/weixin/open/login.jsp" class="font_show">登录</a>
		<a href="${ctx}/page/weixin/open/register.jsp" class="font_show">注册</a><br/>
		<a href="${ctx}/page/weixin/open/weixin_js_sdk.jsp" class="font_show">JS-SDK调试页面</a>
	</div>	
</body>
</html>