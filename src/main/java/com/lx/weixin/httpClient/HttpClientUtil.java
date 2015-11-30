package com.lx.weixin.httpClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.Iterator;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lx.weixin.util.CustomFilePart;

public class HttpClientUtil{
	
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
	
	public static HttpClient initHttpClient(){
		HttpClient httpClient = new HttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.USER_AGENT,"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		return httpClient;
	}
	
	/**
	 * post请求方法
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static PostMethod postMethod(String url, Map<String, String> parameter) throws IOException {
		PostMethod post = new PostMethod(url);
		post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] params = new NameValuePair[parameter.keySet().size()];
		Iterator<String> it = parameter.keySet().iterator(); 
		int i = 0;
		String key = "";
		while(it.hasNext()){
			key = it.next();
			params[i] = new NameValuePair(key, parameter.get(key));
			i++;
		}
//			{ new NameValuePair("eIdListString", parameter)};
		post.setRequestBody(params);
		post.releaseConnection();

		return post;
	}

	/**
	 * post请求方法
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static PostMethod postMethodSimple(String url, Map<String, String> parameter) throws IOException {
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] params = new NameValuePair[parameter.keySet().size()];
		Iterator<String> it = parameter.keySet().iterator(); 
		int i = 0;
		String key = "";
		while(it.hasNext()){
			key = it.next();
			params[i] = new NameValuePair(key, parameter.get(key));
			i++;
		}
		postMethod.setQueryString(params);
		postMethod.releaseConnection();
		return postMethod;
	}
	
	/**
	 * POST请求
	 * @param url
	 * @param outStr
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public static JSONObject doPostStr(String url,String outStr) throws ParseException, IOException{
		DefaultHttpClient client = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(url);
		JSONObject jsonObject = null;
		httpost.setEntity(new StringEntity(outStr,"UTF-8"));
		HttpResponse response = client.execute(httpost);
		String result = EntityUtils.toString(response.getEntity(),"UTF-8");
		jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	/**
	 * GET请求方法
	 * 
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public static GetMethod getMethod(String url, Map<String, String> parameter)
			throws IOException {
		GetMethod get = new GetMethod(url);
		//get.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		if (parameter != null) {
			NameValuePair[] params = new NameValuePair[parameter.keySet().size()];
			Iterator<String> it = parameter.keySet().iterator(); 
			int i = 0;
			String key = "";
			while(it.hasNext()){
				key = it.next();
				params[i] = new NameValuePair(key, parameter.get(key));
				i++;
			}
			get.setQueryString(params);
		}
		return get;
	}
	
	/**
	 * HttpClient 文件上传
	 * @param url
	 * @param filePath
	 */
	public static PostMethod uploadFile(String url, String filePath, Map<String, String> parameter) {
		
		PostMethod postMethod = new PostMethod(url) {
			@Override
			public String getRequestCharSet() {
				return "UTF-8";
			}
		};
		try {
			
			NameValuePair[] nameValuePairs = new NameValuePair[parameter.keySet().size()];
			Iterator<String> it = parameter.keySet().iterator(); 
			int i = 0;
			String key = "";
			while(it.hasNext()) {
				key = it.next();
				nameValuePairs[i] = new NameValuePair(key, parameter.get(key));
				i++;
			}
			postMethod.setQueryString(nameValuePairs);
			
			File file = new File(filePath);
			Part[] parts = new Part[] {new CustomFilePart(file.getName(), file)};
			postMethod.setRequestEntity(new MultipartRequestEntity(parts, postMethod.getParams()));
			postMethod.releaseConnection();
			/*HttpClient httpClient = HttpClientUtil.initHttpClient();
			int statuscode = httpClient.executeMethod(postMethod);
			if(statuscode == HttpStatus.SC_OK) {
				System.out.println("添加文件成功");
			} else {
				System.out.println("添加文件失败");
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return postMethod;
	}
	
	/**
	 * 文件上传
	 * @param url
	 * @param filePath
	 * @param accessToken
	 * @throws Exception
	 */
	public static void uploadFile(String url, String filePath, String accessToken) throws Exception {
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		
		try {
			HttpPost httpPost = new HttpPost(url);
			
			File file = new File(filePath);
			HttpEntity httpEntity = new FileEntity(file, ContentType.TEXT_PLAIN);
			httpPost.setEntity(httpEntity);
			CloseableHttpResponse response = httpClient.execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			if(statusCode == HttpStatus.SC_OK){
				logger.info("服务器正常响应.....");
				try {
					HttpEntity respEntity = response.getEntity();
					
					logger.info(EntityUtils.toString(respEntity));//httpclient自带的工具类读取返回数据
					System.out.println(respEntity.getContent());
					
					EntityUtils.consume(respEntity);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					response.close();
				}
			} else {
				throw new Exception("httpclient upload error, http statusCode: ["+statusCode+"]");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * httpClient的GET请求
	 * 返回ResponseBody
	 * @param url
	 * @param paramsMap
	 * @return 
	 * @throws Exception
	 */
	public static String get(String url, Map<String,String> paramsMap)
    		throws Exception{
    	String result = null;
        HttpClient httpClient = HttpClientUtil.initHttpClient();
        GetMethod getMethod= HttpClientUtil.getMethod(url, paramsMap);
        // 请求成功
        int status = httpClient.executeMethod(getMethod);
        if (status == 200){
        	result = getMethod.getResponseBodyAsString();
        }
    	return result;
    }
    
	/**
	 * httpClient的POST请求
	 * 返回ResponseBody
	 * @param url
	 * @param paramsMap
	 * @return
	 * @throws Exception
	 */
    public static String post(String url, Map<String,String> paramsMap)
    		throws Exception{
    	String result = null;
        HttpClient httpClient = HttpClientUtil.initHttpClient();
		PostMethod postMethod = new PostMethod(url);
		postMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		NameValuePair[] params = new NameValuePair[paramsMap.keySet().size()];
		Iterator<String> it = paramsMap.keySet().iterator(); 
		int i = 0;
		String key = "";
		while(it.hasNext()){
			key = it.next();
			params[i] = new NameValuePair(key, paramsMap.get(key));
			i++;
		}
		postMethod.setQueryString(params);
		postMethod.releaseConnection();
        // 请求成功
        int status = httpClient.executeMethod(postMethod);
        if (status == 200){
        	result = postMethod.getResponseBodyAsString();
        }
    	return result;
    }
	
	public static String md5(String sessionKey ,String method, String now) throws Exception{
		String sign = "123456" + now + sessionKey + method;
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(sign.getBytes());
		byte b[] = md.digest();

		int i;
		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
			i = b[offset];
			if (i < 0)
				i += 256;
			if (i < 16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		return buf.toString();
	}

}
