<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=750, target-densitydpi=device-dpi, user-scalable=no">
	<script type="text/javascript" src="${pageContext.request.contextPath}/resource/jquery-1.9.1.min.js"></script>
	<script type="text/javascript">
	
		var unionId = "${unionId}";	//从session中回去微信账号唯一标识
		$(function(){
			toAutoLogin();
		});
		
		//去用户中心登录
		function toAutoLogin() {
			
			if(unionId) {
				$.ajax({
					url: '/center/center/login_wxMoocLogin.action',
					type: 'post',
					data: {
						"unionId": unionId
					},
					dataType: 'json',
					cache: false,
					async: false,
					success: function(data){
						if(data) {
							var redirectUrl = '/page/weixin/open/index.jsp';
							if(data.success) {
								redirectUrl="/page/weixin/auth/myIndex.jsp";
							}
							window.location.href = redirectUrl;
						}
					},
					error: function() {
						alert("登录操作失败！");
					}
				});
			}
		}
	</script>
</head>
</html>
