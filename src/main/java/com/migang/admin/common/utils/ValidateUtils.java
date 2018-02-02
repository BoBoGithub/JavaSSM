package com.migang.admin.common.utils;

/**
 * 参数校验工具类
 * 
 * @param addTime 2017年8月28日
 * @param author     ChengBo
 */
public class ValidateUtils {

	/**
	 * 校验用户名格式
	 * 
	 * @param userName
	 * @return
	 * 
	 * @param addTime 2017年8月28日
	 * @param author     ChengBo
	 */
	public static Boolean checkUserName(String userName){
		//检查是否为空
		if(userName == null || "".equals(userName)){
			return false;
		}
		
		//设置正则表达式
		String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{5,15}$";

		//返回结果
		return userName.matches(regex);
	}
	
	/**
	 * 检查用户密码格式
	 * 
	 * @param password
	 * @return
	 * 
	 * @param addTime 2017年8月28日
	 * @param author     ChengBo
	 */
	public static Boolean checkPassword(String password){
		//检查是否为空
		if(password == null || "".equals(password)){
			return false;
		}
		
		//设置正则表达式
		String regex = "^[0-9A-Za-z]{5,15}$";
		
		//检查格式
		return password.matches(regex);
	}
	
	/**
	 * 检查手机号格式是否正确
	 * 
	 * @param mobile
	 * 
	 * @return Boolean
	 * 
	 * @param addTime 2017年9月19日
	 * @param author     ChengBo
	 */
	public static Boolean checkMobile(String mobile){
		//检查是否为空
		if(mobile == null || "".equals(mobile)){
			return false;
		}
		
		//设置正则表达式
		String regex = "^(13[0-9]|14[57]|15[012356789]|17[3678]|18[0-9])[0-9]{8}$";
		
		//检查格式
		return mobile.matches(regex);
	}
	
	/**
	 * 检查IP格式
	 * 
	 * @param ip
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 */
	public static Boolean checkIp(String ip){
		//检查是否为空
		if(ip == null || "".equals(ip)){
			return false;
		}
		
		//设置正则表达式
		String regex = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
		
		//检查格式
		return ip.matches(regex);
	}
	
	/**
	 * 验证短信验证码格式
	 * 
	 * @param captcha
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 */
	public static Boolean checkCaptcha(String captcha){
		//检查是否为空
		if(captcha == null || "".equals(captcha)){
			return false;
		}
		
		//设置正则表达式
		String regex = "[0-9A-Za-z]{6}";
		
		//检查格式
		return captcha.matches(regex);
	}
	
	/**
	 * 检查对象是否是数字
	 * 
	 * @param obj
	 * @return
	 * 
	 * @param addTime 2017年9月25日
	 * @param author     ChengBo
	 */
	public static Boolean checkIsNumber(Object obj){
		//检查是否为空
		if(obj == null){
			return false;
		}
		
		//设置正则表达式
		String regex = "^-?[0-9]+";
		
		//检查格式
		return obj.toString().matches(regex);
	}
	
	/**
	 * 检查对象是否时浮点数
	 * 
	 * @param obj
	 * @return
	 * 
	 * @param addTime 2017年10月13日
	 * @param author     ChengBo
	 */
	public static Boolean checkIsDouble(Object obj){
		//检查参数
		if (null == obj || "".equals(obj)) {  
	        return false;  
	    } 
		
		//设置正则表达式
		String regex = "^[-\\+]?[.\\d]*$";
		
		//检查格式
		return obj.toString().matches(regex);
	}
	
	public static Boolean checkEmail(Object obj){
		//检查参数
		if (null == obj || "".equals(obj)) {  
	        return false;  
	    }
		
		//设置正则表达式
		String regex = "\\w+(\\.\\w*)*@\\w+\\.\\w+";
		
		//检查格式
		return obj.toString().matches(regex);
	}
}
