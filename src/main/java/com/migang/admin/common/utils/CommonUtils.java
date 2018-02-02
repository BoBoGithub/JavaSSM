package com.migang.admin.common.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 通用工具处理类
 * 
 *＠param addTime 2017-09-04
 *＠param author 	ChengBo
 */
public class CommonUtils {
	
	//引入日志操作
	private static Logger logger = LoggerFactory.getLogger(CommonUtils.class);

	/**
	 * 生成随机字符串
	 * 
	 * @param length 表示生成字符串的长度  
	 * @return
	 * 
	 * @param addTime 2017年9月4日
	 * @param author     ChengBo
	 */
	public static String getRandomString(int length) {
	    String base = "ABCDEFGHIJKLMNPQRSTUVWXYZ1234567890abcdefghijkmnpqrstuvwxyz";     
	    Random random = new Random();     
	    StringBuffer sb = new StringBuffer();     
	    for (int i = 0; i < length; i++) {     
	        int number = random.nextInt(base.length());     
	        sb.append(base.charAt(number));     
	    }     
	    return sb.toString();     
	 }
	
	/**
	 * 随机生成数字类型字符串
	 * 
	 * @param length
	 * @return
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 */
	public static String getRandomNumber(int length){
		 //验证码
        String vcode = "";
        for (int i = 0; i < length; i++) {
            vcode = vcode + (int)(Math.random() * 9);
        }
        return vcode;
	}
	
	/**
	 * 获取当前时间戳　精确到秒的
	 * 
	 * @return
	 * 
	 * @param addTime 2017年9月20日
	 * @param author     ChengBo
	 */
	public static int getCurrTimeStamp(){
		//获取当前的时间戳
		String timestamp = String.valueOf(new Date().getTime()/1000);

		//返回精确到秒的时间戳
		return Integer.valueOf(timestamp);
	}
	
	/**
	 * 时间字符串转　时间戳
	 * 
	 * @param date
	 * @return
	 * 
	 * @param addTime 2017年10月10日
	 * @param author     ChengBo
	 */
	public static String getTimeStampByDate(String dateStr){
			//检查参数
			if(dateStr == null || dateStr.isEmpty()){
				return "";
			}
		
			try {
				//实例化时间格式
				SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
				//返回时间戳
				return String.valueOf(format.parse(dateStr).getTime()/1000);
			} catch (ParseException e) {
				//记录日志
				logger.error(" getTimeStampByDate param: {}, exception: {}", dateStr, e.getMessage());
			}
			
			return "";
	}
	
	/**
	 * 时间戳　转　日期字符串
	 * 
	 * @param timeStamp
	 * @return
	 * 
	 * @param addTime 2017年10月10日
	 * @param author     ChengBo
	 */
	public static String getDateByTimeStamp(long timeStamp){
		//检查参数
		if(timeStamp == 0){
			return "-";
		}
		
		//设置转换格式
	    SimpleDateFormat format =  new SimpleDateFormat("yyyy-MM-dd HH:mm");  
	    
	    //格式化时间戳
	    String date = format.format(new Date(timeStamp*1000));  
	    
	    return date;
	}
	
	/**
	 * list转成用逗号分割的字符串
	 * 
	 * @param listData
	 * @return
	 * 
	 * @param addTime 2017年10月12日
	 * @param author     ChengBo
	 */
	public static String longListToString(List<Long> listData){
		//检查参数
		if(listData.size() == 0){
			return "";
		}
		
		//定义返回结果
		String retStr = "";
		
		//提取数据
		for(int i = 0; i<listData.size(); i++){
			retStr += (i == 0 ? "" : ",")+listData.get(i);
		}
		
		return retStr;
	}
	
