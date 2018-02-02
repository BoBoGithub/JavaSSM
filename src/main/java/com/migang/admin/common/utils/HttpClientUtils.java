package com.migang.admin.common.utils;

import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Http的模拟请求工具类
 *
 *@param addTime 2017-10-25
 *@param author    ChengBo
 */
public class HttpClientUtils {
	//引入日志记录类
	private static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
	
	/**
	 * 模拟发送Http POST请求
	 * 
	 * @param url
	 * @param param
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	public static String postRequest(String url, Map<String, String> params){
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		
		//创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);
		
		//设置请求头信息
		postMethod.setRequestHeader("User-Agent", "Jakarta Commons-HttpClient/3.1 MiGang/BoBo");
		postMethod.setRequestHeader("Connection", "close");
		
		//添加请求参数
		for(Map.Entry<String, String> entry : params.entrySet()){
			postMethod.addParameter(entry.getKey(), entry.getValue());
		}
		
		//关闭重试请求
		httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		
		//设置接收请求结果
		String requestRet = "";
		try{
			//执行Http Post请求
			httpClient.executeMethod(postMethod);
			
			//设置返回结果
			requestRet = postMethod.getResponseBodyAsString();
		}catch(Exception ex){
			//记录异常日志
			logger.error("HttpClientUtils postRequest exception url: {}, param: {}, exMsg: {}", url, params, ex.getMessage());
		}finally{
			//释放链接
			postMethod.releaseConnection();
			
			//管理HttpClient实例
			if(httpClient != null){
				//释放链接
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
        
		//返回请求结果
		return requestRet;
	}
	
	/**
	 * 模拟发送Http GET请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	public static String getRequest(String url, Map<String, String> params){
		//构造HttpClient实例
		HttpClient httpClient = new HttpClient();
		
		//拼接请求参数
		String paramStr = "";
		for(String key : params.keySet()){
			paramStr += "&"+key+"="+params.get(key);
		}
		
		//提出多余的&符号
		paramStr = paramStr.substring(1);
		
		//创建Get方法的实例
		GetMethod method = new GetMethod(url + "?" + paramStr);
		
		//设置请求头信息
		method.setRequestHeader("User-Agent", "Jakarta Commons-HttpClient/3.1 MiGang/BoBo");
		
		//设置结果请求结果
		String requestRet = "";
		try{
			//执行Http Get请求
			httpClient.executeMethod(method);
			
			//返回请求结果
			requestRet = method.getResponseBodyAsString();
		}catch(Exception ex){
			//记录异常日志
			logger.error("HttpClientUtils getRequest exception url: {}, param: {}, exMsg: {}", url, params, ex.getMessage());
		}finally{
			//释放链接
			method.releaseConnection();
			
			//管理HttpClient实例
			if(httpClient != null){
				//释放链接
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
		
		return requestRet;
	}
	
	/**
	 * 模拟发送Http POST请求 －请求类型是application/json＋requestBody
	 * 
	 * @param url
	 * @param params
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	public static String postBodyRequest(String url, Map<String, String> params){
		//构造HttpClient的实例
		HttpClient httpClient = new HttpClient();
		
		//创建POST方法的实例
		PostMethod postMethod = new PostMethod(url);

		//设置请求头信息
		postMethod.setRequestHeader("User-Agent", "Jakarta Commons-HttpClient/3.1 MiGang/BoBo");
		postMethod.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
		postMethod.setRequestHeader("Connection", "close");
		
		//关闭重试请求
		httpClient.getParams().setBooleanParameter("http.protocol.expect-continue", false);
		
		//设置接收请求结果
		String requestRet = "";
		
		try{
			//请求的Map数据转JSON数据
			ObjectMapper mapper = new ObjectMapper();
			String jsonParam = mapper.writeValueAsString(params);;
			
			//添加请求参数
			postMethod.setRequestEntity(new StringRequestEntity(jsonParam, null, "UTF-8"));
			
			//执行Http Post请求
			httpClient.executeMethod(postMethod);
			
			//设置返回结果
			requestRet = postMethod.getResponseBodyAsString();
		}catch(Exception ex){
			//记录异常日志
			logger.error("HttpClientUtils postRequest exception url: {}, param: {}, exMsg: {}", url, params, ex.getMessage());
		}finally{
			//释放链接
			postMethod.releaseConnection();
			
			//管理HttpClient实例
			if(httpClient != null){
				//释放链接
				((SimpleHttpConnectionManager) httpClient.getHttpConnectionManager()).shutdown();
				httpClient = null;
			}
		}
        
		//返回请求结果
		return requestRet;
	}
}
