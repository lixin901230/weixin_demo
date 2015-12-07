$(function(){
	
	
	// 去注册页面
	$("#btn_register").on("click", function(){
		window.location.replace(base + "/page/weixin/open/register.jsp");
	});
	
	// 去登录页面
	$("a[name='btn_login']").on("click", function(){
		window.location.replace(base + "/page/weixin/open/login.jsp");
	});

	checkUserIsExist();
});

/**
 * 验证新注册的手机账号是否存在
 */
function checkUserIsExist() {
	
	$("#phone").on("blur", function(){
		var phone = $(this).val();
		if(CommonUtil.isEmpty(phone) || phone == '手机号') {
			return false;
		}
		
		$.ajax({
			url: base + '/LoginRegsiterServlet?method=checkUserIsExist',
			type: 'post',
			data: {"phone": phone},
			dataType: 'json',
			cache: false,
			async: false,
			success: function(data){
				if(data && data.isExist == "false") {	//isExist 该手机号是否被注册过；true: 不存在；flase: 存在
					showRegisterErrorMsg("已存在该手机号用户");
				}
			},
			error: function() {
				alert("检测用户唯一请求操作失败！");
			}
		});
	});
}

/**
 * 注册错误信息显示控制
 * @param errorMsg
 */
function showRegisterErrorMsg(errorMsg) {
	if(errorMsg) {
		$("#register_error_msg .validate_prompt").text(errorMsg).show();
		
		// 用户名或者密码输入框得到焦点后将隐藏错误提示
		$("#phone, #password").on("focus", function(e){
			$("#register_error_msg .validate_prompt").hide();
		});
	}
}

function register() {
	
	var phone = $("#phone").val();
	var password = $("#password").val();
	var validateCode = $("#validateCode").val();
	if(CommonUtil.isEmpty(phone) || $.trim(phone) == "手机号") {
		showRegisterErrorMsg("手机号不能为空");
		return false;
	} else if(CommonUtil.isEmpty(password)) {
		showRegisterErrorMsg("密码不能为空");
		return false;
	}/* else if(CommonUtil.isEmpty(validateCode) || validateCode == $.trim("验证码")) {
		showRegisterErrorMsg("请输入验证码");
		return false;
	}*/
	password = MD5(password.replace(/\%/g, "%25").replace(/\+/g, "%2B").replace(/\&/g, "%26"));
	
	$.ajax({
		url: base + '/LoginRegsiterServlet?method=register',
		type: 'post',
		data: {
			"phone": phone,
			"password": password
		},
		dataType: 'json',
		async: false,
		cache: false,
		success: function(data){
			if(data) {
				if(data.success == "true") {	//注册成功后，去用户中心自动登录
					var unionId = data.unionId;
					login(unionId, phone, password);
				}
			}
		},
		error: function(){alrt("注册请求操作失败！");}
	});
}

/**
 * 登录错误信息显示控制
 * @param errorMsg
 */
function showLoginErrorMsg(errorMsg) {
	if(errorMsg) {
		$("#login_error_msg .validate_prompt").text(errorMsg).show();
		
		// 用户名或者密码输入框得到焦点后将隐藏错误提示
		$("#userName, #password").on("focus", function(e){
			$("#login_error_msg .validate_prompt").hide();
		});
	}
}

/**
 * 提交登录
 */ 
function loginSubmit() {
	
	var unionId = $("#unionId").val();
	var userName = $("#userName").val();
	var password = $("#password").val();
	
	if(CommonUtil.isEmpty(userName) || $.trim(userName) == "手机号") {
		showLoginErrorMsg("用户名不能为空");
		return false;
	} else if(CommonUtil.isEmpty(password)) {
		showLoginErrorMsg("密码不能为空");
		return false;
	}
	
	password = MD5(password.replace(/\%/g, "%25").replace(/\+/g, "%2B").replace(/\&/g, "%26"));
	login(unionId, userName, password);
}

// 去用户中心登录
function login(unionId, userName, password) {
	
	var params = {};
	if(!CommonUtil.isEmpty(unionId)) {
		params["unionId"] = unionId;
	}
	if(!CommonUtil.isEmpty(userName) && !CommonUtil.isEmpty(password)) {
		params["userName"] = userName;
		params["password"] = password;
	}
	
	$.ajax({
		url: base + '/LoginRegsiterServlet?method=login',
		type: 'post',
		data: params,
		dataType: 'json',
		cache: false,
		async: false,
		success: function(data){
			if(data) {
				if(data.success) {
					var courseId = $("#courseId").val();
					if($("#courseId").length > 0){
						location.href = _authUrl + 'login_jump.action?courseId='+courseId;
					}else{
						window.location.href = _authUrl + 'index.action';						
					}
				} else {
					var errorMsg = data.logInfo;
					showLoginErrorMsg(errorMsg);
				}
			}
		},
		error: function() {
			alert("登录操作失败！");
		}
	});
}
