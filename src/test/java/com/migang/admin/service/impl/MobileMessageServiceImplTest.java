package com.migang.admin.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.migang.admin.BaseTest;
import com.migang.admin.exception.BusinessException;
import com.migang.admin.service.MobileMessageService;

/**
 * 测试　发送短信服务
 *
 *@param addTime 2017-10-25
 *@param author     ChengBo
 */
public class MobileMessageServiceImplTest extends BaseTest {

	@Autowired
	private MobileMessageService mobileMsgService;
	
	/**
	 * 测试发送登陆短信
	 * 
	 * @param addTime 2017年10月25日
	 * @param author     ChengBo
	 * @throws BusinessException 
	 */
	@Test
	public void testSendLoginMsg() {
		//设置发送参数
		String mobile = "15613187762";

		//调用服务
		Boolean ret;
		try {
			ret = mobileMsgService.sendMobileMsgByType(mobile, 1);
			logger.debug("=== MobileMessageServiceImplTest ret: "+ ret);
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
