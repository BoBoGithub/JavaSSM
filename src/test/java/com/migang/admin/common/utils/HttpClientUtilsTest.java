package com.migang.admin.common.utils;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.migang.admin.BaseTest;

/**
 * 测试　HttpClient请求
 *
 *@param addTime 2017-10-25
 *@param author    ChengBo
 */
public class HttpClientUtilsTest extends BaseTest{

	/**
	 * 测试 Get请求数据
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetRequest(){
		//请求地址
		String url = "http://localhost/sandbox/test/getData5/data.php";
	
		//设置请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("city", "BeiJing");
		
		//获取请求数据
		String RequestData = HttpClientUtils.getRequest(url, params);
		logger.debug("=== {}", RequestData);
	}
	
	/**
	 * 测试　Post发送Body数据
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	@Test
	public void testPostBodyRequest(){
		//设置请求地址
		String url = "http://localhost:8080/admin/app/user/login";
		
		//设置请求参数
		Map<String, String> params = new HashMap<String, String>();
		params.put("appid", "IOS");
		params.put("timestamp", "2017-10-25 15:42:06");
		params.put("data", "+F/f7bw2Owmnrr1obreXRpodBBjb1G8v59gVCSS7tujCyC6yb1RYKO8oWt4naCV9QYAbptbZqgfQHXvJ+eZGUzaEQsI8XkXQ");
		
		//请求数据
		String requestData = HttpClientUtils.postBodyRequest(url, params);
		logger.debug("===== {}", requestData);
	}
	
	@Test
	public void testThreadRequest(){
		MyThreads mt = new MyThreads();
		
		logger.debug("====  Start ======");
		new Thread(mt).start();
		logger.debug("==== One =======");
		new Thread(mt).start();
		logger.debug("==== Two =======");
		new Thread(mt).start();
		logger.debug("==== Third =======");
	}
}

class MyThreads implements Runnable{
//	private int ticket = 100;
	
	public void run(){
		for(int i=0;i<20;i++){
//				System.out.println("买票：ticket "+this.ticket--);
				
				//请求地址
				String requestUrl = "http://172.16.1.95:8180/dubbo-webs/index";
				
				//设置请求参数
				Map<String, String> pMap = new HashMap<String, String>();
				pMap.put("name", "zoo-"+CommonUtils.getRandomNumber(5));
				
				//抓取数据
				String requestData = HttpClientUtils.getRequest(requestUrl, pMap);
				System.out.println("=== "+ requestData);
		}
	}
}