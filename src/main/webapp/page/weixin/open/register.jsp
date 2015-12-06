<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<title>登录</title>
</head>
<body>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
		"/page/weixin/open/index.jsp"
		$(function(){
			
			$.ajax({
				url: '',
				type: 'post',
				data: {},
				dataType: 'json',
				async: false,
				cache: false,
				success: function(data){
					
				},
				error: function(){alrt("注册请求操作失败！")}
			});	
		});
	</script>
</body>
</html>