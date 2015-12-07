/**
 * js通用的工具类
 * @author zhoudonghua
 */
CommonUtil = function (){
    //var sel = this;
},

//得到项目路径
CommonUtil.getPlatformPath = function(){
//  var strFullPath=window.document.location.href;
//  var strPath=window.document.location.pathname;
//  var pos=strFullPath.indexOf(strPath);
//  var prePath=strFullPath.substring(0,pos);
//  var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
//  return(prePath+postPath);
    return "";
},

//得到项目部署路径
CommonUtil.getPlatformDeployPath = function(){
    var strPath=window.document.location.pathname;
    var postPath=strPath.substring(0,strPath.substr(1).indexOf('/')+1);
    return postPath;
},

//得到项目部署路径
CommonUtil.getCenterPath = function(){
    return "/center";
},

//获取URL的域名
CommonUtil.getDomainByUrl = function(url){
    var domain = null;
    if(url != undefined && url != null){
      var regex = /.*\:\/\/([^\/]*).*/; 
      var match = url.match(regex); 
      if(typeof match != "undefined" && null != match){ 
          domain = match[1]; 
      }
    }
    return domain;
},

//url加随机数
CommonUtil.urlAddRandom = function(url){
    var postUrl = url;
    if(postUrl.indexOf("?") > -1){
        if(postUrl.indexOf("?") +1 < postUrl.length){
            if(postUrl.lastIndexOf("&") + 1 < postUrl.length){
                postUrl += "&";
            }
        }
    }else{
        postUrl += "?";
    }
    postUrl += "randomMath=" + Math.random();
    return postUrl;
},

CommonUtil.dataToJson = function(data){
    var jsonData = {};
    var datas = data.split('&'); 
    for(var i = 0;i < datas.length; i++ ){
        var keyValue = datas[i].split('=');
        jsonData[keyValue[0]] = keyValue[1];
    }
    return jsonData;
},

/**
 * 取缓存值
 * @param c_name
 * @returns
 */
CommonUtil.getCookie = function(c_name){
    if (document.cookie.length > 0) {
        c_start = document.cookie.indexOf(c_name + "=");
        if (c_start != -1) {
            c_start = c_start + c_name.length + 1;
            c_end = document.cookie.indexOf(";", c_start);
            if (c_end == -1)
                c_end = document.cookie.length;
            return unescape(document.cookie.substring(c_start, c_end));
        }
    }
    return "";
},

//等到uploadify插件的上传地址
CommonUtil.getUploadUrl = function(url){
    if($.browser.mozilla || ($.browser.webkit && parseInt($.browser.version,10) < 34 )){
        var cookieValueBase64 = CommonUtil.getCookie('UC00OOIIll11');
        url +='?loginToken=' + cookieValueBase64;
    }
    return url;
},

/**
 * 验证值是否为空
 * @param val 待验证的值
 * @param promptyInfo 要排除的文本提示信息
 * @param isReturnVal 若验证的值不为空，是否返回原值；true: 返回验证值， false: 不返回验证值，只返回验证成功失败标记
 * @return 验证成功失败标记：true: 不为空，false: 为空
 */
CommonUtil.isNotEmpty = function(val, promptyInfo, isRetuanValidateVal){
    
    var isPass = true;
    if(val == null || val == "" || val == "undefined") {
        isPass = false;
    } else if(val == promptyInfo) { //验证排除可能提交的问题提示信息
        isPass = false;
    }
    if(isRetuanValidateVal) {   //判断是否需要返回验证的值，需要返回，否则，则返回验证成功失败标记
        return val;
    } else {
        return isPass;
    }
},

CommonUtil.isEmpty = function(val){
    if(val == null || $.trim(val) == "" || val == "undefined") {
        return true;
    }
    return false;
},

/**
 * 获取两个数的百分比值
 * @param divisor 除数
 * @param dividend 被除数
 * @param precision 在除后保留小数的精度，默认保留小数点后三位
 * @return 返回百分比扩大百倍后的值，如：50.1
 * @auto lixin
 */
CommonUtil.getPercentVal = function(divisor, dividend, precision) {
    
    if(isNaN(divisor)) {
        alert("‘除数’不是一个数字");return;
    }
    if(isNaN(dividend)) {
        alert("‘被除数’不是一个数字");return;
    }
    if(divisor == 0) {
        return 0;
    }
    if(precision == null || precision == "" || precision == "undefined" || precision < 0 || precision > 100) {
        precision = 3;
    }
    var val = (parseFloat(divisor) / parseFloat(dividend)).toFixed(precision);
    return  parseFloat(val) * 100;
},


/**
 * 根据结果判定是否设置默认值，若结果为空，则设置当前结果为置顶的默认值
 * @param result        结果
 * @param defaultVal    默认值
 * @returns     result为空或不存在，则返回参数指定的默认值，否则返回result
 */
CommonUtil.setDefaultVal = function(result, defaultVal) {
    
    if(result == null || result == "" || result == "undefined") {
        if(!defaultVal) {
            alert('为设置默认值参数！');return;
        }
        return defaultVal;
    } else {
        return result;
    }
};
