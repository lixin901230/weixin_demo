$(function(){
	
	// 注册液面
	$("#btn_register").on("click", function(){
		window.location.replace(base + "/page/weixin/open/register.jsp");
	});
	
	// 登录页面
	$("a[name='btn_login']").on("click", function(){
		window.location.replace(base + "/page/weixin/open/login.jsp");
	});
});

function register() {
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
}

function login() {
	$.ajax({
		url: '',
		type: 'post',
		data: {},
		dataType: 'json',
		async: false,
		cache: false,
		success: function(data){
			
		},
		error: function(){alrt("登录请求操作失败！")}
	});
}