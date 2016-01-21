<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/taglib.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta charset="utf-8">
	<title>登录成功</title>
	<%@ include file="common/head.jsp"%>
</head>
<body style="font-size: 45px; text-align: center;">
	<div>
		<div>用户：<%=request.getParameter("userName")%> 登录成功，欢迎进入登录成功测试页</div>
	</div>
</body>
</html>