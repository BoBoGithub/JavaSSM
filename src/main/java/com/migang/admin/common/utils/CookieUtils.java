package com.migang.admin.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie操作工具类
 *
 *＠param addTime 2017-08-05
 *@param author   ChengBo
 */
public class CookieUtils {
	
	//设置用户名信息Cookie名
	public final static String COOKIE_USER_NAME = "uname";
	
	//设置过期时间
	public final static int COOKIE_MAX_AGE = 60*60*24*30;
	
	/**
	 * 添加一个新Cookie
	 * 
	 * @param response
	 * @param cookie
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	private static void addCookie(HttpServletResponse response, Cookie cookie){
		if( cookie != null ){
			response.addCookie(cookie);
		}
	}
	
	/**
	 * 添加一个新Cookie
	 * 
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 * @param httpOnly
	 * @param maxAge
	 * @param path
	 * @param secure
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	private static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain, boolean httpOnly, int maxAge, String path, boolean secure){
		//检查Cookie名
		if( cookieName == null ||  cookieName.equals("") ){
			return ;
		}

		//设置Cookie的值
		cookieValue = (cookieValue == null ? "" : cookieValue);

		//实例化Cookie
		Cookie newCookie = new Cookie(cookieName, cookieValue);
		
		//设置子域，在这个子域下才可以访问该Cookie
		if(domain != null){
			newCookie.setDomain(domain);
		}

		//设置HttpOnly属性，那么通过程序(JS脚本、Applet等)将无法访问该Cookie
		newCookie.setHttpOnly(httpOnly);

		//设置生存期
		if(maxAge > 0){
			newCookie.setMaxAge(maxAge);
		}

		//设置在这个路径下面的页面才可以访问该Cookie
		newCookie.setPath((path==null ? "/" : path));

		//如果设置了Secure，则只有当使用https协议连接时cookie才可以被页面访问
		newCookie.setSecure(secure);

		//追加到Cookie中
		addCookie(response, newCookie);
	}
	
	/**
	 * 设置Cookie
	 * 
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	public static void addCookie(HttpServletResponse response, String cookieName, String cookieValue, String domain){
		//设置Cookei
		addCookie(response, cookieName, cookieValue, domain, true, COOKIE_MAX_AGE, "/", false);
	}
	
	/**
	 * 根据Cookie名获取对应的Cookie
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	private static Cookie getCookie(HttpServletRequest request, String cookieName){
		//获取所有的Cookie数据
		Cookie[] cookies = request.getCookies();

		//检查cookie参数
		if(cookies==null||cookieName==null||cookieName.equals("")){
			return null;
		}

		//过滤提取Cookie
		for(Cookie c : cookies){
			if(c.getName().equals(cookieName)){
				return (Cookie) c;
			}
		}
		
		return null;
	}
	
	/**
	 * 根据Cookie名获取对应的Cookie值
	 * 
	 * @param request
	 * @param cookieName
	 * @return
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	public static String getCookieValue(HttpServletRequest request, String cookieName){
		//获取指定key的值
		Cookie cookie = getCookie(request, cookieName);
		
		//返回Cookie数据
		return (cookie==null ? null : cookie.getValue());
	}

	/**
	 * 删除指定Cookie
	 * 
	 * @param response
	 * @param cookie
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	private static void delCookie(HttpServletResponse response, Cookie cookie){
		if(cookie != null){
			//设置Cookie过期
			cookie.setPath("/");
			cookie.setMaxAge(0);
			cookie.setValue(null);

			//更新Cookie
			response.addCookie(cookie);
		}
	}
	
	/**
	 * 删除指定Cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookieName
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	public static void delCookie(HttpServletRequest request, HttpServletResponse response, String cookieName){
		//提取Cookie数据
		Cookie c = getCookie(request, cookieName);
		
		//删除Cookie
		if(c != null && c.getName().equals(cookieName)){
			delCookie(response, c);
		}
	}
	
	/**
	 * 根据cookie名修改指定的cookie
	 * 
	 * @param request
	 * @param response
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 * 
	 * @param addTime 2017年9月5日
	 * @param author     ChengBo
	 */
	public static void editCookie(HttpServletRequest request, HttpServletResponse response, String cookieName, String cookieValue, String domain){
		//获取指定的Cookie数据
		Cookie c = getCookie(request, cookieName);
		
		//修改Cookie数据
		if(c != null && cookieName != null && !cookieName.equals("") && c.getName().equals(cookieName)){
			addCookie(response, cookieName, cookieValue, domain);
		}
	}
}