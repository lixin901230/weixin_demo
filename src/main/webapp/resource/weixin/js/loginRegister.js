$(function(){
	
	
	// 去注册页面
	$("#btn_register").on("click", function(){
		window.location.replace(base + "/page/weixin/open/register.jsp");
	});
	
	// 去登录页面
	$("a[name='btn_login']").on("click", function(){
		window.location.replace(base + "/page/weixin/open/login.jsp");
	});
	
	// 检查用户唯一性
	$("#userName").on("blur", function(){
		checkUserNameIsExist();
	});

	// 注册
	$("#register_btn").on("click", function(){
		register();
	});
	
	// 登录
	$("#login_btn").on("click", function(){
		loginSubmit();
	});

	// 退出登录
	$("#login_out").on("click", function(){
		loginOut();
	});
});

/**
 * 注册错误信息显示控制
 * @param errorMsg
 * @param selector
 */
function showRegisterErrorMsg(errorMsg, selector) {
	if(errorMsg) {
		$("#register_error_msg .validate_prompt").text(errorMsg).show();
		
		// 用户名或者密码输入框得到焦点后将隐藏错误提示
		$(selector).on("focus", function(e){
			$("#register_error_msg .validate_prompt").hide();
		});
	}
}

/**
 * 验证新注册的用户名是否存在
 */
function checkUserNameIsExist() {
	
	var isPass = false;
	var userName = $("#userName").val();
	if(CommonUtil.isEmpty(userName) || userName == '用户名') {
		return false;
	}
	$.ajax({
		url: base + '/LoginRegsiterServlet?method=checkUserNameIsExist',
		type: 'post',
		data: {"userName": userName},
		dataType: 'json',
		cache: false,
		async: false,
		success: function(data){
			if(data) {
				if(data.success) {	//isExist 该用户名是否被注册过；true: 不存在；flase: 存在
					isPass = true;
				} else {
					showRegisterErrorMsg("该用户名已被注册", "#userName");
					isPass = false;
				}
			}
		},
		error: function() {
			alert("检测用户唯一请求操作失败！");
			isPass = false;
		}
	});
	return isPass;
}

function register() {
	
	var allowRegister = checkUserNameIsExist();
	if(!allowRegister) {
		showRegisterErrorMsg("该用户名已被注册", "#userName");
		return false;
	}
	
	var userName = $("#userName").val();
	var password = $("#password").val();
	//var validateCode = $("#validateCode").val();
	if(CommonUtil.isEmpty(userName) || $.trim(userName) == "用户名") {
		showRegisterErrorMsg("用户名不能为空", "#userName");
		return false;
	} else if(CommonUtil.isEmpty(password)) {
		showRegisterErrorMsg("密码不能为空", "#password");
		return false;
	} else if(password.length < 6 || password.length > 12) {
		showRegisterErrorMsg("密码需[6-12]位", "#password");
		return false;
	}/* else if(CommonUtil.isEmpty(validateCode) || validateCode == $.trim("验证码")) {
		showRegisterErrorMsg("请输入验证码", "#validateCode");
		return false;
	}*/
	password = MD5(password.replace(/\%/g, "%25").replace(/\+/g, "%2B").replace(/\&/g, "%26"));
	
	$.ajax({
		url: base + '/LoginRegsiterServlet?method=register',
		type: 'post',
		data: {
			"userName": userName,
			"password": password
		},
		dataType: 'json',
		async: false,
		cache: false,
		success: function(data){
			if(data && data.success) {
				var unionId = null;
				if(data.data.unionId) {
					unionId = data.data.unionId;
				}
				login(unionId, userName, password);	//注册成功后，去用户中心自动登录
			}
		},
		error: function(){alrt("注册请求操作失败！");}
	});
}

/**
 * 登录错误信息显示控制
 * @param errorMsg
 * @param selector
 */
function showLoginErrorMsg(errorMsg, selector) {
	if(errorMsg) {
		$("#login_error_msg .validate_prompt").text(errorMsg).show();
		
		// 用户名或者密码输入框得到焦点后将隐藏错误提示
		$(selector).on("focus", function(e){
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
	
	if(CommonUtil.isEmpty(userName) || $.trim(userName) == "用户名") {
		showLoginErrorMsg("用户名不能为空", "#userName");
		return false;
	} else if(CommonUtil.isEmpty(password)) {
		showLoginErrorMsg("密码不能为空", "#password");
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
					location.href = base + "/page/weixin/auth/myIndex.jsp";
				} else {
					var errorMsg = data.logInfo;
					showLoginErrorMsg(errorMsg, "#userName, #password");
				}
			}
		},
		error: function() {
			alert("登录操作失败！");
		}
	});
}

function loginOut() {
	$.ajax({
		url: base + '/LoginRegsiterServlet?method=loginOut',
		type: 'post',
		dataType: 'json',
		cache: false,
		async: false,
		success: function(data){
			if(data) {
				if(data.success) {
					location.replace(base + "/page/weixin/open/login.jsp");
				}
			}
		},
		error: function() {
			alert("注销操作失败！");
		}
	});
}
