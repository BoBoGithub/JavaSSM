package com.migang.admin.common.utils;

import org.junit.Test;

import com.migang.admin.BaseTest;

/**
 * 基础工具类测试
 *
 */
public class CommonUtilsTest extends BaseTest {

	/**
	 * 测试 字符串转时间戳
	 * 
	 * @param addTime 2017年10月10日
	 * @param author     ChengBo
	 */
	@Test
	public void testGetTimeStampByDate(){
		//设置字符串时间戳
		String dateStr = "2017-10-10 10:10:10";
		
		String timeStamp = CommonUtils.getTimeStampByDate(dateStr);
		logger.debug("==ddd= {}", timeStamp);
	}
	
	/**
	 * 测试浮点数格式
	 * 
	 * @param addTime 2017年11月3日
	 * @param author     ChengBo
	 */
	@Test
	public void testDoubleNum(){
		String num = "500.00";
		
		//设置正则表达式
		String regex = "^[-\\+]?[.\\d]*$";
				
		//检查格式
		Boolean ret = num.toString().matches(regex);
		logger.debug("== {}", ret);
		
		Boolean rets = ValidateUtils.checkIsDouble(num);
		logger.debug("=== {}", rets);
	}
	
	/**
	 * 测试Math类函数
	 * 
	 * 
	 * @param addTime 2017年11月7日
	 * @param author     ChengBo
	 */
	@Test
	public void testMath(){
		double tmp = 19/4.00;
		logger.debug("== {}, {}, {}", tmp, Math.ceil(tmp), Math.floor(tmp));
	}
	
	@Test
	public void testEmpty(){
		String tmp = "0.00";
		logger.debug("===== {}, {}, {}, {}, {}", tmp, tmp.equals("0"), tmp.isEmpty(), Double.parseDouble(tmp), Double.parseDouble(tmp) == 0);
	}
}