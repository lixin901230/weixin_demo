var uuid = null;
$(function() {
    
	//获取二维码
	getQrCode();
    
    //手机登录事件绑定
    $("#login_btn").on("click", function(){
    	qrCodeLogin();
    });
});

//获取二维码
function getQrCode() {
    $.get(base +"/PhoneQrCodeLoginServlet?method=getQrCodeUrl", function(data, status) {
    	var obj = eval("(" + data + ")");
    	if(obj.success) {
    		//存储UUID
    		uuid = obj.data.uuid;
    		//显示二维码
    		$("#qrCodeImg").attr("src", obj.data.qrCodeUrl);
    		//开始验证登录
    		validateLogin();
    	}
    });
}

//pc端模拟长连接检查手机端是否已经成功登录后台
function validateLogin(){
	$.get(base +"/LongConnectionCheckServlet?method=checkPhoneLoginState&uuid="+ uuid , function(data, status) {
		var obj = eval("(" + data + ")");
    	debugger
    	if(obj && obj.success){
    		alert("登录成功了:" + obj.data.userName);
    		var userName = obj.data.userName;
    		$("#userName_msg").text(userName+" 登录成功！");
        } else {
        	$("#userName_msg").text("登录中...");
        	validateLogin();
        }
    });
}

//手机扫码后登录
function qrCodeLogin(){
	var uuid = $('#uuid').val();
	var userName = $("#userName").val();
	var password = $("#password").val();
	if(userName == null || userName == "") {
		return false;
	}
	if(password == null || password == "") {
		return false;
	}
	password = MD5(password.replace(/\%/g, "%25").replace(/\+/g, "%2B").replace(/\&/g, "%26"));
    $.post(base +"/PhoneQrCodeLoginServlet?method=phoneLogin", {
    	uuid: uuid,
        userName: userName,
        password: password
    },
    function(data, status) {
    	var obj = eval("(" + data + ")");
        if(obj && obj.success){
        	alert("登录成功");
        }else{
        	alert("登录失败");
        }
    });
}