	/**
	 * 截取字符串
	 * 
	 * @param s
	 * @param num
	 * @return
	 * 
	 * @param addTime 2017年10月30日
	 * @param author     ChengBo
	 */
	  public static String subString(String s, int num){
		  try{
		    int changdu = s.getBytes("UTF-8").length;
		    if(changdu > num){
		      s = s.substring(0, s.length() - 1);
		      s = subString(s,num);
		    }
		    return s;
		  }catch(Exception ex){
			  //记录日志
			  logger.error(" subString param: {}, {} Exception: {}", s, num, ex.getMessage());
		  }
		  
		  return "";
	  }
	  
	 /**
	  * 获取ip工具类，除了getRemoteAddr，其他ip均可伪造
	  * 
	  * @param request
	  * @return
	  * 
	  * @param addTime 2017年11月1日
	  * @param author     ChengBo
	  */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("Cdn-Src-Ip");    // 网宿cdn的真实ip
		if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");   // 蓝讯cdn的真实ip
		}
		if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("X-Forwarded-For");  // 获取代理ip
		}
		if (ip == null || ip.length() == 0 || " unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP"); // 获取代理ip
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 获取代理ip
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr(); // 获取真实ip
		}
		return ip;
	}
	
	/**
	 * 两个浮点数相加
	 * 
	 * @param num1
	 * @param num2
	 * @return
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	public static String doubleAdd(String num1, String num2){
		//检查参数
		if(!ValidateUtils.checkIsDouble(num1) || !ValidateUtils.checkIsDouble(num2)){
			return "0";
		}
		
		//相熟相加
		BigDecimal a1 = new BigDecimal(String.valueOf(num1));
        BigDecimal b1 = new BigDecimal(String.valueOf(num2));
        BigDecimal c = a1.add(b1);
     
        //返回结果
        return c.toString();
	}
	
	/**
	 * 从身份证号中提取出生日期
	 * 
	 * @param cardNum
	 * @return
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	public static String getBirthDayByCardNum(String cardNum){
		//检查参数
		if(cardNum.equals("")){
			return "";
		}
		
		//提取出生年份
		String year = cardNum.substring(6, 10);
		
		//提取出生月份
		String month = cardNum.substring(10, 12);
		
		//提取出生日期
		String day = cardNum.substring(12, 14);
		
		//返回结果
		return year+"年"+month+"月"+day+"日";
	}
	
	/**
	 * 从身份证中提取性别
	 * 
	 * @param idCard
	 * @return
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
    public static int getAgeByIdCard(String idCard) {
    	//设置返回值
    	int sex = 0;

    	//截取性别字段
        String sCardNum = idCard.substring(16, 17);
        if(sCardNum != ""){
	        if (Integer.parseInt(sCardNum) % 2 != 0) {
	        	 sex = 1;//男
	        } else {
	            sex = 2;//女
	        }
        }
        
        return sex;
    }
    
    /**
     * 格式化输出银行卡号
     * 
     * 　例如：6228 4806 3167 7898 89
     * 
     * @param bankNum
     * @return
     * 
     * @param addTime 2017年11月7日
     * @param author     ChengBo
     */
    public static String getBankNumBySplit(String bankNum){
    	//检查参数
    	if(bankNum == ""){
    		return "";
    	}
    	
    	//设置返回值
    	String retBankNum = "";
    	double maxNum = Math.ceil(bankNum.length()/4.00);
    	for(int i = 1; i <= maxNum ;i++){
    		if(i == maxNum){
    			retBankNum += bankNum.substring((i-1)*4, bankNum.length())+" ";
    		}else{
    			retBankNum += bankNum.substring((i-1)*4, i*4)+" ";
    		}
    	}
    	
    	return retBankNum;
    }
    
    /**
     * 返回加×后的手机号
     * 
     * @param mobile
     * @return
     * 
     * @param addTime 2017年11月8日
     * @param author     ChengBo
     */
    public static String getShortMobile(String mobile){
    	//检查参数
    	if(!ValidateUtils.checkMobile(mobile)){
    		return "";
    	}
    	
    	//返回加星后的字符串
    	return mobile.substring(0, 3)+"****"+mobile.substring(7);
    }
}
