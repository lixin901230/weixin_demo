$(function() {
    var uuid = null;
    
    //获取二维码
    $.get(base +"/PhoneQrCodeLoginServlet?method=getQrCodeUrl", function(data, status) {
        var obj = eval("(" + data + ")");
        //存储UUID
		uuid = obj.data.uuid;
        //显示二维码
		$("#QrCodeImg").attr("src", obj.qrCodeImg);
        //开始验证登录
        validateLogin();
    });
	
    function validateLogin(){
        $.get("/PhoneQrCodeLoginServlet?method=phoneLogin&uuid="+ uuid , function(data, status) {
            if(data == ""){
                validateLogin();
            }else{
                var obj = eval("(" + data + ")");
                alert("登录成功了:" + obj.uname);
            }
        });
    }
});