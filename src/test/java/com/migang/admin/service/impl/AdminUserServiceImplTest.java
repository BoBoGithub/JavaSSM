package com.migang.admin.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.entity.AdminUser;

/**
 * 用户Serivce的单元测试
 *
 *＠param addTime 2017-08-21
 *@param author   ChengBo
 */
public class AdminUserServiceImplTest extends BaseTest {

	//引入操作Service
	@Autowired
	private AdminUserServiceImpl adminUserService;
	
	/**
	 * 测试根据用户获取用户数据
	 */
	@Test
	public void testGetAdminUserByUserName() throws Exception{
		//设置查询用户
		String username = "admin002";
		
		//查询用户
		AdminUser userInfo = adminUserService.getAdminUserInfoByUserName(username);
		logger.debug("++++++++ testGetAdminUserByUserName userInfo: "+userInfo);
	}
	
	
	//紧跟下边抓取https数据方法
	static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                throws java.security.cert.CertificateException {
            return;
        }
    }
    private static void trustAllHttpsCertificates() throws Exception {
        javax.net.ssl.TrustManager[] trustAllCerts = new javax.net.ssl.TrustManager[1];
        javax.net.ssl.TrustManager tm = new miTM();
        trustAllCerts[0] = tm;
        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");
        sc.init(null, trustAllCerts, null);
        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }
	
	/**
	 * 测试抓取数据
	 * @throws Exception 
	 */
	@Test
	public void getDateFromUrl() throws Exception{
		//该部分必须在获取connection前调用
        trustAllHttpsCertificates();
        HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
                logger.info("Warning: URL Host: " + urlHostName + " vs. " + session.getPeerHost());
                return true;
            }
        };
        HttpsURLConnection.setDefaultHostnameVerifier(hv);
		
		String path = "https://www.migang.com";
        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        
        conn.setConnectTimeout(5 * 1000);
        conn.setRequestMethod("GET");
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu-Java-BoBo Chromium/48.0.2564.82 Chrome/48.0.2564.82 Safari/537.36");
        
        InputStream inStream = conn.getInputStream();
        byte[] data = toByteArray(inStream);
        String result = new String(data, "UTF-8");
        System.out.println(result);
	}
	
	  private static byte[] toByteArray(InputStream input) throws IOException {
	        ByteArrayOutputStream output = new ByteArrayOutputStream();
	        byte[] buffer = new byte[4096];
	        int n = 0;
	        while (-1 != (n = input.read(buffer))) {
	            output.write(buffer, 0, n);
	        }
	        return output.toByteArray();
	    }
	  
	  /**
	   * 测试 抓取代码
	   */
	  @Test
	  public void testGaoYanGetData(){
		  	String baseUrl = "https://www.migang.com";
		  
//			// Save the links to oldMap, key is the link, boolean value to check if the link is checked or not
			Map<String, Boolean> oldMap = new LinkedHashMap<String, Boolean>(); 
//			// newMap to save new links find in loop
			Map<String, Boolean> newMap = new LinkedHashMap<String, Boolean>(); 
			Properties checkLinkValidation = new Properties();
			oldMap.put(baseUrl, false);
			oldMap = crawlLinks(baseUrl, oldMap,newMap,checkLinkValidation);
//			for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
//				System.out.println("The Link in the Website:：" + mapping.getKey());
//
//			}
	  }
	  
		/**
		 * get all links from the website,The breadth first algorithm is used 
		 * Check the new link and request with GET，until no new link find if no new link
		 * find, task over.
		 * 
		 * @param oldLinkHost
		 *            host name
		 * @param oldMap
		 *            Map for checking
		 * 
		 * @return return old map which contains new find links.
		 */
		private Map<String, Boolean> crawlLinks(String oldLinkHost, Map<String, Boolean> oldMap,Map<String, Boolean> newMap,Properties checkLinkValidation) {
			
			String oldLink = "";

			for (Map.Entry<String, Boolean> mapping : oldMap.entrySet()) {
				System.out.println("link in oldMap is: " + mapping.getKey() + " is the link checked?:" + mapping.getValue());
				// If the link is not checked
				if (!mapping.getValue()) {
					oldLink = mapping.getKey();
					// Initiate a GET request
					try {
						URL url = new URL(oldLink);
						HttpURLConnection connection = (HttpURLConnection) url.openConnection();

						connection.setRequestMethod("GET");
						connection.setConnectTimeout(3000);
						connection.setReadTimeout(3000);
						//connection.setRequestProperty("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Ubuntu Chromium/48.0.2564.82 Chrome/48.0.2564.82 Safari/537.36");

						String responseMessage = connection.getResponseMessage();// Get linkStatus
						System.out.println("-------"+responseMessage);
						
						checkLinkValidation.setProperty(oldLink, responseMessage);
//						FileOutputStream fos = new FileOutputStream("C:\\Users\\Administrator\\Desktop\\checkLinkValidation.properties");
						FileOutputStream fos = new FileOutputStream("/tmp/checkLinkValidation.properties");
						checkLinkValidation.store(fos, "Check the link validation in this website");
						
						System.out.println("-------The link:" + oldLink + " responseMessage is: " + responseMessage);
						if (connection.getResponseCode() == 200) {
//							InputStream inputStream = connection.getInputStream();
//							BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
							
					        InputStream inStream =connection.getInputStream();
					        byte[] data = toByteArray(inStream);
					        String result = new String(data, "UTF-8");
					        System.out.println("----"+result);
							
						}
						
						fos.close();
						connection.disconnect();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					oldMap.replace(oldLink, false, true);
				}
			}
			// If have new link, continue checking loop
			if (!newMap.isEmpty()) {
				oldMap.putAll(newMap);
				newMap.clear();
				// For Map specific, it will not have duplicate keys
				oldMap.putAll(crawlLinks(oldLinkHost, oldMap, newMap,checkLinkValidation)); 
			}
		
			return oldMap;
		}
	
}
